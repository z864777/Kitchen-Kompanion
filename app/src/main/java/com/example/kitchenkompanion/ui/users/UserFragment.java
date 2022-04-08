package com.example.kitchenkompanion.ui.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kitchenkompanion.MainActivity;
import com.example.kitchenkompanion.R;

import com.example.kitchenkompanion.databinding.FragmentUsersBinding;
import com.google.android.material.button.MaterialButton;

public class UserFragment extends Fragment {

    private FragmentUsersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserViewModel UserViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);

        binding = FragmentUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        EditText username = root.findViewById(R.id.username);
        EditText password = root.findViewById(R.id.password);

        MaterialButton loginbtn = root.findViewById(R.id.loginbtn);

        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equalsIgnoreCase("Charlie") && password.getText().toString().equals("user1")){
                    //correct
                    Toast.makeText(getActivity(),"LOGIN SUCCESSFUL, WELCOME CHARLIE",Toast.LENGTH_SHORT).show();
                    MainActivity.curr_user = "Charlie";
                }else if(username.getText().toString().equalsIgnoreCase("Max") && password.getText().toString().equals("user2")){
                    //correct
                    Toast.makeText(getActivity(),"LOGIN SUCCESSFUL, WELCOME MAX",Toast.LENGTH_SHORT).show();
                    MainActivity.curr_user = "Max";
                }else if(username.getText().toString().equalsIgnoreCase("Zach") && password.getText().toString().equals("user3")){
                    //correct
                    Toast.makeText(getActivity(),"LOGIN SUCCESSFUL, WELCOME ZACH",Toast.LENGTH_SHORT).show();
                    MainActivity.curr_user = "Zach";
                }else if(username.getText().toString().equalsIgnoreCase("Matarr") && password.getText().toString().equals("user4")){
                    //correct
                    Toast.makeText(getActivity(),"LOGIN SUCCESSFUL, WELCOME MATARR",Toast.LENGTH_SHORT).show();
                    MainActivity.curr_user = "Matarr";
                }else
                    //incorrect
                    Toast.makeText(getActivity(),"LOGIN FAILED. INCORRECT USERNAME OR PASSWORD",Toast.LENGTH_SHORT).show();
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
