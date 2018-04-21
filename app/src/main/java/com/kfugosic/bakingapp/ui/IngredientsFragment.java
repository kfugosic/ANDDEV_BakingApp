package com.kfugosic.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kfugosic.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kristijan on 21-Apr-18.
 */

public class IngredientsFragment extends Fragment {

    private static final String INGREDIENTS_CACHE = "ingredients_cache";

    @BindView(R.id.tv_recipe_ingredients) protected TextView mIngredientsTextView;

    private String mIngredientsSummary;

    public IngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null) {
            mIngredientsSummary = savedInstanceState.getString(INGREDIENTS_CACHE);
        }

        if(mIngredientsSummary != null) {
            mIngredientsTextView.setText(mIngredientsSummary);
        }

        return rootView;
    }

    public void setIngredientsSummary(String ingredientsSummary) {
        this.mIngredientsSummary = ingredientsSummary;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(INGREDIENTS_CACHE, mIngredientsSummary);
    }

}
