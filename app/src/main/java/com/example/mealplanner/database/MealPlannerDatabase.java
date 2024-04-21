package com.example.mealplanner.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mealplanner.MainActivity;
import com.example.mealplanner.database.entities.MealPlanner;
import com.example.mealplanner.database.entities.User;
import com.example.mealplanner.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {MealPlanner.class, User.class}, version = 1, exportSchema = false)
public abstract class MealPlannerDatabase extends RoomDatabase {
    public static final String USER_TABLE = "user_table";
    private static final String DATABASE_NAME = "MealPlanner_database";
    public static final String MEAL_PLANNER_TABLE = "mealPlannerTable";

    private static volatile MealPlannerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract MealPlannerDAO mealPlannerDAO();

    public abstract UserDAO userDAO();

    public static MealPlannerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MealPlannerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MealPlannerDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin2", "admin2");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };
}
