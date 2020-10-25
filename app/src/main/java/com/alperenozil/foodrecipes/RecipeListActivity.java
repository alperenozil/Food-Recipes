package com.alperenozil.foodrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alperenozil.foodrecipes.models.Recipe;
import com.alperenozil.foodrecipes.requests.RecipeApi;
import com.alperenozil.foodrecipes.requests.ServiceGenerator;
import com.alperenozil.foodrecipes.requests.responses.RecipeSearchResponse;
import com.alperenozil.foodrecipes.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
    public static final String TAG="RecipeListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        testRetrofitRequest();
    }

    private void testRetrofitRequest(){
        RecipeApi recipeApi= ServiceGenerator.getRecipeApi();
        Call<RecipeSearchResponse> responseCall=recipeApi
                .searchRecipe(Constants.API_KEY,"chicken breast","1");
        responseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                Log.d(TAG, "onResponse: server response "+response.toString());
                if (response.code()==200){
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    List<Recipe> recipes=new ArrayList<>(response.body().getRecipes());
                    for (Recipe recipe:recipes){
                        Log.d(TAG, "onResponse: "+recipe.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {

            }
        });
    }
}