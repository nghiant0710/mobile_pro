package com.example.myapplication;

import android.content.Intent;
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

public class LoginFragment extends Fragment {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private DatabaseHelper databaseHelper;
    private UserTable db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.getWritableDatabase();
        db = new UserTable(databaseHelper);
        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                boolean isAuthenticated = db.checkUser(username, password);

                if (isAuthenticated) {
                    Intent intent = new Intent(getContext(), DashboardActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Invalid credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
