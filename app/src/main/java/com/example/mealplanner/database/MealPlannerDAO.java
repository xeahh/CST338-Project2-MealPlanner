package com.example.mealplanner.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplanner.database.entities.MealPlanner;

import java.util.List;

@Dao
public interface MealPlannerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealPlanner mealPlanner);

    @Query("SELECT * FROM " + MealPlannerDatabase.MEAL_PLANNER_TABLE)
    List<MealPlanner> getAllRecords();

}
