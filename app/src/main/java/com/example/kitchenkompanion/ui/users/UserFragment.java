package com.example.kitchenkompanion.ui.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

    EditText username;
    EditText password;
    TextView current_user;
    ImageView current_user_image;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserViewModel UserViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);

        binding = FragmentUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        current_user = root.findViewById(R.id.current_user_text_var);
        current_user_image = root.findViewById(R.id.curret_user_image_var);

        current_user.setText(MainActivity.curr_user);
        if (MainActivity.curr_user.equals("Charlie")) {
            current_user_image.setImageResource(R.drawable.private_list_owner1);
        } else if (MainActivity.curr_user.equals("Max")) {
            current_user_image.setImageResource(R.drawable.private_list_owner2);
        } else if (MainActivity.curr_user.equals("Zach")) {
            current_user_image.setImageResource(R.drawable.private_list_owner3);
        } else {
            current_user_image.setImageResource(R.drawable.private_list_owner4);
        }

        MaterialButton loginbtn = root.findViewById(R.id.loginbtn);

        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equalsIgnoreCase("Charlie") && password.getText().toString().equals("user1")){
                    //correct
                    Toast.makeText(getActivity(),"LOGIN SUCCESSFUL, WELCOME CHARLIE",Toast.LENGTH_SHORT).show();
                    MainActivity.curr_user = "Charlie";
                    username.setText("");
                    password.setText("");
                    current_user.setText(MainActivity.curr_user);
                    current_user_image.setImageResource(R.drawable.private_list_owner1);
                }else if(username.getText().toString().equalsIgnoreCase("Max") && password.getText().toString().equals("user2")){
                    //correct
                    Toast.makeText(getActivity(),"LOGIN SUCCESSFUL, WELCOME MAX",Toast.LENGTH_SHORT).show();
                    MainActivity.curr_user = "Max";
                    username.setText("");
                    password.setText("");
                    current_user.setText(MainActivity.curr_user);
                    current_user_image.setImageResource(R.drawable.private_list_owner2);
                }else if(username.getText().toString().equalsIgnoreCase("Zach") && password.getText().toString().equals("user3")){
                    //correct
                    Toast.makeText(getActivity(),"LOGIN SUCCESSFUL, WELCOME ZACH",Toast.LENGTH_SHORT).show();
                    MainActivity.curr_user = "Zach";
                    username.setText("");
                    password.setText("");
                    current_user.setText(MainActivity.curr_user);
                    current_user_image.setImageResource(R.drawable.private_list_owner3);
                }else if(username.getText().toString().equalsIgnoreCase("Matarr") && password.getText().toString().equals("user4")){
                    //correct
                    Toast.makeText(getActivity(),"LOGIN SUCCESSFUL, WELCOME MATARR",Toast.LENGTH_SHORT).show();
                    MainActivity.curr_user = "Matarr";
                    username.setText("");
                    password.setText("");
                    current_user.setText(MainActivity.curr_user);
                    current_user_image.setImageResource(R.drawable.private_list_owner4);
                }else
                    //incorrect
                    Toast.makeText(getActivity(),"LOGIN FAILED. INCORRECT USERNAME OR PASSWORD",Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
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
