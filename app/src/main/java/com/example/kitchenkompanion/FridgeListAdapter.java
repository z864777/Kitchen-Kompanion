package com.example.kitchenkompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FridgeListAdapter extends BaseAdapter {

    Context context;
    String listfood[];
    int listimages[];
    String listfoodcount[];
    LayoutInflater inflater;

    public FridgeListAdapter(Context ctxt, String[] list, int[] image, String[] itemcount) {
        this.context = ctxt;
        this.listfood = list;
        this.listimages = image;
        this.listfoodcount = itemcount;
        inflater = LayoutInflater.from(ctxt);
    }

    @Override
    public int getCount() {
        return listfood.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_fridge_list_view, null);
        TextView txtView = (TextView) view.findViewById(R.id.fridge_list_text);
        ImageView foodimage = (ImageView) view.findViewById(R.id.fridge_list_image);
        TextView countView = (TextView) view.findViewById(R.id.fridge_item_count);
        txtView.setText(listfood[i]);
        foodimage.setImageResource(listimages[i]);
        countView.setText(listfoodcount[i]);
        return view;
    }
}
