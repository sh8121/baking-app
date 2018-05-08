package com.example.android.bakingapp.recipelist;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.bakingapp.data.RecipeSummary;
import com.example.android.bakingapp.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sboo on 2018-02-21.
 */

public class RecipeListLoader extends AsyncTaskLoader<List<RecipeSummary>>{
    public static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private Context mContext;

    public RecipeListLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<RecipeSummary> loadInBackground() {
        List<RecipeSummary> result = new ArrayList<RecipeSummary>();
        try{
            String response = NetworkUtil.getJsonResponse(new URL(RECIPE_URL));
            Gson gson = new Gson();
            Type listType = new TypeToken<List<RecipeSummary>>(){}.getType();
            result = gson.fromJson(response, listType);
        }
        catch (Exception ex){
            Log.e("AA", ex.getMessage());
        }

        return result;
    }
}
