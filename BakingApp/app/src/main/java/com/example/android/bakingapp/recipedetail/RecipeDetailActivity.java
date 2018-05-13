package com.example.android.bakingapp.recipedetail;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeSummary;
import com.example.android.bakingapp.data.Step;
import com.google.gson.Gson;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements StepListAdapter.StepListOnClickListener {
    private Configuration mConfig;
    private FrameLayout mStepListContainer;
    private FrameLayout mStepDetailContainer;
    private Fragment mStepListFragment;
    private Fragment mStepDetailFragment;
    private String mRecipeSummaryJsonStr;
    private String mRecipeStepDetailJsonStr;
    private int mRecipeStepDetailPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mConfig = getResources().getConfiguration();
        mStepListContainer = findViewById(R.id.step_list_container);
        mStepDetailContainer = findViewById(R.id.step_detail_container);

        if(savedInstanceState != null){
            mRecipeSummaryJsonStr = savedInstanceState.getString(RecipeSummary.DATA_KEY);
            mRecipeStepDetailJsonStr = savedInstanceState.getString(Step.DATA_KEY);
            mRecipeStepDetailPosition = savedInstanceState.getInt(Step.POSITION_KEY);

            Bundle stepListBundle = new Bundle();
            stepListBundle.putString(RecipeSummary.DATA_KEY, mRecipeStepDetailJsonStr);

            Bundle stepDetailBundle = new Bundle();
            stepDetailBundle.putString(Step.DATA_KEY, mRecipeStepDetailJsonStr);
            stepDetailBundle.putInt(Step.POSITION_KEY, mRecipeStepDetailPosition);

            mStepListFragment = new StepListFragment();
            mStepListFragment.setArguments(stepListBundle);

            if(!TextUtils.isEmpty(mRecipeStepDetailJsonStr)){
                mStepDetailFragment = new StepDetailFragment();
                mStepDetailFragment.setArguments(stepDetailBundle);
                mStepListContainer.setVisibility(View.INVISIBLE);
                mStepDetailContainer.setVisibility(View.VISIBLE);
            }
        }
        else{
            mRecipeSummaryJsonStr = getIntent().getStringExtra(RecipeSummary.DATA_KEY);
            Bundle bundle = new Bundle();
            bundle.putString(RecipeSummary.DATA_KEY, mRecipeSummaryJsonStr);
            mStepListFragment = new StepListFragment();
            mStepListFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.step_list_container, mStepListFragment)
                    .commit();
            if(mConfig.smallestScreenWidthDp < 600){
                mStepDetailContainer.setVisibility(View.INVISIBLE);
            }
            else{
                RecipeSummary recipeSummary = new Gson().fromJson(mRecipeSummaryJsonStr, RecipeSummary.class);
                List<Step> stepList = recipeSummary.getSteps();
                onStepClick(stepList, 0);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(RecipeSummary.DATA_KEY, mRecipeSummaryJsonStr);
        outState.putString(Step.DATA_KEY, mRecipeStepDetailJsonStr);
        outState.putInt(Step.POSITION_KEY, mRecipeStepDetailPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepClick(List<Step> stepList, int position) {
        mStepDetailFragment = new StepDetailFragment();
        mRecipeStepDetailJsonStr = new Gson().toJson(stepList);
        mRecipeStepDetailPosition = position;
        Bundle bundle = new Bundle();
        bundle.putString(Step.DATA_KEY, mRecipeStepDetailJsonStr);
        bundle.putInt(Step.POSITION_KEY, mRecipeStepDetailPosition);
        mStepDetailFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.step_detail_container, mStepDetailFragment)
                .commit();
        if(mConfig.smallestScreenWidthDp < 600){
            mStepListContainer.setVisibility(View.INVISIBLE);
            mStepDetailContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if(mConfig.smallestScreenWidthDp < 600){
            if(mStepDetailFragment instanceof StepDetailFragment){
                mRecipeStepDetailJsonStr = null;
                mRecipeStepDetailPosition = -1;
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(mStepDetailFragment)
                        .commit();
                mStepDetailFragment = null;
                mStepListContainer.setVisibility(View.VISIBLE);
                mStepDetailContainer.setVisibility(View.INVISIBLE);
            }
            else{
                super.onBackPressed();
            }
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
