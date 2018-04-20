package com.kfugosic.bakingapp.recyclerviews;

import com.kfugosic.bakingapp.models.Recipe;

/**
 * Created by Kristijan on 20-Apr-18.
 */

public interface RecipeCardClickListener {
    void onListItemClick(Recipe clickedRecipe);
}
