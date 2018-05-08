package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
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
 * Created by lgpc on 2018-05-07.
 */

public class RecipeUpdatingService extends IntentService {
    public static final String ACTION_RECIPE_UPDATE = "com.example.android.bakingapp.action.recipe_update";
    private static final String TAG_NAME = RecipeUpdatingService.class.getName();
    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public RecipeUpdatingService() {
        super(TAG_NAME);
    }

    public static void startRecipeUpdate(Context context){
        Intent intent = new Intent(context, RecipeUpdatingService.class);
        intent.setAction(ACTION_RECIPE_UPDATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        if(ACTION_RECIPE_UPDATE.equals(action)){
            handleRecipeUpdate();
        }
    }

    private void handleRecipeUpdate(){
        List<RecipeSummary> result = new ArrayList<RecipeSummary>();
        try{
            String response = NetworkUtil.getJsonResponse(new URL(RECIPE_URL));
            Gson gson = new Gson();
            Type listType = new TypeToken<List<RecipeSummary>>(){}.getType();
            result = gson.fromJson(response, listType);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWIdgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientWidgetProvider.class));
            RecipeIngredientWidgetProvider.updateAppWidgets(this, appWidgetManager, appWIdgetIds, result);
        }
        catch (Exception ex){
            Log.e("AA", ex.getMessage());
        }
    }
}
