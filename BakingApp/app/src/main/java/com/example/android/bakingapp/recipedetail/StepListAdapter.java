package com.example.android.bakingapp.recipedetail;

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
import com.example.android.bakingapp.data.Step;

import java.util.List;

/**
 * Created by lgpc on 2018-03-03.
 */

public class StepListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Step> mStepList;
    private StepListOnClickListener mClickListener;

    public StepListAdapter(Context context, List<Step> stepList, StepListOnClickListener clickListener) {
        this.mContext = context;
        this.mStepList = stepList;
        this.mClickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_step_summary, parent, false);
        return new StepListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mStepList == null)
            return;
        ((StepListViewHolder)holder).bind(mStepList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mStepList == null)
            return 0;
        return mStepList.size();
    }

    interface StepListOnClickListener{
        void onStepClick(List<Step> stepList, int position);
    }

    class StepListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mStepThumbnail;
        private TextView mStepShortDesc;

        public StepListViewHolder(View itemView) {
            super(itemView);
            mStepThumbnail = itemView.findViewById(R.id.iv_step_thumbnail);
            mStepShortDesc = itemView.findViewById(R.id.tv_step_short_desc);
            itemView.setOnClickListener(this);
        }

        public void bind(Step step){
            if(!TextUtils.isEmpty(step.getThumbnailURL())){
                Glide.with(mContext).load(step.getThumbnailURL()).into(mStepThumbnail);
                mStepThumbnail.setVisibility(View.VISIBLE);
            }
            else{
                mStepThumbnail.setVisibility(View.GONE);
            }
            mStepShortDesc.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickListener.onStepClick(mStepList, position);
        }
    }
}
