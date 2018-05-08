package com.example.android.bakingapp.recipedetail;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgpc on 2018-04-28.
 */

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener{
    private Configuration mConfig;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStatebuilder;
    private ImageView mThumnailImageView;
    private TextView mShortDescTextView;
    private TextView mStepDetailDescTextView;
    private Button mStepDetailPrevButton;
    private Button mStepDetailNextButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        mConfig = getResources().getConfiguration();
        String stepJsonStr = getArguments().getString(Step.DATA_KEY);
        int position = getArguments().getInt(Step.POSITION_KEY);
        Type stepListType = new TypeToken<ArrayList<Step>>(){}.getType();
        List<Step> stepList = new Gson().fromJson(stepJsonStr, stepListType);

        mExoPlayerView = rootView.findViewById(R.id.step_detail_player);
        mThumnailImageView = rootView.findViewById(R.id.step_detail_image);
        mShortDescTextView = rootView.findViewById(R.id.step_detail_shortdesc);
        mStepDetailDescTextView = rootView.findViewById(R.id.step_detail_desc);
        mStepDetailPrevButton = rootView.findViewById(R.id.step_detail_prev);
        mStepDetailNextButton = rootView.findViewById(R.id.step_detail_next);

        if(!TextUtils.isEmpty(stepList.get(position).getVideoURL())){
            initializePlayer(Uri.parse(stepList.get(position).getVideoURL()));
        }
        else if(!TextUtils.isEmpty(stepList.get(position).getThumbnailURL())){
            initializeThumnailImageView(Uri.parse(stepList.get(position).getThumbnailURL()));
        }
        else{
            initializeShortDescTextView(stepList.get(position).getShortDescription());
        }

        initializeStepDetailTextView(stepList.get(position).getDescription());

        if(mConfig.smallestScreenWidthDp < 600)
            initializeStepDetailPrevNext(stepList, position);

        return rootView;
    }

    @Override
    public void onPause() {
        if(mExoPlayer != null)
            mExoPlayer.stop();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        if(mExoPlayer != null){
            mExoPlayer.release();
            mExoPlayer = null;
        }
        super.onDestroyView();
    }

    private void initializePlayer(Uri mediaUri){
        if(mExoPlayer == null){
            mExoPlayerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void initializeThumnailImageView(Uri thumnailUrl){
        mThumnailImageView.setVisibility(View.VISIBLE);
        Glide.with(this).load(thumnailUrl).into(mThumnailImageView);
    }

    private void initializeShortDescTextView(String shortDesc){
        mShortDescTextView.setVisibility(View.VISIBLE);
        mShortDescTextView.setText(shortDesc);
    }

    private void initializeStepDetailTextView(String stepDetailDesc){
        if(mStepDetailDescTextView != null)
            mStepDetailDescTextView.setText(stepDetailDesc);
    }

    private void initializeStepDetailPrevNext(final List<Step> stepList, final int position){
        if(mStepDetailPrevButton != null && mStepDetailNextButton != null) {
            if (position > 0) {
                mStepDetailPrevButton.setVisibility(View.VISIBLE);
                mStepDetailPrevButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((StepListAdapter.StepListOnClickListener) StepDetailFragment.this.getActivity()).onStepClick(stepList, position - 1);
                    }
                });
            }

            if (position < stepList.size() - 1) {
                mStepDetailNextButton.setVisibility(View.VISIBLE);
                mStepDetailNextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((StepListAdapter.StepListOnClickListener) StepDetailFragment.this.getActivity()).onStepClick(stepList, position + 1);
                    }
                });
            }
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
