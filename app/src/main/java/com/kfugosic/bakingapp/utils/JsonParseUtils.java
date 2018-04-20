package com.kfugosic.bakingapp.utils;

import com.kfugosic.bakingapp.models.Ingredient;
import com.kfugosic.bakingapp.models.Recipe;
import com.kfugosic.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristijan on 20-Apr-18.
 */

public class JsonParseUtils {

    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    public static final String JSON_QUANTITY = "quantity";
    public static final String JSON_MEASURE = "measure";
    public static final String JSON_INGREDIENT = "ingredient";
    public static final String JSON_INGREDIENTS = "ingredients";
    public static final String JSON_STEPS = "steps";
    public static final String JSON_SHORT_DESC = "shortDescription";
    public static final String JSON_DESCRIPTION = "description";
    public static final String JSON_VIDEO_URL = "videoURL";
    public static final String JSON_THUMBNAIL_URL = "thumbnailURL";

    public static List<Recipe> parseRecipesJson(String json) {
        List<Recipe> recipes = new ArrayList<>();
        if (json == null) {
            return recipes;
        }
        try {
            JSONArray allRecipes = new JSONArray(json);
            for (int i = 0; i < allRecipes.length(); i++) {
                JSONObject currentRecipe = allRecipes.getJSONObject(i);
                int id = currentRecipe.optInt(JSON_ID);
                String name = currentRecipe.optString(JSON_NAME);
                List<Ingredient> ingredientList = parseIngredients(currentRecipe.optJSONArray(JSON_INGREDIENTS));
                List<Step> stepsList = parseSteps(currentRecipe.optJSONArray(JSON_STEPS));
                Recipe newRecipe = new Recipe(id, name, ingredientList, stepsList);
                recipes.add(newRecipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private static List<Step> parseSteps(JSONArray steps) {
        List<Step> stepsList = new ArrayList<>();
        for (int j = 0; j < steps.length(); j++) {
            JSONObject currentIngredients = steps.optJSONObject(j);
            Step newStep = new Step(
                    currentIngredients.optInt(JSON_ID),
                    currentIngredients.optString(JSON_SHORT_DESC),
                    currentIngredients.optString(JSON_DESCRIPTION),
                    currentIngredients.optString(JSON_VIDEO_URL),
                    currentIngredients.optString(JSON_THUMBNAIL_URL)
            );
            stepsList.add(newStep);
        }
        return stepsList;
    }

    private static List<Ingredient> parseIngredients(JSONArray ingredients) {
        List<Ingredient> ingredientList = new ArrayList<>();
        for (int j = 0; j < ingredients.length(); j++) {
            JSONObject currentIngredients = ingredients.optJSONObject(j);
            Ingredient newIngredient = new Ingredient(
                    currentIngredients.optString(JSON_QUANTITY),
                    currentIngredients.optString(JSON_MEASURE),
                    currentIngredients.optString(JSON_INGREDIENT)
            );
            ingredientList.add(newIngredient);
        }
        return ingredientList;
    }

}
