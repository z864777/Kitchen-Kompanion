package com.example.kitchenkompanion;

public class foodItem {
    String name;
    int count;
    String count_unit;
    String purchase_date;
    int image;

    public foodItem(String name, int count, String count_unit, String purchase_date) {
        this.name = name;
        this.count = count;
        this.count_unit = count_unit;
        this.purchase_date = purchase_date;
        this.image = getImage(name);
    }

    public int getImage(String item_name) {
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
        } else if (item_name.equalsIgnoreCase("Watermelon")) {
            food_image = R.drawable.food_watermelon;
        } else {
            food_image = R.drawable.ic_fridge_black_24dp;
        }
        return food_image;
    }
}
