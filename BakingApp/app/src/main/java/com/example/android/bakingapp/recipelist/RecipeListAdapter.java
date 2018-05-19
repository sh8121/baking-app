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
    private RecipeItemClickListener mClickListener;

    public RecipeListAdapter(Context context, List<RecipeSummary> recipeSummaryList, RecipeItemClickListener clickListener) {
        this.mContext = context;
        this.mRecipeSummaryList = recipeSummaryList;
        this.mClickListener = clickListener;
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
        if(mRecipeSummaryList == null)
            return 0;
        return mRecipeSummaryList.size();
    }

    interface RecipeItemClickListener{
        void onClick(RecipeSummary recipeSummary);
    }

    class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mIvRecipeImage;
        private TextView mTvRecipeName;
        private TextView mTvRecipeServings;

        public RecipeListViewHolder(View itemView) {
            super(itemView);
            mIvRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            mTvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            mTvRecipeServings = itemView.findViewById(R.id.tv_recipe_servings);
            itemView.setOnClickListener(this);
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

        @Override
        public void onClick(View view) {
            if(mRecipeSummaryList == null || mRecipeSummaryList.size() == 0)
                return;
            int position = getAdapterPosition();
            mClickListener.onClick(mRecipeSummaryList.get(position));
        }
    }
}
