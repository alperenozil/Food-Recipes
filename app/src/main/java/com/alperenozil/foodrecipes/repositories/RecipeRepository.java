package com.alperenozil.foodrecipes.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alperenozil.foodrecipes.models.Recipe;
import com.alperenozil.foodrecipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {
    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;

    public static RecipeRepository getInstance(){
        if (instance==null){
            instance=new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository(){
        mRecipeApiClient=RecipeApiClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeApiClient.getRecipies();
    }
}
