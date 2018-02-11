package com.example.android.bakingapp.recipelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeSummary;

import java.util.List;

/**
 * Created by lgpc on 2018-02-11.
 */

public class RecipeListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<RecipeSummary> mRecipeSummaryList;

    public RecipeListAdapter(Context context, List<RecipeSummary> recipeSummaryList) {
        this.mContext = context;
        this.mRecipeSummaryList = recipeSummaryList;
    }

    public void setRecipeSummaryList(List<RecipeSummary> recipeSummaryList){
        this.mRecipeSummaryList = recipeSummaryList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_recipe_summary, parent, false);
        return new RecipeListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecipeListViewHolder)holder).bind(mRecipeSummaryList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipeSummaryList.size();
    }

    class RecipeListViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIvRecipeImage;
        private TextView mTvRecipeName;
        private TextView mTvRecipeServings;

        public RecipeListViewHolder(View itemView) {
            super(itemView);
            mIvRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            mTvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            mTvRecipeServings = itemView.findViewById(R.id.tv_recipe_servings);
        }

        public void bind(RecipeSummary recipeSummary){
            if(!TextUtils.isEmpty(recipeSummary.getImage())){
                mIvRecipeImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(recipeSummary.getImage()).into(mIvRecipeImage);
            }
            else{
                mIvRecipeImage.setVisibility(View.GONE);
            }

            mTvRecipeName.setText(recipeSummary.getName());
            mTvRecipeServings.setText(String.valueOf(recipeSummary.getServings()));
        }
    }
}
