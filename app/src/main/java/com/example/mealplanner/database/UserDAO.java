package com.example.mealplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplanner.database.entities.User;

import java.util.List;


@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);


    @Query("SELECT * FROM "+ MealPlannerDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();

    @Query("DELETE FROM " + MealPlannerDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + MealPlannerDatabase.USER_TABLE + " WHERE username == :username")
    User getUserByUsername(String username);
}
