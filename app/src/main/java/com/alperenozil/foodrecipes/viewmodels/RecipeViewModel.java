package com.alperenozil.foodrecipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alperenozil.foodrecipes.models.Recipe;
import com.alperenozil.foodrecipes.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {
    private RecipeRepository recipeRepository;

    public RecipeViewModel() {
        recipeRepository=RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe(){
        return recipeRepository.getRecipe();
    }

    public void searchRecipeById(String recipeId){
        recipeRepository.searchRecipeById(recipeId);
    }
}

