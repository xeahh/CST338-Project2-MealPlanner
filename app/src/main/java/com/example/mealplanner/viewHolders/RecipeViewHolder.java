package com.example.mealplanner.viewHolders;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.database.entities.User;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private ImageView recipeViewItem;
    private TextView recipeName;
    private TextView ingredientsTextView;
    private Button deleteButton;

    public RecipeViewHolder(@NonNull View itemView, RecipeAdapter.OnItemClickListener listener) {
        super(itemView);
        recipeViewItem = itemView.findViewById(R.id.image);
        recipeName = itemView.findViewById(R.id.recipe_name);
        ingredientsTextView = itemView.findViewById(R.id.ingredients);
        deleteButton = itemView.findViewById(R.id.deleteRecipe);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position);
                }
            }
        });
    }

    static RecipeViewHolder create(ViewGroup parent, RecipeAdapter.OnItemClickListener listener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view, listener);
    }

    public void bind(Recipe recipe, User user) {
        recipeViewItem.setImageResource(recipe.getImage());
        recipeName.setText(recipe.getName());

        ingredientsTextView.setText("Ingredients: ");

    }
}
