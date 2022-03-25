package com.example.kitchenkompanion.ui.fridge;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kitchenkompanion.FridgeListAdapter;
import com.example.kitchenkompanion.MainActivity;
import com.example.kitchenkompanion.R;
import com.example.kitchenkompanion.databinding.FragmentFridgeBinding;

import java.util.Arrays;

public class FridgeFragment extends Fragment {
    //global variables for the list and buttons
    ListView listView;
    View view1;
    Button global_private;
    Button global_communal;

    private FragmentFridgeBinding binding;

    //Direct Add Popup Information
    Context thisContext;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText direct_name, direct_count;
    private Button direct_add_cancel, direct_add_save;
    private ToggleButton direct_add_toggle;
    private Spinner counter_dropdown;

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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FridgeViewModel fridgeViewModel =
                new ViewModelProvider(this).get(FridgeViewModel.class);

        binding = FragmentFridgeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        view1 = root;

        //create options menu for direct add and search buttons
        setHasOptionsMenu(true);
        //create communal list view by default
        ListView listView = (ListView) root.findViewById(R.id.fridge_list);
        FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.communal_items, MainActivity.communal_images, MainActivity.communal_count, MainActivity.communal_onwer_images);
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
        //change color of buttons depeding on selected list
        Button communal_button = (Button) root.findViewById(R.id.fridge_list_communal_button);
        Button private_button = (Button) root.findViewById(R.id.fridge_list_private_button);
        global_communal = communal_button;
        global_private = private_button;
        //make clicking buttons switch lists
        communal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCommunalList(root);
            }
        });
        private_button.setAlpha((float).6);
        private_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPrivateList(root);
            }
        });
        return root;
    }

    //add the little plus sign to the top right for direct add
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fridge_list_menu, menu);
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
                item_count = item_count + " " + item_counter;
                Boolean private_list = direct_add_toggle.isChecked();
                //if empty info, create warning
                if (item_name.isEmpty()|| item_count.isEmpty()) {
                    emptyTextPopup();
                } else if (private_list == true) {
                    //if duplicate item added, create warning
                    if (Arrays.asList(MainActivity.private_items).contains(item_name)) {
                        int length = MainActivity.private_items.length;
                        int i = 0;
                        int curr_index;
                        boolean dupe_found = false;
                        //check if duplicate belongs to user 1
                        while (i < length) {
                            if (MainActivity.private_items[i].equals(item_name)) {
                                curr_index = i;
                                //if duplicate belongs to user 1, create error message
                                if (MainActivity.private_owner_images[curr_index] == R.drawable.private_list_owner1) {
                                    dupe_found = true;
                                    duplicateItemPopup(item_name, item_count, private_list);
                                    break;
                                } else {
                                    i = i + 1;
                                }
                            } else {
                                i = i + 1;
                            }
                        }
                        //if duplicate does not belong to user 1
                        //add item normally
                        if (dupe_found == false) {
                            MainActivity.addToFridgePrivate(item_name, item_count);
                            viewPrivateList(view1);
                            dialog.dismiss();
                        }
                    } else {
                        //add to private list
                        MainActivity.addToFridgePrivate(item_name, item_count);
                        viewPrivateList(view1);
                        dialog.dismiss();
                    }
                } else {
                    //if duplicate item added, create warning
                    if (Arrays.asList(MainActivity.communal_items).contains(item_name)) {
                        duplicateItemPopup(item_name, item_count, private_list);
                    } else {
                        //add to communal list
                        MainActivity.addToFridgeCommunal(item_name, item_count);
                        viewCommunalList(view1);
                        dialog.dismiss();
                    }
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
        final View foodInfoPopupView = getLayoutInflater().inflate(R.layout.fridge_item_information_popup, null);
        switch_list_button = (Button) foodInfoPopupView.findViewById(R.id.switch_button);
        remove_button = (Button) foodInfoPopupView.findViewById(R.id.remove_button);
        close_button = (Button) foodInfoPopupView.findViewById(R.id.close_button);
        increase_button = (Button) foodInfoPopupView.findViewById(R.id.increase_count);
        decrease_button = (Button) foodInfoPopupView.findViewById(R.id.decrease_count);

        food_name = (TextView) foodInfoPopupView.findViewById(R.id.name_text);
        food_count = (TextView) foodInfoPopupView.findViewById(R.id.count_text);
        food_owner = (TextView) foodInfoPopupView.findViewById(R.id.owner_text);
        food_nutrition = (TextView) foodInfoPopupView.findViewById(R.id.info_text);

        food_image = (ImageView) foodInfoPopupView.findViewById(R.id.food_image);

        boolean privateList = false;

        //get communal or private list and owner
        if (owner != R.drawable.communal_list_item) {
            privateList = true;
            if (owner == R.drawable.private_list_owner1) {
                switch_list_button.setText("Switch to Communal");
                food_owner.setText("User 1");
            } else if (owner == R.drawable.private_list_owner2) {
                switch_list_button.setVisibility(View.GONE);
                remove_button.setVisibility(View.GONE);
                increase_button.setVisibility(View.GONE);
                decrease_button.setVisibility(View.GONE);
                food_owner.setText("User 2");
            } else if (owner == R.drawable.private_list_owner3) {
                switch_list_button.setVisibility(View.GONE);
                remove_button.setVisibility(View.GONE);
                increase_button.setVisibility(View.GONE);
                decrease_button.setVisibility(View.GONE);
                food_owner.setText("User 3");
            } else {
                switch_list_button.setVisibility(View.GONE);
                remove_button.setVisibility(View.GONE);
                increase_button.setVisibility(View.GONE);
                decrease_button.setVisibility(View.GONE);
                food_owner.setText("User 4");
            }
        } else {
            privateList = false;
            switch_list_button.setText("Switch to Private");
            food_owner.setText("Communal");
        }

        //set text fields to match current item
        food_name.setText(name);
        food_count.setText(count);
        food_image.setImageResource(image);

        //hard-coded nutrition facts for certain items
        if (name.equalsIgnoreCase("Apple")) {
            food_nutrition.setText("\nCalories: 83 \n\nSugar: 10.4g \n\nFat: 0.2g \n\nCarbs: 13.8g");
        } else {
            //default case, no hard coded item match
            food_nutrition.setText("\nCalories: N/A \n\nSugar: N/A \n\nFat: N/A \n\nCarbs: N/A");
        }


        dialogBuilder3.setView(foodInfoPopupView);
        dialog3 = dialogBuilder3.create();
        dialog3.show();

        //close food info popup
        boolean finalPrivateList = privateList;
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalPrivateList) {
                    viewPrivateList(view1);
                } else {
                    viewCommunalList(view1);
                }
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

        //switch currently selected food item to other list
        switch_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFridgeItem(index, finalPrivateList);
                if (finalPrivateList) {
                    MainActivity.addToFridgeCommunal(name, count);
                    viewCommunalList(view1);
                } else {
                    MainActivity.addToFridgePrivate(name, count);
                    viewPrivateList(view1);
                }
                dialog3.dismiss();
            }
        });

        //increase count of item by 1
        increase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int string_to_int, new_count;
                final int number1 = 1;
                String number, counter, new_string;
                if (finalPrivateList) {
                    number = MainActivity.private_count[index].substring(0, MainActivity.private_count[index].indexOf(' '));
                    counter = MainActivity.private_count[index].substring(MainActivity.private_count[index].indexOf(' ') + 1);
                    string_to_int = Integer.parseInt(number);
                    new_count = string_to_int + number1;
                    new_string = String.valueOf(new_count);
                    MainActivity.private_count[index] = new_string + " " + counter;
                    food_count.setText(MainActivity.private_count[index]);
                } else {
                    number = MainActivity.communal_count[index].substring(0, MainActivity.communal_count[index].indexOf(' '));
                    counter = MainActivity.communal_count[index].substring(MainActivity.communal_count[index].indexOf(' ') + 1);
                    string_to_int = Integer.parseInt(number);
                    new_count = string_to_int + number1;
                    new_string = String.valueOf(new_count);
                    MainActivity.communal_count[index] = new_string + " " + counter;
                    food_count.setText(MainActivity.communal_count[index]);
                }
            }
        });

        //decrease count of item by 1
        decrease_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int string_to_int, new_count;
                final int number1 = -1;
                String number, counter, new_string;
                if (finalPrivateList) {
                    number = MainActivity.private_count[index].substring(0, MainActivity.private_count[index].indexOf(' '));
                    counter = MainActivity.private_count[index].substring(MainActivity.private_count[index].indexOf(' ') + 1);
                    string_to_int = Integer.parseInt(number);
                    new_count = string_to_int + number1;
                    if (new_count <= 0) {
                        confirmRemovePopup(true, index, finalPrivateList);
                    } else {
                        new_string = String.valueOf(new_count);
                        MainActivity.private_count[index] = new_string + " " + counter;
                        food_count.setText(MainActivity.private_count[index]);
                    }
                } else {
                    number = MainActivity.communal_count[index].substring(0, MainActivity.communal_count[index].indexOf(' '));
                    counter = MainActivity.communal_count[index].substring(MainActivity.communal_count[index].indexOf(' ') + 1);
                    string_to_int = Integer.parseInt(number);
                    new_count = string_to_int + number1;
                    if (new_count <= 0) {
                        confirmRemovePopup(true, index, finalPrivateList);
                    } else {
                        new_string = String.valueOf(new_count);
                        MainActivity.communal_count[index] = new_string + " " + counter;
                        food_count.setText(MainActivity.communal_count[index]);
                    }
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
                if (private_list) {
                    MainActivity.addToFridgePrivate(item_name, item_count);
                    viewPrivateList(view1);
                } else {
                    MainActivity.addToFridgeCommunal(item_name, item_count);
                    viewCommunalList(view1);
                }
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
                if (private_list) {
                    //get index of existing item
                    length = MainActivity.private_items.length;
                    while (i < length) {
                        if (MainActivity.private_items[i].equals(item_name)) {
                            curr_index = i;
                            //add item_count to existing item's count
                            number = MainActivity.private_count[curr_index].substring(0, MainActivity.private_count[curr_index].indexOf(' '));
                            counter = MainActivity.private_count[curr_index].substring(MainActivity.private_count[curr_index].indexOf(' ') + 1);
                            number1 = item_count.substring(0, item_count.indexOf(' '));
                            string_to_int = Integer.parseInt(number);
                            string_to_int1= Integer.parseInt(number1);
                            new_count = string_to_int + string_to_int1;
                            new_string = String.valueOf(new_count);
                            MainActivity.private_count[curr_index] = new_string + " " + counter;
                            viewPrivateList(view1);
                            break;
                        } else {
                            i = i + 1;
                        }
                    }
                } else {
                    //get index of existing item
                    length = MainActivity.communal_items.length;
                    while (i < length) {
                        if (MainActivity.communal_items[i].equals(item_name)) {
                            curr_index = i;
                            //add item_count to existing item's count
                            number = MainActivity.communal_count[curr_index].substring(0, MainActivity.communal_count[curr_index].indexOf(' '));
                            counter = MainActivity.communal_count[curr_index].substring(MainActivity.communal_count[curr_index].indexOf(' ') + 1);
                            number1 = item_count.substring(0, item_count.indexOf(' '));
                            string_to_int = Integer.parseInt(number);
                            string_to_int1= Integer.parseInt(number1);
                            new_count = string_to_int + string_to_int1;
                            new_string = String.valueOf(new_count);
                            MainActivity.communal_count[curr_index] = new_string + " " + counter;
                            viewCommunalList(view1);
                            break;
                        } else {
                            i = i + 1;
                        }
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
        if (privateList) {
            //remove from private list
            list_length = MainActivity.private_items.length;
            String private_items_temp[] = new String[list_length - 1];
            String private_count_temp[] = new String[list_length - 1];
            int private_owner_images_temp[] = new int[list_length - 1];
            int private_images_temp[] = new int[list_length - 1];

            for (int i = 0; i < list_length; i++) {
                if (i != index) {
                    private_items_temp[counter] = MainActivity.private_items[i];
                    private_count_temp[counter] = MainActivity.private_count[i];
                    private_owner_images_temp[counter] = MainActivity.private_owner_images[i];
                    private_images_temp[counter] = MainActivity.private_images[i];
                    counter = counter + 1;
                }
            }
            MainActivity.private_items = private_items_temp.clone();
            MainActivity.private_count = private_count_temp.clone();
            MainActivity.private_owner_images = private_owner_images_temp.clone();
            MainActivity.private_images = private_images_temp.clone();

            viewPrivateList(view1);
        } else {
            //remove from communal list
            list_length = MainActivity.communal_items.length;
            String communal_items_temp[] = new String[list_length - 1];
            String communal_count_temp[] = new String[list_length - 1];
            int communal_owner_images_temp[] = new int[list_length - 1];
            int communal_images_temp[] = new int[list_length - 1];

            for (int i = 0; i < list_length; i++) {
                if (i != index) {
                    communal_items_temp[counter] = MainActivity.communal_items[i];
                    communal_count_temp[counter] = MainActivity.communal_count[i];
                    communal_owner_images_temp[counter] = MainActivity.communal_onwer_images[i];
                    communal_images_temp[counter] = MainActivity.communal_images[i];
                    counter = counter + 1;
                }
            }
            MainActivity.communal_items = communal_items_temp.clone();
            MainActivity.communal_count = communal_count_temp.clone();
            MainActivity.communal_onwer_images = communal_owner_images_temp.clone();
            MainActivity.communal_images = communal_images_temp.clone();

            viewCommunalList(view1);
        }

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

    public void viewCommunalList(View root) {
        global_private.setAlpha((float).6);
        global_communal.setAlpha((float)1);
        ListView listView = (ListView) root.findViewById(R.id.fridge_list);
        FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.communal_items, MainActivity.communal_images, MainActivity.communal_count, MainActivity.communal_onwer_images);
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

    public void viewPrivateList(View root) {
        global_private.setAlpha((float)1);
        global_communal.setAlpha((float).6);
        ListView listView = (ListView) root.findViewById(R.id.fridge_list);
        FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.private_items, MainActivity.private_images, MainActivity.private_count, MainActivity.private_owner_images);
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