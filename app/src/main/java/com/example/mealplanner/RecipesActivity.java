package com.example.mealplanner;

import static com.example.mealplanner.MealPlannerActivity.KEY_SELECTED_DAY;
import static com.example.mealplanner.MealPlannerActivity.KEY_SELECTED_RECIPE_ID;
import static com.example.mealplanner.MealPlannerActivity.KEY_SELECTED_TIME;
import static com.example.mealplanner.MealPlannerActivity.PREF_NAME;
import static com.example.mealplanner.database.MealPlannerRepository.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.RecipeDAO;
import com.example.mealplanner.database.entities.MealPlanner;
import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.database.entities.User;
import com.example.mealplanner.databinding.ActivityRecipesBinding;
import com.example.mealplanner.viewHolders.RecipeAdapter;
import com.example.mealplanner.viewHolders.RecipeViewModel;
import com.example.mealplanner.viewHolders.UserAdapter;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {
    private ActivityRecipesBinding binding;
    private MealPlannerRepository repository;
    private RecipeViewModel recipeViewModel;
    public static final String SHARED_PREFERENCE_USERID_KEY = "com.example.mealplanner.SHARED_PREFERENCE_USERID_KEY";
    private static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.mealplanner.SHARED_PREFERENCE_USERID_VALUE";
    private int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealPlannerRepository.getRepository(getApplication());
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, -1);

//        int loggedInUserId = getIntent().getIntExtra("userId", -1);
        String day = getIntent().getStringExtra("day");
        String time = getIntent().getStringExtra("time");

        binding.toolbar.setNavigationIcon(R.drawable.arrow);
        binding.toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LandingPageActivity.class)
                    .putExtra("userId", loggedInUserId));
        });

        RecyclerView recyclerView = binding.recipesRecyclerview;
        final RecipeAdapter adapter = new RecipeAdapter(new RecipeAdapter.RecipeDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeViewModel.getAllRecipes().observe(this, recipeList -> {
            adapter.submitList(recipeList);
        });

        adapter.setListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Recipe recipe = adapter.getCurrentList().get(position);
                recipeViewModel.deleteRecipe(recipe);
            }

            @Override
            public void onItemClick(int position) {
                Recipe recipe = adapter.getCurrentList().get(position);
                Log.i("DAY_AND_TIME", recipe.getName());
                if (day != null && time != null) {
                    saveSelectedRecipe(recipe.getId(), day, time);
                    startActivity(new Intent(getApplicationContext(), MealPlannerActivity.class)
                            .putExtra("userId", loggedInUserId));
                }
            }
        });
    }

    private void saveSelectedRecipe(int recipeId, String day, String time) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = KEY_SELECTED_RECIPE_ID+"_"+loggedInUserId + "_" + day + "_" + time;
        editor.putInt(key, recipeId);
        editor.apply();
    }



}
