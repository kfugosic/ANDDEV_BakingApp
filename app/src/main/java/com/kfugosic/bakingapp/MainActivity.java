package com.kfugosic.bakingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.kfugosic.bakingapp.models.Recipe;
import com.kfugosic.bakingapp.recyclerviews.RecipeCardAdapter;
import com.kfugosic.bakingapp.recyclerviews.RecipeCardClickListener;
import com.kfugosic.bakingapp.utils.JsonParseUtils;
import com.kfugosic.bakingapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeCardClickListener, LoaderManager.LoaderCallbacks<List<Recipe>> {

    @BindView(R.id.rv_recipes) protected RecyclerView mRecipesList;

    public static final String RECIPE_KEY = "recipe";
    private static final String KEY_INSTANCE_STATE_RV_POSITION = "rv_position";
    private static final int RECIPES_LIST_LOADER = 101;

    private Parcelable mLayoutManagerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecipesList.setLayoutManager(layoutManager);
        mRecipesList.setHasFixedSize(true);
        RecipeCardAdapter adapter = new RecipeCardAdapter(this, null);
        mRecipesList.setAdapter(adapter);

        if(savedInstanceState != null) {
            mLayoutManagerState = savedInstanceState.getParcelable(KEY_INSTANCE_STATE_RV_POSITION);
        }

        fillAdapter();

    }

    /**
     * Shows recipe ingredients and steps for clicked recipe
     * @param clickedRecipe
     */
    @Override
    public void onListItemClick(Recipe clickedRecipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_KEY, clickedRecipe);
        startActivity(intent);
    }



    //
    //    Keeping track of recycler view scroll position
    //
    @Override
    protected void onPause() {
        super.onPause();
        mLayoutManagerState = mRecipesList.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_INSTANCE_STATE_RV_POSITION, mLayoutManagerState);
        super.onSaveInstanceState(outState);
    }



    /**
     * Fill the recycler view (recipes list) adapter
     */
    private void fillAdapter() {

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> recipesListLoader = loaderManager.getLoader(RECIPES_LIST_LOADER);
        if(recipesListLoader == null) {
            loaderManager.initLoader(RECIPES_LIST_LOADER, null, this);
        } else {
            loaderManager.restartLoader(RECIPES_LIST_LOADER, null, this);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(this) {

            List<Recipe> mQueryResults;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(mQueryResults == null) {
                    forceLoad();
                    return;
                }
                deliverResult(mQueryResults);
            }

            @Override
            public List<Recipe> loadInBackground() {
                String queryResult = null;
                try {
                    queryResult = NetworkUtils.getResponseFromHttpUrl(new URL(NetworkUtils.RECIPES_URL));
                } catch (IOException e) {e.printStackTrace();}
                return JsonParseUtils.parseRecipesJson(queryResult);
            }

            @Override
            public void deliverResult(List<Recipe> data) {
                mQueryResults = data;
                super.deliverResult(data);
            }
        };
    }



    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
        if(recipes == null || recipes.isEmpty()) {
            Toast.makeText(this, "Recipes data not available!", Toast.LENGTH_SHORT).show();
            return;
        }
        RecipeCardAdapter adapter = new RecipeCardAdapter(this, recipes);
        mRecipesList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(mLayoutManagerState != null) {
            mRecipesList.getLayoutManager().onRestoreInstanceState(mLayoutManagerState);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
    }
}
