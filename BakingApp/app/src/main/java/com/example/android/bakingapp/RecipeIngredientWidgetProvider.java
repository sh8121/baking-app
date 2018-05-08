package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Ingredient;
import com.example.android.bakingapp.data.RecipeSummary;
import com.example.android.bakingapp.recipedetail.RecipeDetailActivity;
import com.google.gson.Gson;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, RecipeSummary recipeSummary) {
        // Construct the RemoteViews object
        List<Ingredient> ingredientList = recipeSummary.getIngredients();
        String ingredientStr = "";
        for(Ingredient ingredient : ingredientList){
            ingredientStr += (ingredient.getIngredient() + "/" + ingredient.getQuantity() + "/" + ingredient.getMeasure() + "\n");
        }
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_widget);
        views.setTextViewText(R.id.widget_title, recipeSummary.getName());
        views.setTextViewText(R.id.widget_ingredient_list, ingredientStr);

        String recipeSummaryJsonStr = new Gson().toJson(recipeSummary);
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipeSummary.DATA_KEY, recipeSummaryJsonStr);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);
        views.setOnClickPendingIntent(R.id.widget_ingredient_list, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, List<RecipeSummary> recipeSummaryList){
        for(int appWidgetId : appWidgetIds){
            int randomNum = (int)(Math.random() * recipeSummaryList.size());
            RecipeSummary recipeSummary = recipeSummaryList.get(randomNum);
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeSummary);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RecipeUpdatingService.startRecipeUpdate(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

