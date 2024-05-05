package com.example.mealplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.databinding.ActivityMealPlannerBinding;


public class MealPlannerActivity extends AppCompatActivity {
    private ActivityMealPlannerBinding binding;
    private int loggedInUserId;
    private int selectedRecipeId;
    private static final int REQUEST_CODE_EDIT_RECIPE = 1;
    private MealPlannerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealPlannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealPlannerRepository.getRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra("userId", -1);
        selectedRecipeId = getIntent().getIntExtra("selectedRecipeId", 0);


        binding.toolbar.setNavigationIcon(R.drawable.arrow);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandingPageActivity.class)
                        .putExtra("userId", loggedInUserId));
            }
        });

        binding.myFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRecipesActivity("Monday");
            }
        });
        Log.i("mealactivity", String.valueOf(getIntent().getIntExtra("selectedRecipeId", 0)));

        if (getIntent().hasExtra("selectedRecipeId")) {
            LiveData<Recipe> selectedRecipeLiveData = repository.getRecipeById(selectedRecipeId);
            selectedRecipeLiveData.observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(Recipe selectedRecipe) {
                    Log.i("myfragmentm", selectedRecipe.toString());
                    if (selectedRecipe != null) {
                        MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.myFragment);
                        if (fragment != null) {
                            fragment.updateRecipe(selectedRecipe);
                        }
                    }
                }
            });
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_EDIT_RECIPE && resultCode == RESULT_OK && data != null) {
//            // Check if the result is from the RecipesActivity
//            int selectedRecipeId = getIntent().getIntExtra("selectedRecipeId", 0);
//                // Fetch the recipe object using the ID from your repository or database
//                LiveData<Recipe> selectedRecipe = repository.getRecipeById(selectedRecipeId);
//                if (selectedRecipe != null) {
//                    MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.myFragment);
//                    if (fragment != null) {
//                        // Call a method in the fragment to update the recipe
//                        fragment.updateRecipe(selectedRecipe);
//                    }
//                }
//        }
//    }


    private void launchRecipesActivity(String day) {
        // Launch RecipesActivity with the selected day as an extra
        Intent intent = new Intent(MealPlannerActivity.this, RecipesActivity.class);
        intent.putExtra("userId", loggedInUserId);
        intent.putExtra("selectedDay", day);
        startActivity(intent);
    }
}
