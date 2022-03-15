package com.example.kitchenkompanion.ui.fridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    String currfoodlist[] = {"Apple", "Banana", "Whole Milk", "Orange Juice", "Bread", "Cake", "Rice", "Chicken Breast" };
    int currfoodimages[] = {R.drawable.ic_food_apple, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp, R.drawable.ic_fridge_black_24dp};
    String currfoodcount[] = {"1" ,"2", "1 Gal", "2 Gal", "1 Loaf", "2 Slices", "3 Pounds", "2"};
    ListView listView;

    private FragmentFridgeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FridgeViewModel fridgeViewModel =
                new ViewModelProvider(this).get(FridgeViewModel.class);

        binding = FragmentFridgeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = (ListView) root.findViewById(R.id.fridge_list);
        FridgeListAdapter fridgeListAdapter = new FridgeListAdapter(getActivity(), currfoodlist, currfoodimages, currfoodcount);
        listView.setAdapter(fridgeListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast toast = Toast.makeText(getActivity(), "Item " + i + " pressed", Toast.LENGTH_SHORT );
                toast.show();
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