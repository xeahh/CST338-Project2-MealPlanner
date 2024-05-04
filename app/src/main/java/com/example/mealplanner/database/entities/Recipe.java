package com.example.mealplanner.database.entities;

import android.graphics.drawable.Drawable;

import java.util.Objects;

public class Recipe {
    private int image;
    private String name;
    public Recipe(int image, String name) {
        this.image = image;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public int getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
}
