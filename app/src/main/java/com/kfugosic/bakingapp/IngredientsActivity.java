package com.kfugosic.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.kfugosic.bakingapp.ui.IngredientsFragment;

import butterknife.ButterKnife;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        ButterKnife.bind(this);

        if(getIntent() == null){
            return;
        }
        String ingredients = getIntent().getStringExtra(RecipeDetailsActivity.INGREDIENTS_KEY);

        if(savedInstanceState == null) {

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setIngredientsSummary(ingredients);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.ingredients_container, ingredientsFragment)
                    .commit();
        }
    }

}
