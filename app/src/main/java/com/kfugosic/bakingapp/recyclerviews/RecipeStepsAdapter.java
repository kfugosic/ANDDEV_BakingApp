package com.kfugosic.bakingapp.recyclerviews;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kfugosic.bakingapp.R;
import com.kfugosic.bakingapp.RecipeDetailsActivity;
import com.kfugosic.bakingapp.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kristijan on 20-Apr-18.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder>{

    private int selectedPos = RecyclerView.NO_POSITION;
    private RecipeStepClickListener mRecipeStepClickListener;
    private List<Step> mSteps;
    private boolean mTwoPane;


    public RecipeStepsAdapter(RecipeStepClickListener recipeStepClickListener, List<Step> steps, boolean mTwoPane) {
        this.mRecipeStepClickListener = recipeStepClickListener;
        this.mSteps = steps;
        this.mTwoPane = mTwoPane;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_step_view, parent, false);
        return new StepViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StepViewHolder viewHolder, int position) {
        Step current = mSteps.get(position);
        viewHolder.setRecipeText(current.getShortDescription());
        if (mTwoPane) {
            viewHolder.recipeStepTextView.setBackgroundColor(selectedPos == position ? RecipeDetailsActivity.SELECTED_COLOR : Color.WHITE);
        }
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
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
        }
    }

    public void deselectAll() {
        notifyItemChanged(selectedPos);
        selectedPos = RecyclerView.NO_POSITION;
        notifyItemChanged(selectedPos);
    }

}
