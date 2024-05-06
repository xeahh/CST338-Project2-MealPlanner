package com.example.mealplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.mealplanner.database.entities.Recipe;

import org.junit.Test;
public class RecipeTest {

    @Test
    public void testEquals() {
        Recipe recipe1 = new Recipe(1, "Pizza");
        recipe1.setId(1);
        Recipe recipe2 = new Recipe(1, "Pizza");
        recipe2.setId(1);

        // Recipes with the same ID should be equal
        assertEquals(recipe1, recipe2);

        // Recipes with different IDs should not be equal
        recipe2.setId(2);
        assertNotEquals(recipe1, recipe2);
    }

    @Test
    public void testHashCode() {
        Recipe recipe1 = new Recipe(1, "Pizza");
        recipe1.setId(1);
        Recipe recipe2 = new Recipe(1, "Pizza");
        recipe2.setId(2);
        assertNotEquals(recipe1.hashCode(), recipe2.hashCode());
    }
}
