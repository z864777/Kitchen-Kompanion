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
import android.widget.TextView;
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

    //Explore Button Parameters
    Button exploreButton;
    AlertDialog.Builder dialogBuilder;
    Context context;
    private AlertDialog dialog;

    //ListAdapter Parameters
    public static String[] recipe_names = MainActivity.recipe_names;
    public static int[] list_images = MainActivity.list_images;
    public static int [] recipe_image = MainActivity.recipe_image;
    public static int[] recipe_list = MainActivity.recipe_list;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecipesViewModel recipesViewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

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
                Button novice = (Button)dialog.findViewById(R.id.noviceButton);
                Button intermediate = (Button)dialog.findViewById(R.id.intermediateButton);
                Button advanced = (Button)dialog.findViewById(R.id.advancedButton);
                Spinner meal = (Spinner) dialog.findViewById(R.id.meal_spinner);
                meal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView meal_result = (TextView) meal.getSelectedView();
                        String str_meal = meal_result.getText().toString();

                        //configure novice button
                        novice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(str_meal.equals("Breakfast")){
                                    recipe_names = MainActivity.novice_breakfast_names;
                                    list_images = MainActivity.novice_breakfast_list_image;
                                    recipe_image = MainActivity.novice_breakfast_recipe_image;
                                    recipe_list = MainActivity.novice_breakfast_recipe_list;
                                    refreshList(root);
                                    dialog.dismiss();
                                }
                                else if(str_meal.equals("Lunch")){
                                    recipe_names = MainActivity.novice_lunch_names;
                                    list_images = MainActivity.novice_lunch_list_image;
                                    recipe_image = MainActivity.novice_lunch_recipe_image;
                                    recipe_list = MainActivity.novice_lunch_recipe_list;
                                    refreshList(root);
                                    dialog.dismiss();
                                }
                                else if(str_meal.equals("Dinner")){
                                    recipe_names = MainActivity.novice_dinner_names;
                                    list_images = MainActivity.novice_dinner_list_image;
                                    recipe_image = MainActivity.novice_dinner_recipe_image;
                                    recipe_list = MainActivity.novice_dinner_recipe_list;
                                    refreshList(root);
                                    dialog.dismiss();
                                }
                            }
                        });

                        intermediate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(str_meal.equals("Breakfast")){
                                    recipe_names = MainActivity.intermediate_breakfast_names;
                                    list_images = MainActivity.intermediate_breakfast_list_image;
                                    recipe_image = MainActivity.intermediate_breakfast_recipe_image;
                                    recipe_list = MainActivity.intermediate_breakfast_recipe_list;
                                    refreshList(root);
                                    dialog.dismiss();
                                }
                                else if(str_meal.equals("Lunch")){
                                    recipe_names = MainActivity.intermediate_lunch_names;
                                    list_images = MainActivity.intermediate_lunch_list_image;
                                    recipe_image = MainActivity.intermediate_lunch_recipe_image;
                                    recipe_list = MainActivity.intermediate_lunch_recipe_list;
                                    refreshList(root);
                                    dialog.dismiss();
                                }
                                else if(str_meal.equals("Dinner")){
                                    recipe_names = MainActivity.intermediate_dinner_names;
                                    list_images = MainActivity.intermediate_dinner_list_image;
                                    recipe_image = MainActivity.intermediate_dinner_recipe_image;
                                    recipe_list = MainActivity.intermediate_dinner_recipe_list;
                                    refreshList(root);
                                    dialog.dismiss();
                                }
                            }
                        });

                        //When no button is clicked but clicker interacted with
                        if(str_meal.equals("All Meals")){
                            recipe_names = MainActivity.recipe_names;
                            list_images = MainActivity.list_images;
                            recipe_image = MainActivity.recipe_image;
                            recipe_list = MainActivity.recipe_list;
                            refreshList(root);
                        }

                        else if(str_meal.equals("Breakfast")){
                            recipe_names = MainActivity.breakfast_names;
                            list_images = MainActivity.breakfast_list_image;
                            recipe_image = MainActivity.breakfast_recipe_image;
                            recipe_list = MainActivity.breakfast_recipe_list;
                            refreshList(root);
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }

        });

        //Configure the list view
        listview = (ListView) root.findViewById(R.id.recipe_list);
        RecipeListAdapter rla = new RecipeListAdapter(getActivity(), recipe_names, list_images, recipe_image, recipe_list);
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

    public void refreshList(View root){
        listview = (ListView) root.findViewById(R.id.recipe_list);
        RecipeListAdapter rla = new RecipeListAdapter(getActivity(), recipe_names, list_images, recipe_image, recipe_list);
        listview.setAdapter(rla);
    }

}