package com.example.mealplanner.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mealplanner.database.MealPlannerDatabase;

import java.time.LocalDateTime;

@Entity(tableName = MealPlannerDatabase.MEAL_PLANNER_TABLE)
public class MealPlanner {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private LocalDateTime date;
    private int recipeId;

    private String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public MealPlanner() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int userId) {
        this.recipeId = recipeId;
    }
}
