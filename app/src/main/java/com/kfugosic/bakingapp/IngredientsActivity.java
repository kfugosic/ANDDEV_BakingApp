package com.kfugosic.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.kfugosic.bakingapp.ui.IngredientsFragment;
import com.kfugosic.bakingapp.utils.AppUtils;

import butterknife.ButterKnife;

public class IngredientsActivity extends AppCompatActivity {

    private static final String ACTIVITY_TITLE = "Ingredients";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        ButterKnife.bind(this);

        if(getIntent() == null){
            return;
        }

        setTitle(ACTIVITY_TITLE);

        if(savedInstanceState == null) {
            String ingredients = getIntent().getStringExtra(AppUtils.INGREDIENTS_KEY);
            setupIngredientsFragment(ingredients);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("TAG", "onNewIntent: " + String.valueOf(intent==null));
        String ingredients = intent.getStringExtra(AppUtils.INGREDIENTS_KEY);
        setupIngredientsFragment(ingredients);
    }

    private void setupIngredientsFragment(String ingredients) {
        if(ingredients != null) {
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setIngredientsSummary(ingredients);

            FragmentManager fragmentManager = getSupportFragmentManager();
            if(fragmentManager.findFragmentById(R.id.ingredients_container) == null){
                fragmentManager.beginTransaction()
                        .add(R.id.ingredients_container, ingredientsFragment)
                        .commit();
                Log.d("TAG", "add");
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.ingredients_container, ingredientsFragment)
                        .commit();
                Log.d("TAG", "replace");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
