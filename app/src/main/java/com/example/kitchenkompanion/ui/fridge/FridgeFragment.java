package com.example.kitchenkompanion.ui.fridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kitchenkompanion.FridgeListAdapter;
import com.example.kitchenkompanion.R;
import com.example.kitchenkompanion.databinding.FragmentFridgeBinding;

public class FridgeFragment extends Fragment {
    //Data for Communal Items
    String communal_items[] = {"Apple", "Banana", "Whole Milk", "Orange Juice", "Bread", "Cake", "Rice", "Chicken Breast"};
    int communal_images[] = {R.drawable.ic_food_apple, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp};
    String communal_count[] = {"1" ,"2", "1 Gal", "2 Gal", "1 Loaf", "2 Slices", "3 Pounds", "2"};
    int communal_onwer_images[] = {R.drawable.communal_list_item, R.drawable.communal_list_item, R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item,R.drawable.communal_list_item};
    //Data for Private Items
    String private_items[] = {"Private 1", "Private 2", "Private 3", "Private 4", "Private 5", "Private 6", "Private 7"};
    int private_images[] = {R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp};
    String private_count[] = {"1", "2 Pounds", "2 Slices", "1 Gal", "3", "2 Pounds", "1 Gal"};
    int private_owner_images[] = {R.drawable.private_list_owner2, R.drawable.private_list_owner2, R.drawable.private_list_owner2, R.drawable.private_list_owner3, R.drawable.private_list_owner3, R.drawable.private_list_owner4, R.drawable.private_list_owner1};

    ListView listView;

    private FragmentFridgeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FridgeViewModel fridgeViewModel =
                new ViewModelProvider(this).get(FridgeViewModel.class);

        binding = FragmentFridgeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = (ListView) root.findViewById(R.id.fridge_list);
        FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), communal_items, communal_images, communal_count, communal_onwer_images);
        listView.setAdapter(fridgeListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast toast = Toast.makeText(getActivity(), "Item " + i + " pressed", Toast.LENGTH_SHORT );
                toast.show();
            }
        });
        Button communal_button = (Button) root.findViewById(R.id.fridge_list_communal_button);
        communal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView listView = (ListView) root.findViewById(R.id.fridge_list);
                FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), communal_items, communal_images, communal_count, communal_onwer_images);
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
        Button private_button = (Button) root.findViewById(R.id.fridge_list_private_button);
        private_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView listView = (ListView) root.findViewById(R.id.fridge_list);
                FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), private_items, private_images, private_count, private_owner_images);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}