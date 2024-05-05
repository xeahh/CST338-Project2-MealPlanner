package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.database.MealPlannerRepository;
import com.example.mealplanner.database.entities.Recipe;
import com.example.mealplanner.databinding.ActivityRecipesBinding;
import com.example.mealplanner.viewHolders.RecipeAdapter;
import com.example.mealplanner.viewHolders.RecipeViewModel;



public class RecipesActivity extends AppCompatActivity {
    private ActivityRecipesBinding binding;
    private MealPlannerRepository repository;
    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);


        int loggedInUserId = getIntent().getIntExtra("userId", -1);

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

        repository = MealPlannerRepository.getRepository(getApplication());

        recipeViewModel.getAllRecipes().observe(this, recipeList -> {
            adapter.submitList(recipeList);
        });


        adapter.setListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Recipe recipe = adapter.getCurrentList().get(position);
                Log.i("onitem", recipe.toString());
                Intent intent = new Intent(RecipesActivity.this, MealPlannerActivity.class);
                intent.putExtra("userId", loggedInUserId);
                intent.putExtra("selectedRecipeId", recipe.getId());
                startActivity(intent);
//                setResult(RESULT_OK, intent);
//                finish();
            }
        });

//        ArrayList<Recipe> recipeList = new ArrayList<>();
//        recipeList.add(new Recipe(R.drawable.oatmeal, "Oatmeal", Recipe.createIngredients("oats", "milk", "salt")));
//        recipeList.add(new Recipe(R.drawable.baconandeggs, "Bacon and Eggs", Recipe.createIngredients("bacon", "eggs")));
//        recipeList.add(new Recipe(R.drawable.turkeyhummus, "Turkey and Hummus Wrap", Recipe.createIngredients("turkey", "hummus", "whole wheat wrap")));
//        recipeList.add(new Recipe(R.drawable.chickentacos, "Chicken Tacos", Recipe.createIngredients("chicken", "tortillas")));
//        recipeList.add(new Recipe(R.drawable.toast, "Toast", Recipe.createIngredients("bread", "butter")));

    }

}
