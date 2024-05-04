package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner.databinding.ActivityMealPlannerBinding;


public class MealPlannerActivity extends AppCompatActivity {
    private ActivityMealPlannerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealPlannerBinding.inflate(getLayoutInflater());
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

    }
}
