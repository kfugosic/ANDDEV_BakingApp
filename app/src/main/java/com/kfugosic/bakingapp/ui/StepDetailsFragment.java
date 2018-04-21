package com.kfugosic.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kfugosic.bakingapp.R;
import com.kfugosic.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kristijan on 16-Apr-18.
 */

public class StepDetailsFragment extends Fragment {

    @BindView(R.id.tv_step_desc) protected TextView mStepDescription;
    @BindView(R.id.btn_previous) protected Button mPreviousButton;
    @BindView(R.id.btn_next) protected Button mNextButton;

    private Integer mIndex;
    private ArrayList<Step> mSteps;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        if(mIndex != null && mSteps != null) {
            refreshFragment();
        }

        return rootView;
    }

    private void refreshFragment() {
        mStepDescription.setText(mSteps.get(mIndex).getDescription());
        mNextButton.setVisibility(View.VISIBLE);
        mPreviousButton.setVisibility(View.VISIBLE);
        if(mIndex == (mSteps.size() - 1)) {
            mNextButton.setVisibility(View.GONE);
        }
        if(mIndex == 0) {
            mPreviousButton.setVisibility(View.GONE);
        }
    }

    public void initializeFragmentData(ArrayList<Step> steps, int index) {
        mSteps = steps;
        mIndex = index;
    }

    @OnClick(R.id.btn_previous)
    protected void showPrevious() {
        mIndex -= 1;
        refreshFragment();
    }

    @OnClick(R.id.btn_next)
    protected void showNext() {
        mIndex += 1;
        refreshFragment();
    }

}
