package com.example.asus.firstsimpleapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Asus on 11/16/2017.
 */

public class SignIn extends Fragment{
    DatabaseHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText username, password;
    Button signin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signin, container, false);
        username = view.findViewById(R.id.editText2);

        password = view.findViewById(R.id.editText3);
        signin = view.findViewById(R.id.button2);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DatabaseHelper(getActivity());
                sqLiteDatabase = dbHelper.getReadableDatabase();
                String usernamee = username.getText().toString();
                String passwordd = password.getText().toString();
                Cursor res = dbHelper.loginData(usernamee,passwordd,sqLiteDatabase);
                if (res.moveToFirst()){
                    Toast.makeText(getActivity(), "Welcome to Food App", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), FoodDashboard.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
