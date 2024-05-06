package com.example.mealplanner.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mealplanner.database.MealPlannerDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = MealPlannerDatabase.MEAL_PLANNER_TABLE)
public class MealPlanner {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int recipeId;
    private int userId;

    private String day;
    private String time;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public MealPlanner(int userId, int recipeId, String day, String time) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.day = day;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealPlanner that = (MealPlanner) o;
        return id == that.id && recipeId == that.recipeId && userId == that.userId && Objects.equals(day, that.day) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipeId, userId, day, time);
    }

    @Override
    public String toString() {
        return "MealPlanner: " +
                "recipeId=" + recipeId +
                ", userId=" + userId +
                ", day=" + day
                + "/n";
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int userId) {
        this.recipeId = recipeId;
    }
}
