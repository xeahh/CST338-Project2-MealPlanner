package com.example.mealplanner;

import static com.example.mealplanner.database.MealPlannerRepository.repository;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.RecipeDAO;
import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.database.entities.User;
import com.example.mealplanner.databinding.ActivityRecipesBinding;
import com.example.mealplanner.viewHolders.RecipeAdapter;
import com.example.mealplanner.viewHolders.RecipeViewModel;
import com.example.mealplanner.viewHolders.UserAdapter;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {
    private ActivityRecipesBinding binding;
    private MealPlannerRepository repository;
    private RecipeViewModel recipeViewModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = MealPlannerRepository.getRepository(getApplication());
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        int loggedInUserId = getIntent().getIntExtra("userId", -1);
        String day = getIntent().getStringExtra("day");
        String time = getIntent().getStringExtra("time");

        binding.toolbar.setNavigationIcon(R.drawable.arrow);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandingPageActivity.class)
                        .putExtra("userId", loggedInUserId));
            }
        });



        RecyclerView recyclerView = binding.recipesRecyclerview;
        final RecipeAdapter adapter = new RecipeAdapter(new RecipeAdapter.RecipeDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



//        Recipe recipe = new Recipe(R.drawable.turkeyhummus, "Turkey and Hummus Wrap");
//        repository.insertRecipe(recipe);


        recipeViewModel.getAllRecipes().observe(this, recipeList -> {
            adapter.submitList(recipeList);
        });


        adapter.setListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Recipe recipe = adapter.getCurrentList().get(position);
                recipeViewModel.deleteRecipe(recipe);
            }
            @Override
            public void onItemClick(int position) {
                Recipe recipe = adapter.getCurrentList().get(position);
                Log.i("DAY_AND_TIME", recipe.getName());
                if (day!=null && time!=null) {
                    startActivity(new Intent(getApplicationContext(),MealPlannerActivity.class)
                            .putExtra("userId", loggedInUserId)
                            .putExtra("selected_recipe",recipe.getId())
                            .putExtra("day", day)
                            .putExtra("time", time));
                }
            }
        });

    }

}
