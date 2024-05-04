package com.example.mealplanner.database.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Recipe {
    private int image;
    private String name;
    private List<String> ingredients;

    public Recipe(int image, String name, List<String> ingredients) {
        this.image = image;
        this.name = name;
        this.ingredients = ingredients;
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

    public List<String> getIngredients() {
        return ingredients;
    }

    public static List<String> createIngredients(String... ingredients) {
        return Arrays.asList(ingredients);
    }
}

