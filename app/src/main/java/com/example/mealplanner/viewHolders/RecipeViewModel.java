package com.example.mealplanner.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.Recipe;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private MealPlannerRepository repository;
    private final LiveData<List<Recipe>> allRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        repository = MealPlannerRepository.getRepository(application);
        allRecipes = repository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }
}
