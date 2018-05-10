package com.kfugosic.bakingapp;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kfugosic.bakingapp.models.Step;
import com.kfugosic.bakingapp.utils.AppUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


/**
 * Created by Kristijan on 09-May-18.
 */

@RunWith(AndroidJUnit4.class)
public class StepDetailsActivityTest {

    private static final String secondStepDesc = "second";

    @Rule
    public ActivityTestRule<StepDetailsActivity> mActivityRule = new ActivityTestRule<StepDetailsActivity>(StepDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();

            Step s1 = new Step(0, "1st", "first", null, null);
            Step s2 = new Step(1, "2nd", secondStepDesc, null, null);
            List<Step> sampleStepList = new ArrayList<>();
            sampleStepList.add(s1);
            sampleStepList.add(s2);

            intent.putExtra(AppUtils.CURRENT_STEP_INDEX_KEY, 0);
            intent.putExtra(AppUtils.ALL_STEPS_KEY, Parcels.wrap(sampleStepList));
            return intent;
        }
    };

    @Test
    public void clickNextButton_ShowsNextStep() {

        onView(ViewMatchers.withId(R.id.btn_next))
                .perform(click());

        onView(withId(R.id.tv_step_desc)).check(matches(withText(secondStepDesc)));

    }

    @Test
    public void firstStepDoesntHavePrevButton() {

        onView(withId(R.id.btn_previous)).check(matches(not(isDisplayed())));

    }

    @Test
    public void lastStepDoesntHaveNextButton() {

        onView(ViewMatchers.withId(R.id.btn_next))
                .perform(click());

        onView(withId(R.id.tv_step_desc)).check(matches(withText(secondStepDesc)));

        onView(withId(R.id.btn_next)).check(matches(not(isDisplayed())));

        onView(withId(R.id.btn_previous)).check(matches(isDisplayed()));

    }

}
