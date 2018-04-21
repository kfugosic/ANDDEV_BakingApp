package com.kfugosic.bakingapp.models;

import org.parceler.Parcel;

/**
 * Created by Kristijan on 20-Apr-18.
 */

@Parcel
public class Ingredient {

    String quantity;
    String measure;
    String name;

    Ingredient() {}

    public Ingredient(String quantity, String measure, String name) {
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}