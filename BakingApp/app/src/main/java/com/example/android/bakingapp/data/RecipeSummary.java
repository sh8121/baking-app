package com.example.android.bakingapp.data;

/**
 * Created by lgpc on 2018-02-11.
 */

public class RecipeSummary {
    private int mId;
    private String mName;
    private int mServings;
    private String mImage;

    public RecipeSummary(int id, String name, int servings, String image) {
        this.mId = id;
        this.mName = name;
        this.mServings = servings;
        this.mImage = image;
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

}
