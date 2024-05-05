package com.example.mealplanner.viewHolders;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.database.entities.User;


public class RecipeAdapter extends ListAdapter<Recipe, RecipeViewHolder> {
    private OnItemClickListener listener;
    private User user;

    public RecipeAdapter(@NonNull DiffUtil.ItemCallback<Recipe> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RecipeViewHolder.create(parent, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe current = getItem(position);
        holder.bind(current, user);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onItemClick(int position);
    }

    public static class RecipeDiff extends DiffUtil.ItemCallback<Recipe> {

        @Override
        public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.equals(newItem);
        }
    }
}
