package com.kfugosic.bakingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kfugosic.bakingapp.models.Recipe;
import com.kfugosic.bakingapp.models.Step;
import com.kfugosic.bakingapp.recyclerviews.RecipeStepClickListener;
import com.kfugosic.bakingapp.recyclerviews.RecipeStepsAdapter;
import com.kfugosic.bakingapp.ui.IngredientsFragment;
import com.kfugosic.bakingapp.ui.StepDetailsFragment;
import com.kfugosic.bakingapp.utils.AppUtils;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeStepClickListener {

    @BindView(R.id.rv_recipe_steps) protected RecyclerView mRecipeSteps;
    @BindView(R.id.tv_recipe_ingredients) protected TextView mRecipeIngredients;

    private static final String INSTANCE_STATE_RV_POSITION_KEY = "rv_steps_position";
    private static final String TAG = "test123";

    private Parcelable mLayoutManagerState;
    private ArrayList<Step> mSteps;
    private String mIngredients;
    private RecipeStepsAdapter mAdapter;

    private boolean mTwoPane;

    @Override
    protected void onNewIntent(Intent intent) {
        Recipe selectedRecipe = Parcels.unwrap(getIntent().getParcelableExtra(AppUtils.RECIPE_KEY));
        setupRecipeDetailsFragment(selectedRecipe);
    }

    private void setupRecipeDetailsFragment(Recipe selectedRecipe) {
        if(selectedRecipe != null) {
            setTitle(selectedRecipe.getName());
            mSteps = (ArrayList<Step>) selectedRecipe.getSteps();
            mIngredients = AppUtils.buildIngredientsSummary(selectedRecipe.getIngredients());

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecipeSteps.setLayoutManager(layoutManager);
            mRecipeSteps.setHasFixedSize(true);
            mRecipeSteps.setItemAnimator(null);
            mAdapter = new RecipeStepsAdapter(this, mSteps, mTwoPane);
            mRecipeSteps.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            if(mTwoPane) {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setIngredientsSummary(mIngredients);

                FragmentManager fragmentManager = getSupportFragmentManager();
                if(fragmentManager.findFragmentById(R.id.step_details_container) == null){
                    fragmentManager.beginTransaction()
                            .add(R.id.step_details_container, ingredientsFragment)
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.step_details_container, ingredientsFragment)
                            .commit();
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        if(findViewById(R.id.step_details_container) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

        Recipe selectedRecipe = Parcels.unwrap(getIntent().getParcelableExtra(AppUtils.RECIPE_KEY));
        setupRecipeDetailsFragment(selectedRecipe);

        if(savedInstanceState != null) {
            mLayoutManagerState = savedInstanceState.getParcelable(INSTANCE_STATE_RV_POSITION_KEY);
            mRecipeSteps.getLayoutManager().onRestoreInstanceState(mLayoutManagerState);
        }

    }

    @OnClick(R.id.tv_recipe_ingredients)
    public void onIngredientsTextViewClick() {
        mAdapter.deselectAll();
        if(mTwoPane) {
            mRecipeIngredients.setBackgroundColor(AppUtils.SELECTED_COLOR);
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setIngredientsSummary(mIngredients);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, ingredientsFragment)
                    .commit();

            return;
        }
        Intent intent = new Intent(this, IngredientsActivity.class);
        intent.putExtra(AppUtils.INGREDIENTS_KEY, mIngredients);
        startActivity(intent);
    }

    /**
     * Shows recipe ingredients and mSteps for clicked recipe
     * @param clickedStepIndex
     */
    @Override
    public void onListItemClick(int clickedStepIndex) {
        if(mTwoPane) {
            mRecipeIngredients.setBackgroundColor(Color.WHITE);
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.initializeFragmentData(mSteps, clickedStepIndex);
            stepDetailsFragment.setShowButtons(false);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();
            return;
        }
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra(AppUtils.ALL_STEPS_KEY, Parcels.wrap(mSteps));
        intent.putExtra(AppUtils.CURRENT_STEP_INDEX_KEY, clickedStepIndex);
        startActivity(intent);
    }


    //
    //    Keeping track of recycler view scroll position
    //
    @Override
    protected void onPause() {
        super.onPause();
        if(mRecipeSteps != null) {
            mLayoutManagerState = mRecipeSteps.getLayoutManager().onSaveInstanceState();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(INSTANCE_STATE_RV_POSITION_KEY, mLayoutManagerState);
        super.onSaveInstanceState(outState);
    }

}
