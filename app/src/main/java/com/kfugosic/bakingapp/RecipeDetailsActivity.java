package com.kfugosic.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kfugosic.bakingapp.models.Recipe;
import com.kfugosic.bakingapp.models.Step;
import com.kfugosic.bakingapp.recyclerviews.RecipeStepClickListener;
import com.kfugosic.bakingapp.recyclerviews.RecipeStepsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeStepClickListener {

    @BindView(R.id.rv_recipe_steps) protected RecyclerView mRecipeSteps;

    private static final String KEY_INSTANCE_STATE_RV_POSITION = "rv_steps_position";
    public static final String ALL_STEPS_KEY = "step";
    public static final String CURRENT_STEP_INDEX_KEY = "step_index";


    private Parcelable mLayoutManagerState;
    private ArrayList<Step> mSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        Recipe selectedRecipe = getIntent().getParcelableExtra(MainActivity.RECIPE_KEY);
        if(selectedRecipe == null) {
            return;
        }
        mSteps = (ArrayList<Step>) selectedRecipe.getSteps();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecipeSteps.setLayoutManager(layoutManager);
        mRecipeSteps.setHasFixedSize(true);
        RecipeStepsAdapter adapter = new RecipeStepsAdapter(this, mSteps);
        mRecipeSteps.setAdapter(adapter);

        if(savedInstanceState != null) {
            mLayoutManagerState = savedInstanceState.getParcelable(KEY_INSTANCE_STATE_RV_POSITION);
        }

    }

    /**
     * Shows recipe ingredients and mSteps for clicked recipe
     * @param clickedStepIndex
     */
    @Override
    public void onListItemClick(int clickedStepIndex) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putParcelableArrayListExtra(ALL_STEPS_KEY, mSteps);
        intent.putExtra(CURRENT_STEP_INDEX_KEY, clickedStepIndex);
        startActivity(intent);
    }


    //
    //    Keeping track of recycler view scroll position
    //
    @Override
    protected void onPause() {
        super.onPause();
        mLayoutManagerState = mRecipeSteps.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_INSTANCE_STATE_RV_POSITION, mLayoutManagerState);
        super.onSaveInstanceState(outState);
    }

}
