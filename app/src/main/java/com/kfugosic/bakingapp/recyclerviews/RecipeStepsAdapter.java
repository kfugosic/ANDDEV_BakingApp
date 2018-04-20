package com.kfugosic.bakingapp.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kfugosic.bakingapp.R;
import com.kfugosic.bakingapp.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kristijan on 20-Apr-18.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder>{

    private RecipeStepClickListener mRecipeStepClickListener;
    private List<Step> mSteps;

    public RecipeStepsAdapter(RecipeStepClickListener recipeStepClickListener, List<Step> steps) {
        this.mRecipeStepClickListener = recipeStepClickListener;
        this.mSteps = steps;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_step_view, parent, false);
        return new StepViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step current = mSteps.get(position);
        holder.setRecipeText(current.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(mSteps == null) {
            return 0;
        }
        return mSteps.size();
    }


    public class StepViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        @BindView(R.id.tv_recipe_step) TextView recipeStepTextView;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recipeStepTextView.setOnClickListener(this);
        }

        private void setRecipeText(String text) {
            recipeStepTextView.setText(text);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mRecipeStepClickListener.onListItemClick(position);
        }
    }

}
