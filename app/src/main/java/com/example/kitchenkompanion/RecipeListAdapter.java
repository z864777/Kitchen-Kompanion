package com.example.kitchenkompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class RecipeListAdapter extends BaseAdapter {
    Context context;
    String[] recipes;
    LayoutInflater inflater;

    public RecipeListAdapter(Context contxt, String[] list){
        this.context = contxt;
        this.recipes = list;
        inflater = LayoutInflater.from(contxt);


    }
    @Override
    public int getCount() {
        return recipes.length;
    }

    @Override
    public Object getItem(int i) {
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
        txtView.setText(recipes[i]);

        return view;
    }
}
