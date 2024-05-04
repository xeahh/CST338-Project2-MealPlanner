package com.example.mealplanner.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.database.entities.Recipe;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private ImageView recipeViewItem;
    private TextView recipeName;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeViewItem = itemView.findViewById(R.id.image);
        recipeName = itemView.findViewById(R.id.recipe_name);
    }

    static RecipeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    public void bind(Recipe recipe) {
        recipeViewItem.setImageResource(recipe.getImage());
        recipeName.setText(recipe.getName());
    }
}
