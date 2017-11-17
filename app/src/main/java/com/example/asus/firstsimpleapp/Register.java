package com.example.asus.firstsimpleapp;

import android.content.Context;
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

public class Register extends Fragment {
    DatabaseHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText email, username, password, cpassword;
    Button register;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register, container, false);
        email = view.findViewById(R.id.editText);
        username = view.findViewById(R.id.editText2);
        password = view.findViewById(R.id.editText3);
        cpassword = view.findViewById(R.id.editText4);
        register = view.findViewById(R.id.button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill = email.getText().toString();
                String usernamee = username.getText().toString();
                String passwordd = password.getText().toString();
                String cpasswordd = cpassword.getText().toString();
                if (!passwordd.equals(cpasswordd)) {
                    Toast.makeText(getActivity(), "Password doesn't Match", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbHelper = new DatabaseHelper(getContext());
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    dbHelper.AddUser(emaill, usernamee, cpasswordd, sqLiteDatabase);

                    Toast.makeText(getActivity(), "Registration Complete", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
