package com.example.kitchenkompanion.ui.recipes;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitchenkompanion.R;

public class RecipeActivity extends AppCompatActivity {
    Context context;
    TextView title;
    ImageView food_image, recipe_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView title = (TextView)findViewById(R.id.title);
        //Recieve arguments from other class
        Bundle bundle = getIntent().getExtras();
        String recipe_name = bundle.getString("recipe_name");
        int food_pic = bundle.getInt("food_pic");
        int recipe_pic = bundle.getInt("recipe_pic");
        title.setText(recipe_name);
        food_image = (ImageView)findViewById(R.id.picture);
        food_image.setImageResource(food_pic);
        recipe_image = (ImageView) findViewById(R.id.recipe);
        recipe_image.setImageResource(recipe_pic);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
