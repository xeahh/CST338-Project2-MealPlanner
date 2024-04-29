package com.example.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.User;
import com.example.mealplanner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.mealplanner.MAIN_ACTIVITY_USER_ID";
    private static final int LOGGED_OUT = -1;
    private ActivityMainBinding binding;
    public static final String TAG = "MEALPLANNER";
    int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.mealplanner.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "IT WORKED", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }


    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

}