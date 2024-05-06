package com.example.mealplanner;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.example.mealplanner.database.MealPlannerRepository;

import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.databinding.ActivityMealPlannerBinding;

import androidx.lifecycle.LiveData;


public class MealPlannerActivity extends AppCompatActivity {
    public static final String PREF_NAME = "MealPlannerPrefs";
    public static final String KEY_SELECTED_RECIPES = "selected_recipes";
    public static final String KEY_SELECTED_RECIPE_ID = "selected_recipe_id";
    public static final String KEY_SELECTED_DAY = "selected_day";
    public static final String KEY_SELECTED_TIME = "selected_time";
    public static final String SHARED_PREFERENCE_USERID_KEY = "com.example.mealplanner.SHARED_PREFERENCE_USERID_KEY";
    private static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.mealplanner.SHARED_PREFERENCE_USERID_VALUE";
    MealPlannerRepository repository;
    private int loggedInUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.mealplanner.databinding.ActivityMealPlannerBinding binding = ActivityMealPlannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealPlannerRepository.getRepository(getApplication());

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, -1);

        Log.d("MealPlannerActivityLogin", "Logged in user ID: " + loggedInUserId);



        String[] daysOfWeek = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        String[] mealTimes = {"breakfast", "lunch", "dinner"};

        for (String day : daysOfWeek) {
            for (String mealTime : mealTimes) {
                int recipeId = getSelectedRecipeId(day, mealTime);
                observeRecipeChanges(recipeId, day, mealTime);
            }
        }


        binding.toolbar.setNavigationIcon(R.drawable.arrow);
        binding.toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getApplicationContext(),LandingPageActivity.class)
                .putExtra("userId", loggedInUserId)));


        binding.monBFragment.setOnClickListener(v -> {
            // Open RecipesActivity to select a recipe
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "monday")
                    .putExtra("time", "breakfast"));
        });
        binding.monLFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "monday")
                .putExtra("time", "lunch")));
        binding.monDFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "monday")
                .putExtra("time", "dinner")));
        binding.tueBFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "tuesday")
                .putExtra("time", "breakfast")));
        binding.tueLFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "tuesday")
                .putExtra("time", "lunch")));
        binding.tueDFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "tuesday")
                .putExtra("time", "dinner")));
        binding.wedBFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "wednesday")
                .putExtra("time", "breakfast")));
        binding.wedLFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "wednesday")
                .putExtra("time", "lunch")));
        binding.wedDFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "wednesday")
                .putExtra("time", "dinner")));
        binding.thuBFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "thursday")
                .putExtra("time", "breakfast")));
        binding.thuLFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "thursday")
                .putExtra("time", "lunch")));
        binding.thuDFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "thursday")
                .putExtra("time", "dinner")));
        binding.friBFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "friday")
                .putExtra("time", "breakfast")));
        binding.friLFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "friday")
                .putExtra("time", "lunch")));
        binding.friDFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "friday")
                .putExtra("time", "dinner")));
        binding.satBFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "saturday")
                .putExtra("time", "breakfast")));
        binding.satLFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "saturday")
                .putExtra("time", "lunch")));
        binding.satDFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "saturday")
                .putExtra("time", "dinner")));
        binding.sunBFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "sunday")
                .putExtra("time", "breakfast")));
        binding.sunLFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "sunday")
                .putExtra("time", "lunch")));
        binding.sunDFragment.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                .putExtra("userId", loggedInUserId)
                .putExtra("day", "sunday")
                .putExtra("time", "dinner")));


    }

    private void observeRecipeChanges(int recipeId, String day, String time) {
        LiveData<Recipe> recipeObserver = repository.getRecipeById(recipeId);
        recipeObserver.observe(this, recipe -> {
            RecipeFragment recipeFragment = findRecipeFragment(day, time);
            if (recipeFragment != null) {
                recipeFragment.updateRecipe(recipe);
            }
        });
    }



    private RecipeFragment findRecipeFragment(String day, String time) {
        if (day != null && time != null) {
            switch (time) {
                case "breakfast":
                    switch (day) {
                        case "monday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.mon_b_fragment);
                        case "tuesday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.tue_b_fragment);
                        case "wednesday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.wed_b_fragment);
                        case "thursday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.thu_b_fragment);
                        case "friday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.fri_b_fragment);
                        case "saturday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.sat_b_fragment);
                        case "sunday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.sun_b_fragment);
                    }
                    break;
                case "lunch":
                    switch (day) {
                        case "monday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.mon_l_fragment);
                        case "tuesday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.tue_l_fragment);
                        case "wednesday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.wed_l_fragment);
                        case "thursday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.thu_l_fragment);
                        case "friday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.fri_l_fragment);
                        case "saturday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.sat_l_fragment);
                        case "sunday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.sun_l_fragment);
                    }
                    break;
                case "dinner":
                    switch (day) {
                        case "monday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.mon_d_fragment);
                        case "tuesday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.tue_d_fragment);
                        case "wednesday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.wed_d_fragment);
                        case "thursday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.thu_d_fragment);
                        case "friday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.fri_d_fragment);
                        case "saturday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.sat_d_fragment);
                        case "sunday":
                            return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.sun_d_fragment);
                    }
                    break;
            }
        }
        return null;
    }

    private int getSelectedRecipeId(String day, String time) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String key = KEY_SELECTED_RECIPE_ID +"_"+ loggedInUserId + "_" + day + "_" + time;
        return sharedPreferences.getInt(key, 1); // 1 is the default value if the key doesn't exist
    }




}
