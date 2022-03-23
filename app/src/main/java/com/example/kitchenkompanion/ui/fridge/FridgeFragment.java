package com.example.kitchenkompanion.ui.fridge;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class FridgeFragment extends Fragment {
    ListView listView;
    View view1;

    private FragmentFridgeBinding binding;

    //Direct Add Popup Information
    Context thisContext;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText direct_name, direct_count;
    private Button direct_add_cancel, direct_add_save;
    private ToggleButton direct_add_toggle;

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
                Toast toast = Toast.makeText(getActivity(), "Item " + i + " pressed", Toast.LENGTH_SHORT );
                toast.show();
            }
        });
        Button communal_button = (Button) root.findViewById(R.id.fridge_list_communal_button);
        Button private_button = (Button) root.findViewById(R.id.fridge_list_private_button);
        communal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communal_button.setAlpha((float)1);
                private_button.setAlpha((float).6);
                ListView listView = (ListView) root.findViewById(R.id.fridge_list);
                FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.communal_items, MainActivity.communal_images, MainActivity.communal_count, MainActivity.communal_onwer_images);
                listView.setAdapter(fridgeListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast toast = Toast.makeText(getActivity(), "Item " + i + " pressed", Toast.LENGTH_SHORT );
                        toast.show();
                    }
                });
            }
        });
        private_button.setAlpha((float).6);
        private_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communal_button.setAlpha((float).6);
                private_button.setAlpha((float)1);
                ListView listView = (ListView) root.findViewById(R.id.fridge_list);
                FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.private_items, MainActivity.private_images, MainActivity.private_count, MainActivity.private_owner_images);
                listView.setAdapter(fridgeListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast toast = Toast.makeText(getActivity(), "Item " + i + " pressed", Toast.LENGTH_SHORT );
                        toast.show();
                    }
                });
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
        final View contactPopupView = getLayoutInflater().inflate(R.layout.fridge_direct_add_popup, null);
        direct_name = (EditText) contactPopupView.findViewById(R.id.direct_name);
        direct_count = (EditText) contactPopupView.findViewById(R.id.direct_count);
        direct_add_save = (Button) contactPopupView.findViewById(R.id.direct_add_save);
        direct_add_cancel = (Button) contactPopupView.findViewById(R.id.direct_add_cancel);
        direct_add_toggle = (ToggleButton) contactPopupView.findViewById(R.id.direct_add_private_toggle);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        //add information to list when click add
        direct_add_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_name = direct_name.getText().toString();
                String item_count = direct_count.getText().toString();
                Boolean private_list = direct_add_toggle.isChecked();
                int food_image = R.drawable.ic_fridge_black_24dp;
                int list_background_image;
                int list_length;
                if (private_list == true) {
                    //add to private list
                    list_background_image = R.drawable.private_list_owner1;
                } else {
                    //add to communal list
                    list_background_image = R.drawable.communal_list_item;
                    list_length = MainActivity.communal_items.length;
                    //create lists of length + 1
                    String communal_items_temp[] = new String[list_length + 1];
                    String communal_count_temp[] = new String[list_length + 1];
                    int communal_owner_images_temp[] = new int[list_length + 1];
                    int communal_images_temp[] = new int[list_length + 1];
                    //copy elements to temp
                    for (int i = 0; i < list_length; i++) {
                        communal_items_temp[i] = MainActivity.communal_items[i];
                        communal_count_temp[i] = MainActivity.communal_count[i];
                        communal_owner_images_temp[i] = MainActivity.communal_onwer_images[i];
                        communal_images_temp[i] = MainActivity.communal_images[i];
                    }
                    //add new element
                    communal_items_temp[list_length] = item_name;
                    communal_count_temp[list_length] = item_count;
                    communal_owner_images_temp[list_length] = list_background_image;
                    communal_images_temp[list_length] = food_image;
                    //update global references
                    MainActivity.communal_items = communal_items_temp.clone();
                    MainActivity.communal_count = communal_count_temp.clone();
                    MainActivity.communal_onwer_images = communal_owner_images_temp.clone();
                    MainActivity.communal_images = communal_images_temp.clone();

                    ListView listView = (ListView) view1.findViewById(R.id.fridge_list);
                    FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), MainActivity.communal_items, MainActivity.communal_images, MainActivity.communal_count, MainActivity.communal_onwer_images);
                    listView.setAdapter(fridgeListAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast toast = Toast.makeText(getActivity(), "Item " + i + " pressed", Toast.LENGTH_SHORT );
                            toast.show();
                        }
                    });
                }

                dialog.dismiss();

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