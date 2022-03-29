package com.example.kitchenkompanion.ui.shop;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kitchenkompanion.FridgeListAdapter;
import com.example.kitchenkompanion.MainActivity;
import com.example.kitchenkompanion.R;
import com.example.kitchenkompanion.databinding.FragmentFridgeBinding;
import com.example.kitchenkompanion.databinding.FragmentShopBinding;

import org.w3c.dom.Text;

import java.util.Arrays;

public class ShopFragment extends Fragment {
    //global variables for the list and buttons
    ListView listView;
    View view1;

    //Direct Add Popup Information
    Context thisContext;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText direct_name, direct_count;
    private Button direct_add_cancel, direct_add_save;
    private ToggleButton direct_add_toggle;
    private Spinner counter_dropdown;
    private TextView title_text, list_to_add_text;

    //Empty Text Field Popup Information
    private AlertDialog.Builder dialogBuilder1;
    private AlertDialog dialog1;
    private Button confirm_no_empty;

    //Duplicate Item Added Popup Information
    private AlertDialog.Builder dialogBuilder2;
    private AlertDialog dialog2;
    private Button confirm_add, increase_count, cancel_add;

    //Item Information Popup
    private AlertDialog.Builder dialogBuilder3;
    private AlertDialog dialog3;
    private Button switch_list_button, remove_button, close_button, increase_button, decrease_button;
    private TextView food_name, food_count, food_owner, food_nutrition;
    private ImageView food_image;

    //confirm remove Popup
    private AlertDialog.Builder dialogBuilder4;
    private AlertDialog dialog4;
    private Button cancel_button, confirm_button;
    private TextView confirm_message;

    //confirm purchase popup
    private AlertDialog.Builder dialogBuilder5;
    private AlertDialog dialog5;
    private Button no_purchase_button, purchase_button;
    private TextView purchase_message;

    //save list as preset popup
    private AlertDialog.Builder dialogBuilder6;
    private AlertDialog dialog6;
    private EditText listName;
    private Spinner saveSlot;
    private Button save_button, no_save_button;

    //load a preset list
    private AlertDialog.Builder dialogBuilder7;
    private AlertDialog dialog7;
    private Button list_one_button, list_two_button, list_three_button, list_four_button, cancel_preset;
    private TextView text_one, text_two, text_three, text_four;

    //error cant save empty list
    private AlertDialog.Builder dialogBuilder8;
    private AlertDialog dialog8;
    private Button close_empty;
    private TextView text1, text2;

    //overwrite preset popup
    private AlertDialog.Builder dialogBuilder9;
    private AlertDialog dialog9;
    private Button overwrite_button, cancel_overwrite;
    private TextView overwrite_text;


    private FragmentShopBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopViewModel shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        view1 = root;

