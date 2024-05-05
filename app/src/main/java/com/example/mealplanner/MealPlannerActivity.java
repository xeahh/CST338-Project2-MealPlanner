package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.databinding.ActivityMealPlannerBinding;


public class MealPlannerActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private ActivityMealPlannerBinding binding;
    private MyFragment myFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealPlannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int loggedInUserId = getIntent().getIntExtra("userId", -1);

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
                // Open RecipesActivity to select a recipe
                startActivity(new Intent(getApplicationContext(), RecipesActivity.class));
            }
        });

        myFragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.myFragment);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Recipe selectedRecipe = data.getParcelableExtra("selected_recipe");
            // Update the fragment with the selected recipe
            assert selectedRecipe != null;
            myFragment.updateRecipe(selectedRecipe);
        }
    }
}
