package com.kfugosic.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kfugosic.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_step_desc) protected TextView mStepDescription;
    @BindView(R.id.btn_previous) protected Button mPreviousButton;
    @BindView(R.id.btn_next) protected Button mNextButton;

    private int mIndex;
    private ArrayList<Step> mSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);

        if(getIntent() == null){
            return;
        }
        mIndex = getIntent().getIntExtra(RecipeDetailsActivity.CURRENT_STEP_INDEX_KEY, -1);
        mSteps = getIntent().getParcelableArrayListExtra(RecipeDetailsActivity.ALL_STEPS_KEY);
        mStepDescription.setText(mSteps.get(mIndex).getDescription());

        if(mIndex == (mSteps.size() - 1)) {
            mNextButton.setVisibility(View.GONE);
        }
        if(mIndex == 0) {
            mPreviousButton.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_previous)
    protected void showPrevious() {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putParcelableArrayListExtra(RecipeDetailsActivity.ALL_STEPS_KEY, mSteps);
        intent.putExtra(RecipeDetailsActivity.CURRENT_STEP_INDEX_KEY, mIndex - 1);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_next)
    protected void showNext() {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putParcelableArrayListExtra(RecipeDetailsActivity.ALL_STEPS_KEY, mSteps);
        intent.putExtra(RecipeDetailsActivity.CURRENT_STEP_INDEX_KEY, mIndex + 1);
        startActivity(intent);
        finish();
    }

}
