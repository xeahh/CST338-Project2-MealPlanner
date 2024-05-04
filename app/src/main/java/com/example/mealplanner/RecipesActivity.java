package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.databinding.ActivityRecipesBinding;
import com.example.mealplanner.viewHolders.RecipeAdapter;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {
    private ActivityRecipesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        int loggedInUserId = getIntent().getIntExtra("userId", -1);

        binding.toolbar.setNavigationIcon(R.drawable.arrow);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandingPageActivity.class)
                        .putExtra("userId", loggedInUserId));
            }
        });

        RecyclerView recyclerView = binding.recipesRecyclerview;
        final RecipeAdapter adapter = new RecipeAdapter(new RecipeAdapter.RecipeDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Recipe> recipeList = new ArrayList<>();

        recipeList.add(new Recipe(R.drawable.oatmeal, "Oatmeal"));
        recipeList.add(new Recipe(R.drawable.baconandeggs, "Bacon and Eggs"));
        recipeList.add(new Recipe(R.drawable.turkeyhummus, "Turkey and Hummus Wrap"));
        recipeList.add(new Recipe(R.drawable.chickentacos, "Chicken Tacos"));
        recipeList.add(new Recipe(R.drawable.toast, "Toast"));

        adapter.submitList(recipeList);
    }
}
