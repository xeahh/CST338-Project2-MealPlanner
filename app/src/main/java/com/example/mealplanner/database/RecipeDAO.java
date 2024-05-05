package com.example.mealplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplanner.database.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe... recipe);

    @Query("SELECT * FROM "+MealPlannerDatabase.RECIPE_TABLE + " ORDER BY name")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("DELETE FROM "+MealPlannerDatabase.RECIPE_TABLE)
    void deleteAll();

    @Delete
    void delete(Recipe recipe);
}
