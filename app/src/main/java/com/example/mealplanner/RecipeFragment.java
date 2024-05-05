package com.example.mealplanner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.viewHolders.RecipeViewModel;

public class RecipeFragment extends Fragment {
    private RecipeViewModel recipeViewModel;

    public RecipeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

        } else {

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_item, container, false);
    }

    public void updateRecipe(Recipe selectedRecipe) {
        Log.i("myfragment", selectedRecipe.toString());
        TextView recipeNameTextView = getView().findViewById(R.id.recipe_name);
        recipeNameTextView.setText(selectedRecipe.getName());
        ImageView imageView = getView().findViewById(R.id.image);
        imageView.setImageResource(selectedRecipe.getImage());
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
