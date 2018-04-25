package com.kfugosic.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
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
        String ingredients = getIntent().getStringExtra(AppUtils.INGREDIENTS_KEY);

        if(savedInstanceState == null) {

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setIngredientsSummary(ingredients);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.ingredients_container, ingredientsFragment)
                    .commit();
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
