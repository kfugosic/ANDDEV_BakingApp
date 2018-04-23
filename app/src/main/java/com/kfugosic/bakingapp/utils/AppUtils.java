package com.kfugosic.bakingapp.utils;

import android.graphics.Color;

import com.kfugosic.bakingapp.models.Ingredient;

import java.util.List;

/**
 * Created by Kristijan on 23-Apr-18.
 */

public class AppUtils {

    public static final String RECIPE_KEY = "recipe";
    public static final String ALL_STEPS_KEY = "step";
    public static final String CURRENT_STEP_INDEX_KEY = "step_index";
    public static final String INGREDIENTS_KEY = "ingredients";

    public static final int SELECTED_COLOR = Color.parseColor("#F7F4EB");
    //
    // Build ingredients preview string from list
    //
    public static String buildIngredientsSummary(List<Ingredient> ingredients) {
        StringBuilder sb = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            sb.append(String.format("%s %s of %s%n", ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getName()));
        }
        return sb.toString();
    }

}
