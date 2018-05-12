package com.kfugosic.bakingapp.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.kfugosic.bakingapp.R;
import com.kfugosic.bakingapp.models.Step;
import com.kfugosic.bakingapp.utils.LoadImageIntoViewTask;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Kristijan on 16-Apr-18.
 */

public class StepDetailsFragment extends Fragment {

    private final String RESUME_WINDOW = "resume_window";
    private final String RESUME_POSITION = "resume_position";
    private final String RESUME_PLAY_STATE = "resume_play_state";
    private static final String STEPS_LIST = "steps_list";
    public static final String STEP_INDEX = "step_index";

    @BindView(R.id.media_player_view) protected SimpleExoPlayerView mMediaPlayerView;
    @BindView(R.id.iv_thumbnail) protected ImageView mThumbnailView;
    @BindView(R.id.tv_step_desc) protected TextView mStepDescription;
    @BindView(R.id.btn_previous) protected Button mPreviousButton;
    @BindView(R.id.btn_next) protected Button mNextButton;

    private Integer mIndex;
    private ArrayList<Step> mSteps;
    private SimpleExoPlayer mExoPlayer;
    private boolean showButtons = true;
    private int mResumeWindow;
    private long mResumePosition;
    private boolean mResumePlayState;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(STEP_INDEX);
            mSteps = Parcels.unwrap(savedInstanceState.getParcelable(STEPS_LIST));
            mResumeWindow = savedInstanceState.getInt(RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(RESUME_POSITION);
            mResumePlayState = savedInstanceState.getBoolean(RESUME_PLAY_STATE);
        }

        if(mIndex != null && mSteps != null) {
            refreshFragment();
            refreshPlayer();
        }

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer != null) {
            mResumeWindow = mExoPlayer.getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayer.getCurrentPosition());
            mResumePlayState = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STEP_INDEX, mIndex);
        outState.putParcelable(STEPS_LIST, Parcels.wrap(mSteps));
        outState.putInt(RESUME_WINDOW, mResumeWindow);
        outState.putLong(RESUME_POSITION, mResumePosition);
        outState.putBoolean(RESUME_PLAY_STATE, mResumePlayState);
    }

    private void refreshFragment() {
        mStepDescription.setText(mSteps.get(mIndex).getDescription());
        if(showButtons && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            refreshButtons();
        }
        getActivity().setTitle(String.format("%d. %s", mIndex+1, mSteps.get(mIndex).getShortDescription()));
        mStepDescription.setVisibility(View.VISIBLE);
    }

    private void refreshButtons() {
        mNextButton.setVisibility(View.VISIBLE);
        mPreviousButton.setVisibility(View.VISIBLE);
        if(mIndex == (mSteps.size() - 1)) {
            mNextButton.setVisibility(View.GONE);
        }
        if(mIndex == 0) {
            mPreviousButton.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_previous)
    protected void showPrevious() {
        mIndex -= 1;
        refreshFragment();
        releasePlayer();
        refreshPlayer();
    }

    @OnClick(R.id.btn_next)
    protected void showNext() {
        mIndex += 1;
        refreshFragment();
        releasePlayer();
        refreshPlayer();
    }

    public void initializeFragmentData(ArrayList<Step> steps, int index) {
        mSteps = steps;
        mIndex = index;
    }

    public void setShowButtons(boolean showButtons) {
        this.showButtons = showButtons;
    }

    //
    // Media player
    //

    private void refreshPlayer() {
        final String currentVideoURL = mSteps.get(mIndex).getVideoURL();
        final String currentThumbnailURL = mSteps.get(mIndex).getThumbnailURL();
        mMediaPlayerView.setVisibility(View.GONE);
        mThumbnailView.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(currentVideoURL)) {
            if(mExoPlayer == null) {
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector(), new DefaultLoadControl());
                mExoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                mMediaPlayerView.setPlayer(mExoPlayer);
            }
            MediaSource mediaSource = new ExtractorMediaSource(
                    Uri.parse(currentVideoURL),
                    new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp")),
                    new DefaultExtractorsFactory(),
                    null,
                    null
            );

            mExoPlayer.prepare(mediaSource);

            if(mResumeWindow != C.INDEX_UNSET) {
                mExoPlayer.seekTo(mResumeWindow, mResumePosition);
            }
            mExoPlayer.setPlayWhenReady(mResumePlayState);

            mMediaPlayerView.setVisibility(View.VISIBLE);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !getResources().getBoolean(R.bool.isTablet)) {
                mStepDescription.setVisibility(View.GONE);
            }
        }
        if (currentThumbnailURL != null && !currentThumbnailURL.isEmpty()) {
            mThumbnailView.setVisibility(View.VISIBLE);
            new LoadImageIntoViewTask(getActivity(), mThumbnailView).execute(currentThumbnailURL);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

}
