package com.example.kitchenkompanion.ui.recipes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kitchenkompanion.MainActivity;
import com.example.kitchenkompanion.R;
import com.example.kitchenkompanion.databinding.FragmentRecipesBinding;

public class RecipesFragment extends Fragment {

    private FragmentRecipesBinding binding;

    ListView listview;

    //Parameters for the explore button
    Button exploreButton;
    AlertDialog.Builder dialogBuilder;
    Context context;
    private AlertDialog dialog;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecipesViewModel recipesViewModel =
                new ViewModelProvider(this).get(RecipesViewModel.class);

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        //Configure the Explore Button
        exploreButton = root.findViewById(R.id.exploreButton);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder = new AlertDialog.Builder(context);
                final View addPopupView = getLayoutInflater().inflate(R.layout.recipe_filter__popup, null);

                //show dialog
                dialogBuilder.setView(addPopupView);
                dialog = dialogBuilder.create();
                dialog.show();

            }
        });

        //Configure the list view
        listview = (ListView) root.findViewById(R.id.recipe_list);
        RecipeListAdapter rla = new RecipeListAdapter(getActivity(), MainActivity.recipe_names, MainActivity.list_images, MainActivity.recipe_image, MainActivity.recipe_list);
        listview.setAdapter(rla);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,RecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("recipe_name", rla.getItem(i));
                bundle.putInt("food_pic", rla.getFoodPic(i));
                bundle.putInt("recipe_pic", rla.getRecipePic(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(Context c) {
        super.onAttach(context);
        context = c;
    }



}