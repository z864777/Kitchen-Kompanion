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
    String[] recipes;
    int[] listImage;
    LayoutInflater inflater;

    public RecipeListAdapter(Context contxt, String[] list, int[] image){
        this.context = contxt;
        this.recipes = list;
        this.listImage = image;
        inflater = LayoutInflater.from(contxt);


    }
    @Override
    public int getCount() {
        return recipes.length;
    }

    @Override
    public String getItem(int i) {
        return recipes[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_recipe_list_view, null);
        TextView txtView = (TextView) view.findViewById(R.id.recipe_list_text);
        ImageView foodImage = (ImageView) view.findViewById(R.id.recipe_owner_image);

        txtView.setText(recipes[i]);
        foodImage.setImageResource(listImage[i]);

        return view;
    }
}
