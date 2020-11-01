package com.alperenozil.foodrecipes;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alperenozil.foodrecipes.models.Recipe;

public class RecipeActivity extends BaseActivity {
    private TextView recipeDetailTextView;
    private ImageView recipeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")){
            Recipe recipe=getIntent().getParcelableExtra("recipe");
        }
    }
}