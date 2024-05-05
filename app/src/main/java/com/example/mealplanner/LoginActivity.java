package com.example.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.User;
import com.example.mealplanner.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private MealPlannerRepository repository;
    private int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (repository == null) {
            Log.e("LoginActivity", "MealPlannerRepository is null");
        }

        repository = MealPlannerRepository.getRepository(getApplication());

        if (repository == null) {
            Log.e("LoginActivity", "Failed to initialize MealPlannerRepository");
        } else {
            Log.d("LoginActivity", "MealPlannerRepository initialized successfully");
        }
        binding.loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void verifyUser() {
        String username = binding.editUsername.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username may not be blank");
            return;
        }
        if (repository == null) {
            toastMaker("Repository is not initialized");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this,user -> {
            if (user != null) {
                String password = binding.editPassword.getText().toString();
                if (password.equals(user.getPassword())){
                    loggedInUserId = user.getId();
                    Log.d("MyTag", "user.getId() "+user.getId());
                    startActivity(new Intent(LoginActivity.this, LandingPageActivity.class)
                            .putExtra("userId", loggedInUserId));
                    finish();
                    clearSelectedRecipes();
                } else {
                    toastMaker("Invalid password.");
                    binding.editPassword.setSelection(0);
                }
            } else {
                toastMaker(String.format("%s is not a valid username", username));
                binding.editUsername.setSelection(0);
            }
            userObserver.removeObservers(this);
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearSelectedRecipes() {
        SharedPreferences preferences = getSharedPreferences(MealPlannerActivity.PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().remove(MealPlannerActivity.KEY_SELECTED_RECIPES).apply();
    }
}
