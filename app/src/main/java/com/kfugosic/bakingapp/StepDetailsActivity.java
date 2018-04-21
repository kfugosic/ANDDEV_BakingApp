package com.kfugosic.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.kfugosic.bakingapp.models.Step;
import com.kfugosic.bakingapp.ui.StepDetailsFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);

        if(getIntent() == null){
            return;
        }
        int index = getIntent().getIntExtra(RecipeDetailsActivity.CURRENT_STEP_INDEX_KEY, -1);
        ArrayList<Step> steps = getIntent().getParcelableArrayListExtra(RecipeDetailsActivity.ALL_STEPS_KEY);

        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.initializeFragmentData(steps, index);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.step_details_container, stepDetailsFragment)
                .commit();
    }

//    @OnClick(R.id.btn_previous)
//    protected void showPrevious() {
//        Intent intent = new Intent(this, StepDetailsActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        intent.putParcelableArrayListExtra(RecipeDetailsActivity.ALL_STEPS_KEY, mSteps);
//        intent.putExtra(RecipeDetailsActivity.CURRENT_STEP_INDEX_KEY, mIndex - 1);
//        startActivity(intent);
//        finish();
//    }
//
//    @OnClick(R.id.btn_next)
//    protected void showNext() {
//        Intent intent = new Intent(this, StepDetailsActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        intent.putParcelableArrayListExtra(RecipeDetailsActivity.ALL_STEPS_KEY, mSteps);
//        intent.putExtra(RecipeDetailsActivity.CURRENT_STEP_INDEX_KEY, mIndex + 1);
//        startActivity(intent);
//        finish();
//    }

}
