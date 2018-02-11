package com.example.android.bakingapp.recipelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeSummary;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecyclerView = findViewById(R.id.rv_recipe_list);
        mAdapter = new RecipeListAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);

        List<RecipeSummary> dummy = new ArrayList<>();
        dummy.add(new RecipeSummary(1, "Nutella Pi", 8, ""));
        dummy.add(new RecipeSummary(2, "Brownies", 8, ""));
        dummy.add(new RecipeSummary(3, "Yellow Cake", 8, ""));
        dummy.add(new RecipeSummary(4, "Cheesecake", 8, ""));
        dummy.add(new RecipeSummary(5, "Nutella Pi", 8, ""));
        dummy.add(new RecipeSummary(6, "Nutella Pi", 8, ""));
        dummy.add(new RecipeSummary(7, "Nutella Pi", 8, ""));
        dummy.add(new RecipeSummary(8, "Nutella Pi", 8, ""));
        dummy.add(new RecipeSummary(9, "Nutella Pi", 8, ""));
        dummy.add(new RecipeSummary(10, "Nutella Pi", 8, ""));
        dummy.add(new RecipeSummary(11, "Nutella Pi", 8, ""));
        dummy.add(new RecipeSummary(12, "Nutella Pi", 8, ""));
        dummy.add(new RecipeSummary(13, "Nutella Pi", 8, ""));

        mAdapter.setRecipeSummaryList(dummy);
    }
}
