package com.example.kitchenkompanion.ui.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kitchenkompanion.R;


public class RecipeListAdapter extends BaseAdapter {
    Context context;
    String[] recipes_name;
    int[] listImage, food_pic, recipe_pic;
    LayoutInflater inflater;

    public RecipeListAdapter(Context contxt, String[] list, int[] image, int[] food_picture, int recipe_picture[]){
        this.context = contxt;
        this.recipes_name = list;
        this.listImage = image;
        this.food_pic = food_picture;
        this.recipe_pic = recipe_picture;
        inflater = LayoutInflater.from(contxt);


    }
    @Override
    public int getCount() {
        return recipes_name.length;
    }

    @Override
    public String getItem(int i) {
        return recipes_name[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public int getFoodPic(int i) {
        return food_pic[i];
    }

    public int getRecipePic(int i){
        return recipe_pic[i];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.recipe_list_view, null);
        TextView txtView = (TextView) view.findViewById(R.id.recipe_list_text);
        ImageView foodImage = (ImageView) view.findViewById(R.id.recipe_owner_image);

        txtView.setText(recipes_name[i]);
        foodImage.setImageResource(listImage[i]);

        return view;
    }
}
