package com.alperenozil.foodrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alperenozil.foodrecipes.models.Recipe;
import com.alperenozil.foodrecipes.requests.RecipeApi;
import com.alperenozil.foodrecipes.requests.ServiceGenerator;
import com.alperenozil.foodrecipes.requests.responses.RecipeResponse;
import com.alperenozil.foodrecipes.requests.responses.RecipeSearchResponse;
import com.alperenozil.foodrecipes.util.Constants;
import com.alperenozil.foodrecipes.util.Testing;
import com.alperenozil.foodrecipes.viewmodels.RecipeListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
    public static final String TAG="RecipeListActivity";
    private RecipeListViewModel recipeListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recipeListViewModel= ViewModelProviders.of(this).get(RecipeListViewModel.class);
        testRetrofitRequest();
        subscriveObservers();
    }

    private void subscriveObservers(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes!=null) {
                    Testing.printRecipes(recipes,TAG);
                }
            }
        });
    }

    private void searchRecipesApi(String query, int pageNumber){
        recipeListViewModel.searchRecipesApi(query, pageNumber);
    }

    private void testRetrofitRequest(){
        searchRecipesApi("chicken breast",1);
    }
}