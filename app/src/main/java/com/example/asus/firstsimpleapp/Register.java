package com.example.asus.firstsimpleapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Asus on 11/16/2017.
 */

public class Register extends Fragment {
    DatabaseHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText username, password, cpassword;
    TextInputEditText email;
    Cursor cursor;
    Button register;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register, container, false);
        email = view.findViewById(R.id.editText);
        register = view.findViewById(R.id.button);
        final TextInputLayout til = view.findViewById(R.id.text_input_layout);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String emaill = email.getText().toString();
                if (!emaill.equals("")) {
                    boolean valid = emailValidator(emaill);
                    if(valid){
                        register.setEnabled(true);
                        dbHelper = new DatabaseHelper(getContext());
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        cursor = dbHelper.duplicateemail(emaill, sqLiteDatabase);
                        if(cursor.moveToFirst()){

                            til.setError("Email Address is already taken");
                            register.setEnabled(false);
                        }
                        else{
                            register.setEnabled(true);
                            til.setError(null);
                        }
                    }
                    else{
                        register.setEnabled(false);
                        til.setError("Email Address is invalid");
                    }
                }
            }
        });
        username = view.findViewById(R.id.editText2);
        password = view.findViewById(R.id.editText3);
        cpassword = view.findViewById(R.id.editText4);
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
                    email.setText(" ");
                    username.setText(" ");
                    password.setText(" ");
                    cpassword.setText(" ");

                }
            }
        });
        return view;
    }
    public boolean emailValidator (String email){
        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
