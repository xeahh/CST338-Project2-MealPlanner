package com.example.mealplanner;

import static com.example.mealplanner.database.MealPlannerRepository.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.User;
import com.example.mealplanner.databinding.ActivityLoginBinding;
import com.example.mealplanner.databinding.LandingPageBinding;


public class LandingPageActivity extends AppCompatActivity {
    private LandingPageBinding binding;
    private static final int LOGGED_OUT = -1;
    private static final String SHARED_PREFERENCE_USERID_KEY = "com.example.mealplanner.SHARED_PREFERENCE_USERID_KEY";
    private static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.mealplanner.SHARED_PREFERENCE_USERID_VALUE";
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.mealplanner.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.mealplanner.MAIN_ACTIVITY_USER_ID";
    private int loggedInUserId = -1;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = MealPlannerRepository.getRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra("userId", LOGGED_OUT);
        Log.d("LandingPageActivity", "Received userId: " + loggedInUserId);

        loginUser(savedInstanceState);

        binding.mealplannerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, MealPlannerActivity.class)
                        .putExtra("userId", loggedInUserId);
                startActivity(intent);
            }
        });
        binding.recipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageActivity.this, RecipesActivity.class)
                        .putExtra("userId", loggedInUserId));
            }
        });
        binding.usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, UsersActivity.class)
                        .putExtra("userId", loggedInUserId);
                startActivity(intent);
            }
        });
        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSharedPreferences();
                Intent intent = new Intent(LandingPageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);
        if (sharedPreferences.contains(SHARED_PREFERENCE_USERID_VALUE)){
            loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            Log.e("LandingPageActivity", "Failed to get userId");
            return;
        }

        // Save the user ID in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREFERENCE_USERID_VALUE, loggedInUserId);
        editor.apply();

        Log.d("LandingPageActivity", "Using userId: " + loggedInUserId);

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                Log.d("LandingPageActivity", "User loaded: " + user.toString());
                invalidateOptionsMenu();
                if (user.isAdmin()) {
                    binding.usersButton.setVisibility(View.VISIBLE);
                } else {
                    binding.usersButton.setVisibility(View.GONE);
                }
            }
            else {
                Intent intent = new Intent(LandingPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        loggedInUserId = LOGGED_OUT;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the observer to avoid memory leaks
        repository.getUserByUserId(loggedInUserId).removeObservers(this);
    }

}
