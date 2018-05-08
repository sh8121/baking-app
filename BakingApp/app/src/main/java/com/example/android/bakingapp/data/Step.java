package com.example.android.bakingapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lgpc on 2018-03-01.
 */

public class Step {
    public static final String DATA_KEY = "step";
    public static final String POSITION_KEY = "position";

    @SerializedName("id") private int mId;
    @SerializedName("shortDescription") private String mShortDescription;
    @SerializedName("description") private String mDescription;
    @SerializedName("videoURL") private String mVideoURL;
    @SerializedName("thumbnailURL") private String mThumbnailURL;

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL){
        this.mId = id;
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mVideoURL = videoURL;
        this.mThumbnailURL = thumbnailURL;
    }

    public int getId() {
        return mId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }
}
