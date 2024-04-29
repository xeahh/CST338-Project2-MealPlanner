package com.example.mealplanner.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.MainActivity;
import com.example.mealplanner.database.entities.MealPlanner;
import com.example.mealplanner.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MealPlannerRepository {
    private final MealPlannerDAO mealPlannerDAO;
    private final UserDAO userDAO;
    private ArrayList<MealPlanner> allLogs;

    public static MealPlannerRepository repository;

    public MealPlannerRepository(Application application) {
        MealPlannerDatabase db = MealPlannerDatabase.getDatabase(application);
        this.mealPlannerDAO = db.mealPlannerDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<MealPlanner>) this.mealPlannerDAO.getAllRecords();
    }
    public static MealPlannerRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<MealPlannerRepository> future = MealPlannerDatabase.databaseWriteExecutor.submit(
                new Callable<MealPlannerRepository>() {
                    @Override
                    public MealPlannerRepository call() throws Exception {
                        return new MealPlannerRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting MealPlannerRepository, thread error.");
        }
        return null;
    }

    public ArrayList<MealPlanner> getAllLogs() {
        Future<ArrayList<MealPlanner>> future = MealPlannerDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<MealPlanner>>() {
                    @Override
                    public ArrayList<MealPlanner> call() throws Exception {
                        return (ArrayList<MealPlanner>) mealPlannerDAO.getAllRecords();
                    }
                });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all MealPlanners in the repository");
        }
        return null;
    }

    public void insertMealPlanner(MealPlanner mealPlanner) {
        MealPlannerDatabase.databaseWriteExecutor.execute(() ->
        {
            mealPlannerDAO.insert(mealPlanner);
        });
    }

    public void insertUser(User... user) {
        MealPlannerDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUsername(username);
    }

    public LiveData<User> getUserByUserId(int loggedInUserId) {
        return userDAO.getUserById(loggedInUserId);
    }
}
