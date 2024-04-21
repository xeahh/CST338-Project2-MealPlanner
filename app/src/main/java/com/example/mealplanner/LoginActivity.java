package com.example.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.User;
import com.example.mealplanner.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private MealPlannerRepository repository;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealPlannerRepository.getRepository(getApplication());
        binding.loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verifyUser()) {
                    toastMaker("Invalid username or password");
                } else {
                    startActivity(new Intent(LoginActivity.this, LandingPageActivity.class));
                }

            }
        });



    }

    private boolean verifyUser() {
        String username = binding.loginButton2.getText().toString();
        if (username.isEmpty()) {
            toastMaker("Username may not be blank");
            return false;
        }
        User user = repository.getUserByUserName(username);
        if(user != null) {
            String password = binding.editPassword.getText().toString();
            if (password.equals(user.getPassword())) {
                return true;
            } else {
                toastMaker("Invalid password.");
                return false;
            }
        }
        toastMaker(String.format("No %s found", username));
        return false;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
