package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.User;
import com.example.mealplanner.databinding.ActivityCreateAccountBinding;


public class CreateAccountActivity extends AppCompatActivity  {
    private ActivityCreateAccountBinding binding;
    private MealPlannerRepository repository;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealPlannerRepository.getRepository(getApplication());

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
        binding.toolbar.setNavigationIcon(R.drawable.arrow);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
    private void verifyUser() {
        String username = binding.editUsername.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username may not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this,user -> {
            if (user == null) { // Username doesn't exist
                String password1 = binding.editPassword.getText().toString();
                if (password1.isEmpty()) {
                    toastMaker("Password may not be blank");
                    return;
                }
                String password2 = binding.confirmPassword.getText().toString();
                if (password1.equals(password2)) {
                    User newUser = new User(username, password1);
                    repository.insertUser(newUser);
                    startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                    toastMaker("Account successfully created");

                    finish();
                } else {
                    toastMaker("Passwords do not match");
                    binding.editPassword.setSelection(0);
                }
            } else { // Username already exists
                toastMaker("Username already exists");
            }
            userObserver.removeObservers(this);
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
