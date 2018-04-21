package com.kfugosic.bakingapp.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Kristijan on 20-Apr-18.
 */

@Parcel
public class Recipe {

    int id;
    String name;
    List<Ingredient> ingredients;
    List<Step> steps;

    Recipe() {}

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

}