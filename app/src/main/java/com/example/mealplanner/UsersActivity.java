package com.example.mealplanner;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.User;
import com.example.mealplanner.databinding.ActivityUsersBinding;
import com.example.mealplanner.viewHolders.UserAdapter;
import com.example.mealplanner.viewHolders.UsersViewModel;

public class UsersActivity extends AppCompatActivity {
    private ActivityUsersBinding binding;
    private MealPlannerRepository repository;
    private UsersViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.mealplanner.databinding.ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UsersViewModel.class);


        RecyclerView recyclerView = binding.recyclerView;
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = MealPlannerRepository.getRepository(getApplication());

        int loggedInUserId = getIntent().getIntExtra("userId", -1);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersActivity.this, LandingPageActivity.class)
                        .putExtra("userId", loggedInUserId);
                startActivity(intent);
                finish();
            }
        });

        userViewModel.getAllUsers().observe(this, userList -> {
            adapter.submitList(userList);
        });

        adapter.setListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                User userToDelete = adapter.getCurrentList().get(position);
                userViewModel.deleteUser(userToDelete);
            }
        });
    }
}
