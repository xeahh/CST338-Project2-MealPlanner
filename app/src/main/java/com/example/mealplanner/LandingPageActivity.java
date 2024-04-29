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


public class LandingPageActivity extends AppCompatActivity {
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
        setContentView(R.layout.landing_page);
        repository = MealPlannerRepository.getRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra("userId", LOGGED_OUT);
        Log.d("LandingPageActivity", "Received userId: " + loggedInUserId);

        loginUser(savedInstanceState);

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        Log.d("LandingPageActivity", "Using userId: " + loggedInUserId);

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                invalidateOptionsMenu();
                if (user.isAdmin()) {
                    Button adminButton = findViewById(R.id.users_button);
                    adminButton.setVisibility(View.VISIBLE);
                }
            }
            else {
                Intent intent = new Intent(LandingPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
