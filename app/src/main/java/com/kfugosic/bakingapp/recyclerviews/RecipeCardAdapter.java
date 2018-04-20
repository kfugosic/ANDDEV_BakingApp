package com.kfugosic.bakingapp.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kfugosic.bakingapp.R;
import com.kfugosic.bakingapp.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kristijan on 20-Apr-18.
 */

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.MovieCardViewHolder>{

    private RecipeCardClickListener mRecipeCardClickListener;
    private List<Recipe> mRecipes;

    public RecipeCardAdapter(RecipeCardClickListener recipeCardClickListener, List<Recipe> recipes) {
        this.mRecipeCardClickListener = recipeCardClickListener;
        this.mRecipes = recipes;
    }

    @Override
    public MovieCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_card, parent, false);
        return new MovieCardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieCardViewHolder holder, int position) {
        Recipe current = mRecipes.get(position);
        holder.setRecipeText(current.getName());
    }

    @Override
    public int getItemCount() {
        if(mRecipes == null) {
            return 0;
        }
        return mRecipes.size();
    }


    public class MovieCardViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        @BindView(R.id.recipe_card_text) TextView recipeNameTextView;

        public MovieCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recipeNameTextView.setOnClickListener(this);
        }

        private void setRecipeText(String text) {
            recipeNameTextView.setText(text);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mRecipeCardClickListener.onListItemClick(mRecipes.get(position));
        }
    }

}
