package com.example.android.bakingapp.recipedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Ingredient;

import java.util.List;

/**
 * Created by lgpc on 2018-03-03.
 */

public class IngredientListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Ingredient> mIngredientList;

    public IngredientListAdapter(Context context, List<Ingredient> ingredientList){
        this.mContext = context;
        this.mIngredientList = ingredientList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_ingredient, parent, false);
        return new IngredientListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mIngredientList == null || mIngredientList.size() == 0)
            return;
        ((IngredientListViewHolder)holder).bind(mIngredientList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mIngredientList == null)
            return 0;
        return mIngredientList.size();
    }

    class IngredientListViewHolder extends RecyclerView.ViewHolder{

        private TextView mIngredientSummary;

        public IngredientListViewHolder(View itemView) {
            super(itemView);
            mIngredientSummary = itemView.findViewById(R.id.tv_ingredient_summary);
        }

        public void bind(Ingredient ingredient){
            mIngredientSummary.setText(String.format(mContext.getString(R.string.ingredient_format),
                    ingredient.getIngredient(), ingredient.getQuantity(), ingredient.getMeasure()));
        }
    }
}
