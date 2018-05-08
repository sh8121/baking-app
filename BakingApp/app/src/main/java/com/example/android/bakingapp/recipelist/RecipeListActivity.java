package com.example.android.bakingapp.recipelist;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.recipedetail.RecipeDetailActivity;
import com.example.android.bakingapp.data.RecipeSummary;
import com.google.gson.Gson;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<RecipeSummary>>,
                   RecipeListAdapter.RecipeItemClickListener {

    private RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecyclerView = findViewById(R.id.rv_recipe_list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter = new RecipeListAdapter(this, null, this);
        mRecyclerView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    @Override
    public Loader<List<RecipeSummary>> onCreateLoader(int i, Bundle bundle) {
        return new RecipeListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<RecipeSummary>> loader, List<RecipeSummary> recipeSummaryList) {
        if(recipeSummaryList != null){
            mAdapter.setRecipeSummaryList(recipeSummaryList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<RecipeSummary>> loader) {
        mAdapter.setRecipeSummaryList(null);
    }

    @Override
    public void onClick(RecipeSummary recipeSummary) {
        String jsonStr = new Gson().toJson(recipeSummary);
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeSummary.DATA_KEY, jsonStr);
        startActivity(intent);
    }
}
