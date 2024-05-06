package com.example.mealplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.MealPlanner;
import com.example.mealplanner.databinding.ActivityMealPlannerLogsBinding;


import java.util.ArrayList;

public class MealPlannerLogsActivity extends AppCompatActivity {
    private ActivityMealPlannerLogsBinding binding;
    private MealPlannerRepository repository;
    private int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealPlannerLogsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealPlannerRepository.getRepository(getApplication());

        SharedPreferences sharedPreferences = getSharedPreferences(MealPlannerActivity.SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(MealPlannerActivity.SHARED_PREFERENCE_USERID_VALUE, -1);

        binding.toolbar.setNavigationIcon(R.drawable.arrow);
        binding.toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LandingPageActivity.class)
                .putExtra("userId", loggedInUserId)));

        ArrayList<MealPlanner> allLogs = repository.getAllLogs();

        StringBuilder sb = new StringBuilder();
        for(MealPlanner log : allLogs) {
            sb.append(log);
        }
        binding.logs.setText(sb.toString());

    }



}
