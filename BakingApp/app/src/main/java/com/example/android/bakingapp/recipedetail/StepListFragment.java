package com.example.android.bakingapp.recipedetail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeSummary;
import com.google.gson.Gson;

/**
 * Created by lgpc on 2018-04-28.
 */

public class StepListFragment extends Fragment {
    private RecyclerView mIngredientRecyclerView;
    private RecyclerView mStepRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        String extraJsonStr =  getArguments().getString(RecipeSummary.DATA_KEY);
        RecipeSummary extraRecipeSummary = new Gson().fromJson(extraJsonStr, RecipeSummary.class);

        mIngredientRecyclerView = rootView.findViewById(R.id.rv_ingredient_list);
        mStepRecyclerView = rootView.findViewById(R.id.rv_step_list);

        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(getActivity(), extraRecipeSummary.getIngredients());
        mIngredientRecyclerView.setAdapter(ingredientListAdapter);
        ingredientListAdapter.notifyDataSetChanged();

        StepListAdapter stepListAdapter = new StepListAdapter(getActivity(), extraRecipeSummary.getSteps(), (RecipeDetailActivity)getActivity());
        mStepRecyclerView.setAdapter(stepListAdapter);
        stepListAdapter.notifyDataSetChanged();

        return rootView;
    }
}
