package com.alperenozil.foodrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.alperenozil.foodrecipes.adapters.OnRecipeListener;
import com.alperenozil.foodrecipes.adapters.RecipeRecyclerAdapter;
import com.alperenozil.foodrecipes.models.Recipe;
import com.alperenozil.foodrecipes.util.Testing;
import com.alperenozil.foodrecipes.viewmodels.RecipeListViewModel;

import java.util.List;


public class RecipeListActivity extends BaseActivity implements OnRecipeListener {
    public static final String TAG="RecipeListActivity";
    private RecipeListViewModel recipeListViewModel;
    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recyclerView=findViewById(R.id.recipe_list);
        recipeListViewModel= ViewModelProviders.of(this).get(RecipeListViewModel.class);
        initSearchView();
        subscriveObservers();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter=new RecipeRecyclerAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){ // if it is at the bottom
                    // search for next page's recipes
                    recipeListViewModel.searchNextPage();
                }
            }
        });
    }

    private void subscriveObservers(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes!=null) {
                    Testing.printRecipes(recipes,TAG);
                    mAdapter.setRecipes(recipes);
                }
            }
        });
    }

    private void initSearchView(){
        final SearchView searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // will be triggered once
                Log.d(TAG, "onQueryTextSubmit: "+query);
                recipeListViewModel.searchRecipesApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { // will be triggered on every key stroke
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {
        startActivity(new Intent(this,RecipeActivity.class).putExtra("recipe",mAdapter.getSelectedRecipe(position)));
    }

    @Override
    public void onCategoryClick(String category) {

    }
}