package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.UserTable;
import com.example.myapplication.Model.User;

public class RegisterFragment extends Fragment {

    private EditText edtUsername, edtPassword, edtAddress;
    private Button btnRegister;
    private DatabaseHelper databaseHelper;
    private UserTable db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.getWritableDatabase();
        db = new UserTable(databaseHelper);
        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtAddress = view.findViewById(R.id.edtAddress);
        btnRegister = view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();

                User newUser = new User(username, password, address);

                boolean isRegistered = db.registerUser(newUser);

                if (isRegistered) {
                    Toast.makeText(getContext(), "Registered successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
