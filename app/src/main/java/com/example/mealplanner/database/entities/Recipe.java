package com.example.mealplanner.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mealplanner.database.MealPlannerDatabase;

import java.util.List;
import java.util.Objects;

@Entity(tableName = MealPlannerDatabase.RECIPE_TABLE)
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int image;
    private String name;


    public Recipe(int image, String name) {
        this.image = image;
        this.name = name;
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
        Recipe recipe = (Recipe) o;
        return id == recipe.id && image == recipe.image && Objects.equals(name, recipe.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, name);
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

