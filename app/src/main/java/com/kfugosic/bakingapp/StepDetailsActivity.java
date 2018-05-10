package com.kfugosic.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.kfugosic.bakingapp.models.Step;
import com.kfugosic.bakingapp.ui.StepDetailsFragment;
import com.kfugosic.bakingapp.utils.AppUtils;

import org.parceler.Parcels;

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

        int index = getIntent().getIntExtra(AppUtils.CURRENT_STEP_INDEX_KEY, -1);
        ArrayList<Step> steps = Parcels.unwrap(getIntent().getParcelableExtra(AppUtils.ALL_STEPS_KEY));

        setTitle(steps.get(index).getShortDescription());

        if(savedInstanceState == null) {

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.initializeFragmentData(steps, index);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }
    }

}
