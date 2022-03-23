package com.example.kitchenkompanion;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kitchenkompanion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //Arrays for storing information in fridge list.
    //Can be accessed by any fragment
    //Data saved globally across entire app
    //Add to lists by copying elements into temp array of size + 1, add new element in last spot, set these list = temp.clone()
    //Data for Communal Items
    public static String communal_items[] = {"Apple", "Banana", "Whole Milk", "Orange Juice", "Bread", "Cake", "Rice", "Chicken Breast"};
    public static int communal_images[] = {R.drawable.ic_food_apple, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp};
    public static String communal_count[] = {"1" ,"2", "1 Gal", "2 Gal", "1 Loaf", "2 Slices", "3 Pounds", "2"};
    public static int communal_onwer_images[] = {R.drawable.communal_list_item, R.drawable.communal_list_item, R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item};
    //Data for Private Items
    public static String private_items[] = {"Private 1", "Private 2", "Private 3", "Private 4", "Private 5", "Private 6", "Private 7"};
    public static int private_images[] = {R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp};
    public static String private_count[] = {"1", "2 Pounds", "2 Slices", "1 Gal", "3", "2 Pounds", "1 Gal"};
    public static int private_owner_images[] = {R.drawable.private_list_owner2, R.drawable.private_list_owner2, R.drawable.private_list_owner2, R.drawable.private_list_owner3, R.drawable.private_list_owner3, R.drawable.private_list_owner4, R.drawable.private_list_owner1};


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_fridge, R.id.navigation_shop, R.id.navigation_recipes)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}