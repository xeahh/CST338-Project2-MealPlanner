package com.example.mealplanner;

import static com.example.mealplanner.database.MealPlannerRepository.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.databinding.ActivityMealPlannerBinding;
import com.example.mealplanner.viewHolders.RecipeViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MealPlannerActivity extends AppCompatActivity {
    public static final String PREF_NAME = "MealPlannerPrefs";
    public static final String KEY_SELECTED_RECIPES = "selected_recipes";
    private Map<String, Map<String, Integer>> selectedRecipesMap;
    private ActivityMealPlannerBinding binding;
    private RecipeFragment recipeFragment;
    private RecipeViewModel recipeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealPlannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealPlannerRepository.getRepository(getApplication());

        int loggedInUserId = getIntent().getIntExtra("userId", -1);
        Log.d("MealPlannerActivityLogin", "Logged in user ID: " + loggedInUserId);
        int recipeId = getIntent().getIntExtra("selected_recipe", 0);
        String day = getIntent().getStringExtra("day");
        String time = getIntent().getStringExtra("time");


        // Initialize ViewModel
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        loadSelectedRecipes(loggedInUserId);
        updateFragmentsWithSelectedRecipes();

        recipeViewModel.getRecipeById(recipeId).observe(this, recipe -> {
            if (recipe != null) {
                if (day!=null&&day.equals("monday")) {
                    if(time!=null&&time.equals("breakfast")) {
                        // Find the RecipeFragment by tag
                        RecipeFragment recipeFragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.mon_b_fragment);
                        // Check if the fragment is not null
                        if (recipeFragment != null) {
                            // Call the updateRecipe method
                            recipeFragment.updateRecipe(recipe);
                            updateSelectedRecipes(day, time, recipeId);
                            saveSelectedRecipes(loggedInUserId);
                        }
                    }
                    if(time!=null&&time.equals("lunch")) {
                        RecipeFragment recipeFragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.mon_l_fragment);

                        if (recipeFragment != null) {
                            recipeFragment.updateRecipe(recipe);
                            updateSelectedRecipes(day, time, recipeId);
                            saveSelectedRecipes(loggedInUserId);
                        }
                    }
                }
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


    }

    private void loadSelectedRecipes(int userId) {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME + "_" + userId, Context.MODE_PRIVATE);
        String selectedRecipesJson = preferences.getString(KEY_SELECTED_RECIPES, null);
        if (selectedRecipesJson != null) {
            selectedRecipesMap = parseSelectedRecipes(selectedRecipesJson);
        } else {
            selectedRecipesMap = new HashMap<>();
        }
    }

    private void saveSelectedRecipes(int userId) {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME + "_" + userId, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String selectedRecipesJson = formatSelectedRecipes(selectedRecipesMap);
        editor.putString(KEY_SELECTED_RECIPES, selectedRecipesJson);
        editor.apply();
    }

    // Method to parse selected recipes JSON string to map
    private Map<String, Map<String, Integer>> parseSelectedRecipes(String json) {
        Map<String, Map<String, Integer>> resultMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> daysIterator = jsonObject.keys();
            while (daysIterator.hasNext()) {
                String day = daysIterator.next();
                JSONObject dayObject = jsonObject.getJSONObject(day);
                Map<String, Integer> timeMap = new HashMap<>();
                Iterator<String> timesIterator = dayObject.keys();
                while (timesIterator.hasNext()) {
                    String time = timesIterator.next();
                    int recipeId = dayObject.getInt(time);
                    timeMap.put(time, recipeId);
                }
                resultMap.put(day, timeMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    // Method to format selected recipes map to JSON string
    private String formatSelectedRecipes(Map<String, Map<String, Integer>> selectedRecipesMap) {
        JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, Map<String, Integer>> dayEntry : selectedRecipesMap.entrySet()) {
                String day = dayEntry.getKey();
                Map<String, Integer> timeMap = dayEntry.getValue();
                JSONObject dayObject = new JSONObject();
                for (Map.Entry<String, Integer> timeEntry : timeMap.entrySet()) {
                    String time = timeEntry.getKey();
                    int recipeId = timeEntry.getValue();
                    dayObject.put(time, recipeId);
                }
                jsonObject.put(day, dayObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    // Method to update selected recipes in the map
    private void updateSelectedRecipes(String day, String time, int recipeId) {
        if (!selectedRecipesMap.containsKey(day)) {
            selectedRecipesMap.put(day, new HashMap<>());
        }
        selectedRecipesMap.get(day).put(time, recipeId);
    }

    // Method to get selected recipe for a given day and time
    private int getSelectedRecipe(String day, String time) {
        if (selectedRecipesMap.containsKey(day)) {
            Map<String, Integer> dayMap = selectedRecipesMap.get(day);
            if (dayMap.containsKey(time)) {
                return dayMap.get(time);
            }
        }
        return 0;
    }

    private void updateFragmentsWithSelectedRecipes() {
        for (Map.Entry<String, Map<String, Integer>> entry : selectedRecipesMap.entrySet()) {
            String day = entry.getKey();
            Map<String, Integer> timeMap = entry.getValue();
            for (Map.Entry<String, Integer> timeEntry : timeMap.entrySet()) {
                String time = timeEntry.getKey();
                int recipeId = timeEntry.getValue();
                // Get the RecipeFragment based on day and time, then update it
                updateRecipeFragment(day, time, recipeId);
            }
        }
    }


    private void updateRecipeFragment(String day, String time, int recipeId) {
        if (day != null && time != null && recipeId > 0) {
            RecipeFragment recipeFragment = findRecipeFragment(day, time);
            if (recipeFragment != null) {
                recipeViewModel.getRecipeById(recipeId).observe(this, recipe -> {
                    if (recipe != null) {
                        recipeFragment.updateRecipe(recipe);
                    }
                });
            }
        }
    }


    private RecipeFragment findRecipeFragment(String day, String time) {
        if (day != null && time != null) {
            if (day.equals("monday")) {
                if (time.equals("breakfast")) {
                    return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.mon_b_fragment);
                } else if (time.equals("lunch")) {
                    return (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.mon_l_fragment);
                }
            }
            // Add cases for other days and times as needed
        }
        return null;
    }

}
