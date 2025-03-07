package com.example.mealplanner.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.User;

import java.util.List;

public class UsersViewModel extends AndroidViewModel {
    private MealPlannerRepository repository;
    private final LiveData<List<User>> allUsers;
    public UsersViewModel(@NonNull Application application) {
        super(application);
        repository = MealPlannerRepository.getRepository(application);
        allUsers = repository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
    public void insert(User user) {
        repository.insertUser(user);
    }
    public void deleteUser(User user) {
        repository.deleteUser(user);
    }
}