        //create options menu for direct add and search buttons
        setHasOptionsMenu(true);
        //create communal list view by default
        ListView listView = (ListView) root.findViewById(R.id.shopping_list);
        FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.shopping_list, MainActivity.shopping_list_images, MainActivity.shopping_count, MainActivity.shopping_owner_images);
        listView.setAdapter(fridgeListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String curr_name = fridgeListAdapter.getItemName(i);
                String curr_count = fridgeListAdapter.getItemCount(i);
                int curr_owner = fridgeListAdapter.getItemOwner(i);
                int curr_image = fridgeListAdapter.getItemImage(i);
                foodInfoPopup(curr_name, curr_count, curr_owner, curr_image, i);
            }
        });

        return root;
    }

    //add the little plus sign to the top right for direct add
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shop_list_menu, menu);
    }

    //make the options menus button do stuff
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.direct_add) {
            //make direct add popup when clicking plus sign
            directAddDialog();
            return true;
        }
        if (id == R.id.save_current_list) {
            saveCurrentListAsPreset();
            return true;
        }

        if (id == R.id.preset_shopping_lists) {
            selectPresetPopup();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //direct add popup
    public void directAddDialog() {
        //get variables from layout
        dialogBuilder = new AlertDialog.Builder(thisContext);
        final View addPopupView = getLayoutInflater().inflate(R.layout.fridge_direct_add_popup, null);
        direct_name = (EditText) addPopupView.findViewById(R.id.direct_name);
        direct_count = (EditText) addPopupView.findViewById(R.id.direct_count);
        direct_add_save = (Button) addPopupView.findViewById(R.id.direct_add_save);
        direct_add_cancel = (Button) addPopupView.findViewById(R.id.direct_add_cancel);
        direct_add_toggle = (ToggleButton) addPopupView.findViewById(R.id.direct_add_private_toggle);
        counter_dropdown = (Spinner) addPopupView.findViewById(R.id.counter_dropdown);

        title_text = (TextView) addPopupView.findViewById(R.id.title_text);
        title_text.setText("Add To Shopping List");
        list_to_add_text = (TextView) addPopupView.findViewById(R.id.direct_add_text);
        list_to_add_text.setText("Set Ownership");

        //show dialog
        dialogBuilder.setView(addPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        //numbers only for quantity input
        direct_count.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        //set selection of choices for number counters
        String[] counters = new String[]{"None", "Gal", "Pound", "Slice", "Loaf"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(thisContext, android.R.layout.simple_spinner_dropdown_item, counters);
        counter_dropdown.setAdapter(adapter);

        //add information to list when add button is clicked
        direct_add_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_name = direct_name.getText().toString();
                String item_count = direct_count.getText().toString();
                TextView counter_text = (TextView) counter_dropdown.getSelectedView();
                String item_counter = counter_text.getText().toString();
                if (item_counter.equals("None")) {
                    item_counter = " ";
                }
                Boolean private_list = direct_add_toggle.isChecked();
                //if empty info, create warning
                if (item_name.isEmpty()|| item_count.isEmpty()) {
                    emptyTextPopup();
                } else if (Arrays.asList(MainActivity.shopping_list).contains(item_name)) {
                    item_count = item_count + " " + item_counter;
                    //if duplicate item added, create warning
                    duplicateItemPopup(item_name, item_count, private_list);
                } else {
                    item_count = item_count + " " + item_counter;
                    //add to shopping list
                    MainActivity.addToShoppingList(item_name, item_count, private_list);
                    viewShoppingList(view1);
                    dialog.dismiss();
                }
            }
        });

        //close popup when click cancel
        direct_add_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    //food info popup
    public void foodInfoPopup(String name, String count, int owner, int image, int index) {
        dialogBuilder3 = new AlertDialog.Builder(thisContext);
        final View foodInfoPopupView = getLayoutInflater().inflate(R.layout.shop_confirm_purchase_popup, null);
        switch_list_button = (Button) foodInfoPopupView.findViewById(R.id.switch_button);
        remove_button = (Button) foodInfoPopupView.findViewById(R.id.remove_button);
        close_button = (Button) foodInfoPopupView.findViewById(R.id.close_button);
        increase_button = (Button) foodInfoPopupView.findViewById(R.id.increase_count);
        decrease_button = (Button) foodInfoPopupView.findViewById(R.id.decrease_count);

        food_name = (TextView) foodInfoPopupView.findViewById(R.id.name_text);
        food_count = (TextView) foodInfoPopupView.findViewById(R.id.count_text);
        food_owner = (TextView) foodInfoPopupView.findViewById(R.id.owner_text);

        food_image = (ImageView) foodInfoPopupView.findViewById(R.id.food_image);

        boolean privateList = false;

        //get communal or private list and owner
        if (owner != R.drawable.communal_list_item) {
            privateList = true;
            food_owner.setText("User 1");
        } else {
            privateList = false;
            food_owner.setText("Communal");
        }

        //set text fields to match current item
        food_name.setText(name);
        food_count.setText(count);
        food_image.setImageResource(image);

        dialogBuilder3.setView(foodInfoPopupView);
        dialog3 = dialogBuilder3.create();
        dialog3.show();

        //confrim purchase, add to fridge list
        boolean finalPrivateList = privateList;
        switch_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPurchasePopup(name, MainActivity.shopping_count[index], index, finalPrivateList);
            }
        });

        //close food info popup
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewShoppingList(view1);
                dialog3.dismiss();
            }
        });

        //remove currently selected food item
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmRemovePopup(false, index, finalPrivateList);
            }
        });

        //increase count of item by 1
        increase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int string_to_int, new_count;
                final int number1 = 1;
                String number, counter, new_string;

                number = MainActivity.shopping_count[index].substring(0, MainActivity.shopping_count[index].indexOf(' '));
                counter = MainActivity.shopping_count[index].substring(MainActivity.shopping_count[index].indexOf(' ') + 1);
                string_to_int = Integer.parseInt(number);
                new_count = string_to_int + number1;
                new_string = String.valueOf(new_count);
                MainActivity.shopping_count[index] = new_string + " " + counter;
                food_count.setText(MainActivity.shopping_count[index]);
            }
        });

        //decrease count of item by 1
        decrease_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int string_to_int, new_count;
                final int number1 = -1;
                String number, counter, new_string;

                number = MainActivity.shopping_count[index].substring(0, MainActivity.shopping_count[index].indexOf(' '));
                counter = MainActivity.shopping_count[index].substring(MainActivity.shopping_count[index].indexOf(' ') + 1);
                string_to_int = Integer.parseInt(number);
                new_count = string_to_int + number1;
                if (new_count <= 0) {
                    confirmRemovePopup(true, index, finalPrivateList);
                } else {
                    new_string = String.valueOf(new_count);
                    MainActivity.shopping_count[index] = new_string + " " + counter;
                    food_count.setText(MainActivity.shopping_count[index]);
                }
            }
        });
    }

    //make empty text popup
    public void emptyTextPopup() {
        dialogBuilder1 = new AlertDialog.Builder(thisContext);
        final View emptyPopupView = getLayoutInflater().inflate(R.layout.fridge_direct_add_empty_popup, null);
        confirm_no_empty = (Button) emptyPopupView.findViewById(R.id.return_button);

        dialogBuilder1.setView(emptyPopupView);
        dialog1 = dialogBuilder1.create();
        dialog1.show();

        confirm_no_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
    }

    //make duplicate item added popup
    public void duplicateItemPopup(String item_name, String item_count, Boolean private_list) {
        dialogBuilder2 = new AlertDialog.Builder(thisContext);
        final View duplicatePopupView = getLayoutInflater().inflate(R.layout.fridge_direct_add_duplicate_popup, null);
        confirm_add = (Button) duplicatePopupView.findViewById(R.id.confirm_add_button);
        increase_count = (Button) duplicatePopupView.findViewById(R.id.increase_exisiting_count_button);
        cancel_add = (Button) duplicatePopupView.findViewById(R.id.cancel_button1);

        dialogBuilder2.setView(duplicatePopupView);
        dialog2 = dialogBuilder2.create();
        dialog2.show();

        //add duplicate item anyway
        confirm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.addToShoppingList(item_name, item_count, private_list);
                viewShoppingList(view1);
                dialog2.dismiss();
                dialog.dismiss();
            }
        });
        //dont add duplicate item, but increase quantity of already existing item
        increase_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curr_index;
                int length;
                int i = 0;
                int new_count;
                String new_string;
                int string_to_int;
                int string_to_int1;
                String number;
                String number1;
                String counter;

                //get index of existing item
                length = MainActivity.shopping_list.length;
                while (i < length) {
                    if (MainActivity.shopping_list[i].equals(item_name)) {
                        curr_index = i;
                        //add item_count to existing item's count
                        number = MainActivity.shopping_count[curr_index].substring(0, MainActivity.shopping_count[curr_index].indexOf(' '));
                        counter = MainActivity.shopping_count[curr_index].substring(MainActivity.shopping_count[curr_index].indexOf(' ') + 1);
                        number1 = item_count.substring(0, item_count.indexOf(' '));
                        string_to_int = Integer.parseInt(number);
                        string_to_int1= Integer.parseInt(number1);
                        new_count = string_to_int + string_to_int1;
                        new_string = String.valueOf(new_count);
                        MainActivity.shopping_count[curr_index] = new_string + " " + counter;
                        viewShoppingList(view1);
                        break;
                    } else {
                        i = i + 1;
                    }
                }
                dialog2.dismiss();
                dialog.dismiss();
            }
        });
        //go back to direct add screen
        cancel_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
    }

    public void removeFridgeItem(int index, boolean privateList) {
        int list_length;
        int counter = 0;

        //remove from communal list
        list_length = MainActivity.shopping_list.length;
        String shopping_items_temp[] = new String[list_length - 1];
        String shopping_count_temp[] = new String[list_length - 1];
        int shopping_owner_images_temp[] = new int[list_length - 1];
        int shopping_images_temp[] = new int[list_length - 1];

        for (int i = 0; i < list_length; i++) {
            if (i != index) {
                shopping_items_temp[counter] = MainActivity.shopping_list[i];
                shopping_count_temp[counter] = MainActivity.shopping_count[i];
                shopping_owner_images_temp[counter] = MainActivity.shopping_owner_images[i];
                shopping_images_temp[counter] = MainActivity.shopping_list_images[i];
                counter = counter + 1;
            }
        }
        MainActivity.shopping_list = shopping_items_temp.clone();
        MainActivity.shopping_count = shopping_count_temp.clone();
        MainActivity.shopping_owner_images = shopping_owner_images_temp.clone();
        MainActivity.shopping_list_images = shopping_images_temp.clone();
        viewShoppingList(view1);
    }

    public void saveCurrentListAsPreset() {
        dialogBuilder6 = new AlertDialog.Builder(thisContext);
        final View saveCurrentListPopup = getLayoutInflater().inflate(R.layout.shop_save_current_list_as_popup, null);
        listName = (EditText) saveCurrentListPopup.findViewById(R.id.direct_name);
        saveSlot = (Spinner) saveCurrentListPopup.findViewById(R.id.counter_dropdown);
        save_button = (Button) saveCurrentListPopup.findViewById(R.id.direct_add_save);
        no_save_button = (Button) saveCurrentListPopup.findViewById(R.id.direct_add_cancel);

        dialogBuilder6.setView(saveCurrentListPopup);
        dialog6 = dialogBuilder6.create();
        dialog6.show();

        String[] counters = new String[]{"1", "2", "3", "4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(thisContext, android.R.layout.simple_spinner_dropdown_item, counters);
        saveSlot.setAdapter(adapter);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_name = listName.getText().toString();
                TextView counter_text = (TextView) saveSlot.getSelectedView();
                String item_counter = counter_text.getText().toString();
                boolean flag;
                if (MainActivity.shopping_list.length == 0) {
                    flag = true;
                    emptyPresetErrorPopup(flag);
                    dialog6.dismiss();
                } else if (item_name.length() == 0) {
                    flag = false;
                    emptyPresetErrorPopup(flag);
                } else
                if (item_counter.equals("1")) {
                    if (MainActivity.preset1_name.length() != 0) {
                        overwritePresetPopup(1, item_name);
                    } else {
                        MainActivity.preset_list1 = MainActivity.shopping_list.clone();
                        MainActivity.preset_images1 = MainActivity.shopping_list_images.clone();
                        MainActivity.preset_count1 = MainActivity.shopping_count.clone();
                        MainActivity.preset_owner_images1 = MainActivity.shopping_owner_images.clone();
                        MainActivity.preset1_name = item_name;
                        dialog6.dismiss();
                    }
                } else if (item_counter.equals("2")) {
                    if (MainActivity.preset2_name.length() != 0) {
                        overwritePresetPopup(2, item_name);
                    } else {
                        MainActivity.preset_list2 = MainActivity.shopping_list.clone();
                        MainActivity.preset_images2 = MainActivity.shopping_list_images.clone();
                        MainActivity.preset_count2 = MainActivity.shopping_count.clone();
                        MainActivity.preset_owner_images2 = MainActivity.shopping_owner_images.clone();
                        MainActivity.preset2_name = item_name;
                        dialog6.dismiss();
                    }
                } else if (item_counter.equals("3")) {
                    if (MainActivity.preset3_name.length() != 0) {
                        overwritePresetPopup(3, item_name);
                    } else {
                        MainActivity.preset_list3 = MainActivity.shopping_list.clone();
                        MainActivity.preset_images3 = MainActivity.shopping_list_images.clone();
                        MainActivity.preset_count3 = MainActivity.shopping_count.clone();
                        MainActivity.preset_owner_images3 = MainActivity.shopping_owner_images.clone();
                        MainActivity.preset3_name = item_name;
                        dialog6.dismiss();
                    }
                } else if (MainActivity.preset4_name.length() != 0) {
                    overwritePresetPopup(4, item_name);
                } else {
                    MainActivity.preset_list4 = MainActivity.shopping_list.clone();
                    MainActivity.preset_images4 = MainActivity.shopping_list_images.clone();
                    MainActivity.preset_count4 = MainActivity.shopping_count.clone();
                    MainActivity.preset_owner_images4 = MainActivity.shopping_owner_images.clone();
                    MainActivity.preset4_name = item_name;
                    dialog6.dismiss();
                }
            }
        });

        no_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog6.dismiss();
            }
        });
    }

    public void overwritePresetPopup(int index, String item_name) {
        dialogBuilder9 = new AlertDialog.Builder(thisContext);
        final View overwritePresetPopupView = getLayoutInflater().inflate(R.layout.shop_overwrite_preset_popup, null);
        overwrite_button = (Button) overwritePresetPopupView.findViewById(R.id.confirm_button78);
        cancel_overwrite = (Button) overwritePresetPopupView.findViewById(R.id.cancel_button78);
        overwrite_text = (TextView) overwritePresetPopupView.findViewById(R.id.remove_confirm_text);

        dialogBuilder9.setView(overwritePresetPopupView);
        dialog9 = dialogBuilder9.create();
        dialog9.show();

        if (index == 1) {
            overwrite_text.setText("Save Slot 1 Already Contains " + MainActivity.preset1_name + "\nAre You Sure You Want To Overwrite?");
        } else if (index == 2) {
            overwrite_text.setText("Save Slot 2 Already Contains " + MainActivity.preset2_name + "\nAre You Sure You Want To Overwrite?");
        } else if (index == 3) {
            overwrite_text.setText("Save Slot 3 Already Contains " + MainActivity.preset3_name + "\nAre You Sure You Want To Overwrite?");
        } else {
            overwrite_text.setText("Save Slot 4 Already Contains " + MainActivity.preset4_name + "\nAre You Sure You Want To Overwrite?");
        }

        overwrite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index == 1) {
                    MainActivity.preset_list1 = MainActivity.shopping_list.clone();
                    MainActivity.preset_images1 = MainActivity.shopping_list_images.clone();
                    MainActivity.preset_count1 = MainActivity.shopping_count.clone();
                    MainActivity.preset_owner_images1 = MainActivity.shopping_owner_images.clone();
                    MainActivity.preset1_name = item_name;
                    dialog6.dismiss();
                    dialog9.dismiss();
                } else if (index == 2) {
                    MainActivity.preset_list2 = MainActivity.shopping_list.clone();
                    MainActivity.preset_images2 = MainActivity.shopping_list_images.clone();
                    MainActivity.preset_count2 = MainActivity.shopping_count.clone();
                    MainActivity.preset_owner_images2 = MainActivity.shopping_owner_images.clone();
                    MainActivity.preset2_name = item_name;
                    dialog6.dismiss();
                    dialog9.dismiss();
                } else if (index == 3) {
                    MainActivity.preset_list3 = MainActivity.shopping_list.clone();
                    MainActivity.preset_images3 = MainActivity.shopping_list_images.clone();
                    MainActivity.preset_count3 = MainActivity.shopping_count.clone();
                    MainActivity.preset_owner_images3 = MainActivity.shopping_owner_images.clone();
                    MainActivity.preset3_name = item_name;
                    dialog6.dismiss();
                    dialog9.dismiss();
                } else {
                    MainActivity.preset_list4 = MainActivity.shopping_list.clone();
                    MainActivity.preset_images4 = MainActivity.shopping_list_images.clone();
                    MainActivity.preset_count4 = MainActivity.shopping_count.clone();
                    MainActivity.preset_owner_images4 = MainActivity.shopping_owner_images.clone();
                    MainActivity.preset4_name = item_name;
                    dialog6.dismiss();
                    dialog9.dismiss();
                }
            }
        });

        cancel_overwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog6.dismiss();
                dialog9.dismiss();
            }
        });
    }

    public void selectPresetPopup() {
        dialogBuilder7 = new AlertDialog.Builder(thisContext);
        final View selectPresetPopupView = getLayoutInflater().inflate(R.layout.shop_select_preset_list, null);
        list_one_button = (Button) selectPresetPopupView.findViewById(R.id.image_one);
        list_two_button = (Button) selectPresetPopupView.findViewById(R.id.image_two);
        list_three_button = (Button) selectPresetPopupView.findViewById(R.id.image_three);
        list_four_button = (Button) selectPresetPopupView.findViewById(R.id.image_four);
        cancel_preset = (Button) selectPresetPopupView.findViewById(R.id.preset_cancel);
        text_one = (TextView) selectPresetPopupView.findViewById(R.id.text_one);
        text_two = (TextView) selectPresetPopupView.findViewById(R.id.text_two);
        text_three = (TextView) selectPresetPopupView.findViewById(R.id.text_three);
        text_four = (TextView) selectPresetPopupView.findViewById(R.id.text_four);

        dialogBuilder7.setView(selectPresetPopupView);
        dialog7 = dialogBuilder7.create();
        dialog7.show();

        if (MainActivity.preset_list1.length != 0) {
            text_one.setText(MainActivity.preset1_name);
            list_one_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.shopping_list = MainActivity.preset_list1.clone();
                    MainActivity.shopping_count = MainActivity.preset_count1.clone();
                    MainActivity.shopping_list_images = MainActivity.preset_images1.clone();
                    MainActivity.shopping_owner_images = MainActivity.preset_owner_images1.clone();
                    viewShoppingList(view1);
                    dialog7.dismiss();
                }
            });
        } else {
            text_one.setText("Empty");
        }

        if (MainActivity.preset_list2.length != 0) {
            text_two.setText(MainActivity.preset2_name);
            list_two_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.shopping_list = MainActivity.preset_list2.clone();
                    MainActivity.shopping_count = MainActivity.preset_count2.clone();
                    MainActivity.shopping_list_images = MainActivity.preset_images2.clone();
                    MainActivity.shopping_owner_images = MainActivity.preset_owner_images2.clone();
                    viewShoppingList(view1);
                    dialog7.dismiss();
                }
            });
        } else {
            text_two.setText("Empty");
        }

        if (MainActivity.preset_list3.length != 0) {
            text_three.setText(MainActivity.preset3_name);
            list_three_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.shopping_list = MainActivity.preset_list3.clone();
                    MainActivity.shopping_count = MainActivity.preset_count3.clone();
                    MainActivity.shopping_list_images = MainActivity.preset_images3.clone();
                    MainActivity.shopping_owner_images = MainActivity.preset_owner_images3.clone();
                    viewShoppingList(view1);
                    dialog7.dismiss();
                }
            });
        } else {
            text_three.setText("Empty");
        }

        if (MainActivity.preset_list4.length != 0) {
            text_four.setText(MainActivity.preset4_name);
            list_four_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.shopping_list = MainActivity.preset_list4.clone();
                    MainActivity.shopping_count = MainActivity.preset_count4.clone();
                    MainActivity.shopping_list_images = MainActivity.preset_images4.clone();
                    MainActivity.shopping_owner_images = MainActivity.preset_owner_images4.clone();
                    viewShoppingList(view1);
                    dialog7.dismiss();
                }
            });
        } else {
            text_four.setText("Empty");
        }

        cancel_preset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog7.dismiss();
            }
        });
    }

    public void emptyPresetErrorPopup(boolean flag) {
        dialogBuilder8 = new AlertDialog.Builder(thisContext);
        final View showEmptyError = getLayoutInflater().inflate(R.layout.shop_save_current_empty_error_popup, null);
        close_empty = (Button) showEmptyError.findViewById(R.id.return_button);
        text1 = (TextView) showEmptyError.findViewById(R.id.empty_text);
        text2 = (TextView) showEmptyError.findViewById(R.id.empty_text1);

        if (flag == false) {
            text1.setText("Empty List Name Detected");
            text2.setText("Please Enter A List Name");
        }

        dialogBuilder8.setView(showEmptyError);
        dialog8 = dialogBuilder8.create();
        dialog8.show();

        close_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog8.dismiss();
            }
        });
    }


    public void confirmPurchasePopup(String item_name, String item_count, int index, boolean private_list) {
        dialogBuilder5 = new AlertDialog.Builder(thisContext);
        final View confirmPurchasePopup = getLayoutInflater().inflate(R.layout.shop_confirm_purchase_popup1, null);
        no_purchase_button = (Button) confirmPurchasePopup.findViewById(R.id.cancel_button77);
        purchase_button = (Button) confirmPurchasePopup.findViewById(R.id.confirm_button77);
        purchase_message = (TextView) confirmPurchasePopup.findViewById(R.id.remove_confirm_text);

        dialogBuilder5.setView(confirmPurchasePopup);
        dialog5 = dialogBuilder5.create();
        dialog5.show();

        purchase_message.setText("Confirm Purchase of " + item_count + " " + item_name);

        no_purchase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog5.dismiss();
            }
        });

        purchase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (private_list) {
                    MainActivity.addToFridgePrivate(item_name, item_count);
                } else {
                    MainActivity.addToFridgeCommunal(item_name, item_count);
                }
                removeFridgeItem(index, private_list);
                dialog5.dismiss();
                dialog3.dismiss();
            }
        });
    }

    public void confirmRemovePopup(boolean flag, int index, boolean privateList) {
        dialogBuilder4 = new AlertDialog.Builder(thisContext);
        final View confirmRemovePopupView = getLayoutInflater().inflate(R.layout.fridge_remove_confirmation, null);
        cancel_button = (Button) confirmRemovePopupView.findViewById(R.id.cancel_button77);
        confirm_button = (Button) confirmRemovePopupView.findViewById(R.id.confirm_button77);
        confirm_message = (TextView) confirmRemovePopupView.findViewById(R.id.remove_confirm_text);

        if (flag) {
            confirm_message.setText("Quantity reduced to 0. \nDo you want to remove item?");
        } else {
            confirm_message.setText("Are you sure you want to remove item?");
        }

        dialogBuilder4.setView(confirmRemovePopupView);
        dialog4 = dialogBuilder4.create();
        dialog4.show();

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFridgeItem(index, privateList);
                dialog3.dismiss();
                dialog4.dismiss();
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog4.dismiss();
            }
        });
    }

    public void viewShoppingList(View root) {
        ListView listView = (ListView) root.findViewById(R.id.shopping_list);
        FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.shopping_list, MainActivity.shopping_list_images, MainActivity.shopping_count, MainActivity.shopping_owner_images);
        listView.setAdapter(fridgeListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String curr_name = fridgeListAdapter.getItemName(i);
                String curr_count = fridgeListAdapter.getItemCount(i);
                int curr_owner = fridgeListAdapter.getItemOwner(i);
                int curr_image = fridgeListAdapter.getItemImage(i);
                foodInfoPopup(curr_name, curr_count, curr_owner, curr_image, i);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}