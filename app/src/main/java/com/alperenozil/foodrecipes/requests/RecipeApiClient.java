package com.alperenozil.foodrecipes.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alperenozil.foodrecipes.AppExecutors;
import com.alperenozil.foodrecipes.models.Recipe;
import com.alperenozil.foodrecipes.requests.responses.RecipeSearchResponse;
import com.alperenozil.foodrecipes.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {

    private static final String TAG = "RecipeApiClient";
    private static RecipeApiClient instance;
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;
    private MutableLiveData<List<Recipe>> mRecipes;

    public static RecipeApiClient getInstance(){
        if(instance==null){
            instance=new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient() {
        mRecipes=new MutableLiveData<>();

    }

    public LiveData<List<Recipe>> getRecipies() {
        return mRecipes;
    }

    public void searchRecipesApi(String query, int pageNumber){
        if(mRetrieveRecipesRunnable != null){
            mRetrieveRecipesRunnable = null;
        }
        mRetrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);
        final Future handler= AppExecutors.getInstance().networkIO().submit(mRetrieveRecipesRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if (cancelRequest){
                    return;
                }
                if (response.code()==200){
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse)response.body()).getRecipes());
                    if(pageNumber == 1){
                        mRecipes.postValue(list);
                    } else {
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        currentRecipes.addAll(list);
                        mRecipes.postValue(currentRecipes);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error );
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }
        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber){
            return ServiceGenerator.getRecipeApi().searchRecipe(
                    Constants.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }
        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the search request.");
            cancelRequest = true;
        }

    }

}