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
import com.example.mealplanner.viewHolders.RecipeViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;


public class MealPlannerActivity extends AppCompatActivity {
    public static final String PREF_NAME = "MealPlannerPrefs";
    public static final String KEY_SELECTED_RECIPES = "selected_recipes";
    public static final String KEY_SELECTED_RECIPE_ID = "selected_recipe_id";
    public static final String KEY_SELECTED_DAY = "selected_day";
    public static final String KEY_SELECTED_TIME = "selected_time";
    private ActivityMealPlannerBinding binding;
    private RecipeViewModel recipeViewModel;
    private int loggedInUserId;
    private static MealPlannerRepository repository;
    private Map<String, Map<String, RecipeFragment>> fragmentMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMealPlannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealPlannerRepository.getRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra("userId", -1);
        Log.d("MealPlannerActivityLogin", "Logged in user ID: " + loggedInUserId);

//        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);



//        recipeId = getIntent().getIntExtra("selected_recipe", 0);
//        day = getIntent().getStringExtra("day");
//        time = getIntent().getStringExtra("time");

        int recipeIdMondayBreakfast = getSelectedRecipeId("monday", "breakfast");
        Log.d("MealPlannerActivity","recipe id breakfast: "+recipeIdMondayBreakfast);
        int recipeIdMondayLunch = getSelectedRecipeId("monday", "lunch");
        Log.d("MealPlannerActivity","recipe id lunc: "+recipeIdMondayLunch);

        Log.d("MealPlannerActivity", "Before calling updateSelectedRecipes()");

        LiveData<Recipe> recipeObserver = repository.getRecipeById(recipeIdMondayBreakfast);
        recipeObserver.observe(this, recipe -> {
            Log.d("MealPlannerActivity", "recipeviewmodel.getrecipe");
                RecipeFragment recipeFragment = findRecipeFragment("monday", "breakfast");
                if (recipeFragment != null) {
                    recipeFragment.updateRecipe(recipe);
                    Log.d("MealPlannerActivity", "after calling breakfast updateSelectedRecipes()");
                }

        });

        recipeObserver = repository.getRecipeById(recipeIdMondayLunch);
        recipeObserver.observe(this, recipe -> {
            RecipeFragment recipeFragment = findRecipeFragment("monday", "lunch");
            if (recipeFragment != null) {
                recipeFragment.updateRecipe(recipe);
                Log.d("MealPlannerActivity", "after calling lunch updateSelectedRecipes()");
            }
        });


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
        binding.monLFragment.setOnClickListener(v -> {
                    startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                            .putExtra("userId", loggedInUserId)
                            .putExtra("day", "monday")
                            .putExtra("time", "lunch"));
        });
        binding.monDFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "monday")
                    .putExtra("time", "dinner"));
        });
        binding.tueBFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "tuesday")
                    .putExtra("time", "breakfast"));
        });
        binding.tueLFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "tuesday")
                    .putExtra("time", "lunch"));
        });
        binding.tueDFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "tuesday")
                    .putExtra("time", "dinner"));
        });
        binding.wedBFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "wednesday")
                    .putExtra("time", "breakfast"));
        });
        binding.wedLFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "wednesday")
                    .putExtra("time", "lunch"));
        });
        binding.wedDFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "wednesday")
                    .putExtra("time", "dinner"));
        });
        binding.thuBFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "thursday")
                    .putExtra("time", "breakfast"));
        });
        binding.thuLFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "thursday")
                    .putExtra("time", "lunch"));
        });
        binding.thuDFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "thursday")
                    .putExtra("time", "dinner"));
        });
        binding.friBFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "friday")
                    .putExtra("time", "breakfast"));
        });
        binding.friLFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "friday")
                    .putExtra("time", "lunch"));
        });
        binding.friDFragment.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RecipesActivity.class)
                    .putExtra("userId", loggedInUserId)
                    .putExtra("day", "friday")
                    .putExtra("time", "dinner"));
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
                    }
                    break;
            }
        }
        return null;
    }

    private void displaySelectedRecipeFragments(int recipeId, String day, String time) {
        for (Map<String, RecipeFragment> timeMap : fragmentMap.values()) {
            for (RecipeFragment fragment : timeMap.values()) {
                if (fragment != null) {
                    recipeViewModel.getRecipeById(recipeId).observe(this, recipe -> {
                        if (recipe != null) {
                            fragment.updateRecipe(recipe);
                        }
                    });
                }
            }
        }
    }

    private int getSelectedRecipeId(String day, String time) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String key = KEY_SELECTED_RECIPE_ID + "_" + day + "_" + time;
        return sharedPreferences.getInt(key, 0); // 0 is the default value if the key doesn't exist
    }

    private void saveSelectedRecipe(int recipeId, String day, String time) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = KEY_SELECTED_RECIPE_ID + "_" + day + "_" + time;
        editor.putInt(key, recipeId);
        editor.apply();
    }


}
