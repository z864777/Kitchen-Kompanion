package com.example.kitchenkompanion;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.kitchenkompanion.databinding.ActivityMainBinding;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    //Recipes
    public static String recipes[] = {"Chocolate Chip Pancakes", "Taco Meat", "Lasagna", "Banana Bread", "Homemade Mac and Cheese", "Chicken Parmesan", "Classic Waffles", "Microwave Baked Potato", "Chicken Pot Pie"};
    public static int communal_owner_images[] = {R.drawable.communal_list_item, R.drawable.communal_list_item, R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item};

    //Arrays for storing information in fridge list.
    //Can be accessed by any fragment
    //Data saved globally across entire app
    //Add to lists by calling addToFridgeCommunal or addToFridgePrivate
    //Data for Communal Items
    public static String communal_items[] = {"Apple", "Banana", "Milk", "Soda", "Bread", "Cake", "Flour", "Raw Beef"};
    public static int communal_images[] = {R.drawable.food_apple, R.drawable.food_banana, R.drawable.food_milk, R.drawable.food_soda, R.drawable.food_bread, R.drawable.food_cake, R.drawable.food_flour, R.drawable.food_raw_meat};
    public static String communal_count[] = {"1  " ,"2  ", "1 Gal", "2 Gal", "1 Loaf", "2 Slice", "3 Pound", "2  Pound"};
    public static int communal_onwer_images[] = {R.drawable.communal_list_item, R.drawable.communal_list_item, R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item};
    public static String communal_purchase_dates[] = {"03/29/22", "01/22/22", "01/02/22", "02/25/22", "03/12/22", "11/16/21", "03/28/22", "02/09/22"};
    //Data for Private Items
    public static String private_items[] = {"Cookie", "Pizza", "Burger", "Carrot", "Watermelon", "Cake", "Cheese"};
    public static int private_images[] = {R.drawable.food_cookie, R.drawable.food_pizza, R.drawable.food_burger, R.drawable.food_carrot, R.drawable.food_watermelon, R.drawable.food_cake, R.drawable.food_cheese};
    public static String private_count[] = {"1  ", "2 Slice", "2  ", "3  ", "3  ", "2 Slice", "1 Pound"};
    public static int private_owner_images[] = {R.drawable.private_list_owner1, R.drawable.private_list_owner1, R.drawable.private_list_owner2, R.drawable.private_list_owner3, R.drawable.private_list_owner3, R.drawable.private_list_owner4, R.drawable.private_list_owner4};
    public static String private_purchase_dates[] = {"02/19/22", "02/25/22", "03/22/22", "12/25/21", "02/15/22", "12/11/21", "01/08/22"};
    //Data for Shopping List
    public static String shopping_list[] = {};
    public static int shopping_list_images[] = {};
    public static String shopping_count[] = {};
    public static int shopping_owner_images[] = {};
    //Data for the four preset Shopping Lists
    public static String preset_list1[] = {"Apple", "Strawberry", "Egg"};
    public static int preset_images1[] = {R.drawable.food_apple, R.drawable.food_strawberry, R.drawable.food_egg};
    public static String preset_count1[] = {"1  ", "3  ", "12  "};
    public static int preset_owner_images1[] = {R.drawable.communal_list_item, R.drawable.private_list_owner1, R.drawable.communal_list_item};
    public static String preset_list2[] = {};
    public static int preset_images2[] = {};
    public static String preset_count2[] = {};
    public static int preset_owner_images2[] = {};
    public static String preset_list3[] = {};
    public static int preset_images3[] = {};
    public static String preset_count3[] = {};
    public static int preset_owner_images3[] = {};
    public static String preset_list4[] = {};
    public static int preset_images4[] = {};
    public static String preset_count4[] = {};
    public static int preset_owner_images4[] = {};
    public static String preset1_name = "Spring Seasonal";
    public static String preset2_name = "";
    public static String preset3_name = "";
    public static String preset4_name = "";


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
                R.id.navigation_fridge, R.id.navigation_shop, R.id.navigation_recipes, R.id.navigation_users)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    //add to communal fridge list
    //call MainActivity(String item_name, String item_count) to add item_name and item_count
    //Item_name should be a string of max length 15
    //item_count should be in format (number + space + counter)
    //Number being any number 1-999, space being " ", and counter being (None, Pound, Slice, Gal, Loaf)
    //For counter = None, use a space " " (Ex "1  " followed by 2 spaces).
    public static void addToFridgeCommunal(String item_name, String item_count) {
        int list_background_image = R.drawable.communal_list_item;
        int food_image = R.drawable.ic_fridge_black_24dp;
        int list_length = MainActivity.communal_items.length;
        //create lists of length + 1
        String communal_items_temp[] = new String[list_length + 1];
        String communal_count_temp[] = new String[list_length + 1];
        int communal_owner_images_temp[] = new int[list_length + 1];
        int communal_images_temp[] = new int[list_length + 1];
        String communal_purchase_temp[] = new String[list_length + 1];
        //copy elements to temp
        for (int i = 0; i < list_length; i++) {
            communal_items_temp[i+1] = communal_items[i];
            communal_count_temp[i+1] = communal_count[i];
            communal_owner_images_temp[i+1] = communal_onwer_images[i];
            communal_images_temp[i+1] = communal_images[i];
            communal_purchase_temp[i+1] = communal_purchase_dates[i];
        }
        //add new element
        communal_items_temp[0] = item_name;
        communal_count_temp[0] = item_count;
        communal_owner_images_temp[0] = list_background_image;
        communal_images_temp[0] = setFoodImage(item_name);
        communal_purchase_temp[0] = getDate();
        //update global references
        communal_items = communal_items_temp.clone();
        communal_count = communal_count_temp.clone();
        communal_onwer_images = communal_owner_images_temp.clone();
        communal_images = communal_images_temp.clone();
        communal_purchase_dates = communal_purchase_temp.clone();
    }

    //Add to private fridge list
    //refer to notes above addToFridgeCommunal
    public static void addToFridgePrivate(String item_name, String item_count) {
        int list_background_image = R.drawable.private_list_owner1;
        int food_image = R.drawable.ic_fridge_black_24dp;
        int list_length = private_items.length;
        //create lists of length + 1
        String private_items_temp[] = new String[list_length + 1];
        String private_count_temp[] = new String[list_length + 1];
        int private_owner_images_temp[] = new int[list_length + 1];
        int private_images_temp[] = new int[list_length + 1];
        String private_purchase_temp[] = new String[list_length + 1];
        //copy elements to temp
        for (int i = 0; i < list_length; i++) {
            private_items_temp[i+1] = private_items[i];
            private_count_temp[i+1] = private_count[i];
            private_owner_images_temp[i+1] = private_owner_images[i];
            private_images_temp[i+1] = private_images[i];
            private_purchase_temp[i+1] = private_purchase_dates[i];
        }
        //add new element
        private_items_temp[0] = item_name;
        private_count_temp[0] = item_count;
        private_owner_images_temp[0] = list_background_image;
        private_images_temp[0] = setFoodImage(item_name);
        private_purchase_temp[0] = getDate();
        //update global references
        private_items = private_items_temp.clone();
        private_count = private_count_temp.clone();
        private_owner_images = private_owner_images_temp.clone();
        private_images = private_images_temp.clone();
        private_purchase_dates = private_purchase_temp.clone();
    }

    //Called by addToFridgePrivate and addToFridgeCommunal
    //Contains a list of hardcoded images to be used
    //depending on the item name
    private static int setFoodImage(String item_name) {
        int food_image;
        if (item_name.equalsIgnoreCase("Apple")) {
            food_image = R.drawable.food_apple;
        } else if (item_name.equalsIgnoreCase("Banana")) {
            food_image = R.drawable.food_banana;
        } else if (item_name.equalsIgnoreCase("Bread")) {
            food_image = R.drawable.food_bread;
        } else if (item_name.equalsIgnoreCase("Broccoli")) {
            food_image = R.drawable.food_brocolli;
        } else if (item_name.equalsIgnoreCase("Burger")) {
            food_image = R.drawable.food_burger;
        } else if (item_name.equalsIgnoreCase("Cake")) {
            food_image = R.drawable.food_cake;
        } else if (item_name.equalsIgnoreCase("Carrot")) {
            food_image = R.drawable.food_carrot;
        } else if (item_name.equalsIgnoreCase("Cheese")) {
            food_image = R.drawable.food_cheese;
        } else if (item_name.equalsIgnoreCase("Cookie")) {
            food_image = R.drawable.food_cookie;
        } else if (item_name.equalsIgnoreCase("Corn")) {
            food_image = R.drawable.food_corn;
        } else if (item_name.equalsIgnoreCase("Egg")) {
            food_image = R.drawable.food_egg;
        } else if (item_name.equalsIgnoreCase("Flour")) {
            food_image = R.drawable.food_flour;
        } else if (item_name.equalsIgnoreCase("Milk")) {
            food_image = R.drawable.food_milk;
        } else if (item_name.equalsIgnoreCase("Olive Oil")) {
            food_image = R.drawable.food_olive_oil;
        } else if (item_name.equalsIgnoreCase("Orange")) {
            food_image = R.drawable.food_orange;
        } else if (item_name.equalsIgnoreCase("Peanut Butter")) {
            food_image = R.drawable.food_peanut_butter;
        } else if (item_name.equalsIgnoreCase("Pizza")) {
            food_image = R.drawable.food_pizza;
        } else if (item_name.equalsIgnoreCase("Raw Beef")) {
            food_image = R.drawable.food_raw_meat;
        } else if (item_name.equalsIgnoreCase("Salt")) {
            food_image = R.drawable.food_salt;
        } else if (item_name.equalsIgnoreCase("Soda")) {
            food_image = R.drawable.food_soda;
        } else if (item_name.equalsIgnoreCase("Strawberry")) {
            food_image = R.drawable.food_strawberry;
        } else if (item_name.equalsIgnoreCase("Tomato")) {
            food_image = R.drawable.food_tomato;
        } else if (item_name.equalsIgnoreCase("Watermelon")) {
            food_image = R.drawable.food_watermelon;
        } else {
            food_image = R.drawable.ic_fridge_black_24dp;
        }
        return food_image;
    }

    //returns the current date of system for purchase date
    private static String getDate() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        int year = c.get(Calendar.YEAR);
        String date = month + "/" + day + "/" + year;
        return date;
    }

    public static void addToShoppingList(String item_name, String item_count, boolean private_list) {
        int list_background_image;
        int list_length = MainActivity.shopping_list.length;
        //create lists of length + 1
        String shopping_items_temp[] = new String[list_length + 1];
        String shopping_count_temp[] = new String[list_length + 1];
        int shopping_owner_images_temp[] = new int[list_length + 1];
        int shopping_images_temp[] = new int[list_length + 1];
        //copy elements to temp
        for (int i = 0; i < list_length; i++) {
            shopping_items_temp[i+1] = shopping_list[i];
            shopping_count_temp[i+1] = shopping_count[i];
            shopping_owner_images_temp[i+1] = shopping_owner_images[i];
            shopping_images_temp[i+1] = shopping_list_images[i];
        }

        if (private_list) {
            list_background_image = R.drawable.private_list_owner1;
        } else {
            list_background_image = R.drawable.communal_list_item;
        }
        //add new element
        shopping_items_temp[0] = item_name;
        shopping_count_temp[0] = item_count;
        shopping_owner_images_temp[0] = list_background_image;
        shopping_images_temp[0] = setFoodImage(item_name);
        //update global references
        shopping_list = shopping_items_temp.clone();
        shopping_count = shopping_count_temp.clone();
        shopping_owner_images = shopping_owner_images_temp.clone();
        shopping_list_images = shopping_images_temp.clone();
    }

}