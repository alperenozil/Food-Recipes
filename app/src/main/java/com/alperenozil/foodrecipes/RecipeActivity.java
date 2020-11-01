package com.alperenozil.foodrecipes;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alperenozil.foodrecipes.models.Recipe;
import com.alperenozil.foodrecipes.viewmodels.RecipeViewModel;
import com.bumptech.glide.Glide;

public class RecipeActivity extends BaseActivity {
    private static final String TAG = "RecipeActivity";
    private TextView recipeDetailTextView;
    private ImageView recipeImageView;
    private RecipeViewModel recipeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeViewModel= ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeDetailTextView=findViewById(R.id.recipe_detail);
        recipeImageView=findViewById(R.id.recipe_image);
        subscribeObservers();
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")){
            Recipe recipe=getIntent().getParcelableExtra("recipe");
            recipeViewModel.searchRecipeById(recipe.getRecipe_id());
        }
    }

    private void subscribeObservers(){
        recipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if(recipe!=null){
                    String ingredients="";
                    for (String ingredient: recipe.getIngredients()){
                        ingredients=ingredients+ingredient+"\n";
                    }
                    recipeDetailTextView.setText(ingredients);
                    Glide.with(getApplicationContext()).load(recipe.getImage_url())
                            .into(recipeImageView);
                }
            }
        });
    }
}