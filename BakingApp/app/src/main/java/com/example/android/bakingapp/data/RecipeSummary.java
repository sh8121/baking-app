package com.example.android.bakingapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lgpc on 2018-02-11.
 */

public class RecipeSummary {
    public static final String DATA_KEY = "recipeSummary";

    @SerializedName("id") private int mId;
    @SerializedName("name")private String mName;
    @SerializedName("servings")private int mServings;
    @SerializedName("image")private String mImage;
    @SerializedName("ingredients")private List<Ingredient> mIngredients;
    @SerializedName("steps")private List<Step> mSteps;

    public RecipeSummary(int id, String name, int servings, String image, List<Ingredient> ingredients, List<Step> steps) {
        this.mId = id;
        this.mName = name;
        this.mServings = servings;
        this.mImage = image;
        this.mIngredients = ingredients;
        this.mSteps = steps;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getServings() {
        return mServings;
    }

    public String getImage() {
        return mImage;
    }

    public List<Ingredient> getIngredients(){
        return mIngredients;
    }

    public List<Step> getSteps(){
        return mSteps;
    }
}
