package com.alperenozil.foodrecipes;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


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
        testRetrofitRequest();
        subscriveObservers();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter=new RecipeRecyclerAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void searchRecipesApi(String query, int pageNumber){
        recipeListViewModel.searchRecipesApi(query, pageNumber);
    }

    private void testRetrofitRequest(){
        searchRecipesApi("chicken breast",1);
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}