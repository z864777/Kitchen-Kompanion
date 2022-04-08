package com.example.kitchenkompanion.ui.recipes;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kitchenkompanion.MainActivity;
import com.example.kitchenkompanion.R;
import com.example.kitchenkompanion.databinding.FragmentRecipesBinding;

public class RecipesFragment extends Fragment {

    private FragmentRecipesBinding binding;

    Button filterButton;
    AlertDialog.Builder dialogBuilder;
    Context context;

    private EditText direct_name, direct_count;
    private Button direct_add_cancel, direct_add_save;
    private ToggleButton direct_add_toggle;
    private Spinner counter_dropdown;
    private AlertDialog dialog;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecipesViewModel recipesViewModel =
                new ViewModelProvider(this).get(RecipesViewModel.class);

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = (ListView) root.findViewById(R.id.recipe_list);
        filterButton = root.findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
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
        RecipeListAdapter rla = new RecipeListAdapter(getActivity(), MainActivity.recipes, MainActivity.communal_owner_images);

        listView.setAdapter(rla);

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