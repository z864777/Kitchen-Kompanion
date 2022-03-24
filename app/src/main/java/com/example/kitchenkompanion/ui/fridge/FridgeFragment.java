package com.example.kitchenkompanion.ui.fridge;

import android.app.AlertDialog;
import android.content.Context;
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FridgeViewModel fridgeViewModel =
                new ViewModelProvider(this).get(FridgeViewModel.class);

        binding = FragmentFridgeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        view1 = root;

        //create options menu for direct add and search buttons
        setHasOptionsMenu(true);
        //create list view
        ListView listView = (ListView) root.findViewById(R.id.fridge_list);
        FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.communal_items, MainActivity.communal_images, MainActivity.communal_count, MainActivity.communal_onwer_images);
        listView.setAdapter(fridgeListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast toast = Toast.makeText(getActivity(), "Item " + fridgeListAdapter.getItemName(i) + " pressed", Toast.LENGTH_SHORT );
                toast.show();
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

    //create the options menu for direct add and search
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fridge_list_menu, menu);
    }

    //make the options menus button do stuff
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fridge_list_search) {
            Toast toast = Toast.makeText(getActivity(), "Search Pressed", Toast.LENGTH_SHORT );
            toast.show();
            return true;
        }
        if (id == R.id.direct_add) {
            directAddDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //make direct add popup
    public void directAddDialog() {
        dialogBuilder = new AlertDialog.Builder(thisContext);
        final View addPopupView = getLayoutInflater().inflate(R.layout.fridge_direct_add_popup, null);
        direct_name = (EditText) addPopupView.findViewById(R.id.direct_name);
        direct_count = (EditText) addPopupView.findViewById(R.id.direct_count);
        direct_add_save = (Button) addPopupView.findViewById(R.id.direct_add_save);
        direct_add_cancel = (Button) addPopupView.findViewById(R.id.direct_add_cancel);
        direct_add_toggle = (ToggleButton) addPopupView.findViewById(R.id.direct_add_private_toggle);
        counter_dropdown = (Spinner) addPopupView.findViewById(R.id.counter_dropdown);

        dialogBuilder.setView(addPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        direct_count.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
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

    public void viewCommunalList(View root) {
        global_private.setAlpha((float).6);
        global_communal.setAlpha((float)1);
        ListView listView = (ListView) root.findViewById(R.id.fridge_list);
        FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.communal_items, MainActivity.communal_images, MainActivity.communal_count, MainActivity.communal_onwer_images);
        listView.setAdapter(fridgeListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast toast = Toast.makeText(getActivity(), "Item " + fridgeListAdapter.getItemName(i) + " pressed", Toast.LENGTH_SHORT );
                toast.show();
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
                Toast toast = Toast.makeText(getActivity(), "Item " + fridgeListAdapter.getItemName(i) + " pressed", Toast.LENGTH_SHORT );
                toast.show();
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