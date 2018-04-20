package com.kfugosic.bakingapp.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kfugosic.bakingapp.R;

/**
 * Created by Kristijan on 16-Apr-18.
 */

public class StepDetailsFragment extends Fragment {

//    @BindView(R.id.tv_step_desc) protected TextView mStepDescription;
//    @BindView(R.id.btn_previous) protected Button mPreviousButton;
//    @BindView(R.id.btn_next) protected Button mNextButton;
//
//    private int mIndex;
//    private ArrayList<Step> mSteps;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        return rootView;
    }
}
