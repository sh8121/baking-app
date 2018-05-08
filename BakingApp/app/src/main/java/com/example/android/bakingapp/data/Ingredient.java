package com.example.android.bakingapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lgpc on 2018-03-01.
 */

public class Ingredient {
    @SerializedName("quantity") private double mQuantity;
    @SerializedName("measure") private String mMeasure;
    @SerializedName("ingredient") private String mIngredient;

    public Ingredient(double quantity, String measure, String ingredient){
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mIngredient = ingredient;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

}
