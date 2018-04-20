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

public class RecipeDetailsFragment extends Fragment {
    public RecipeDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        return rootView;
    }
}
