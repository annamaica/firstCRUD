package com.example.asus.firstsimpleapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
    String arg = "False";
    String arg2 = "False";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register, container, false);
        email = view.findViewById(R.id.editText);
        register = view.findViewById(R.id.button);
        final TextInputLayout til = view.findViewById(R.id.text_input_layout);
        final TextInputLayout til2 = view.findViewById(R.id.text_input_layout4);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String emaill = email.getText().toString();
                if (!emaill.equals("")) {
                    boolean valid = emailValidator(emaill);
                    if(valid){
                        dbHelper = new DatabaseHelper(getContext());
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        cursor = dbHelper.duplicateemail(emaill, sqLiteDatabase);
                        if(cursor.moveToFirst()){
                            register.setEnabled(false);
                            til.setError("Email Address is already taken");
                        }
                        else{
                            register.setEnabled(true);
                            til.setError(null);
                        }
                    }
                    else{
                        til.setError("Email Address is invalid");
                    }
                }
            }
        });
        username = view.findViewById(R.id.editText2);
        password = view.findViewById(R.id.editText3);
        cpassword = view.findViewById(R.id.editText4);
        cpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String passwordd = password.getText().toString();
                final String cpasswordd = cpassword.getText().toString();
                if(!passwordd.equals(cpasswordd)){
                    til2.setError("Password Doesn't Match");
                }
                else{
                    til2.setError(null);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill = email.getText().toString();
                String usernamee = username.getText().toString();
                String passwordd = password.getText().toString();
                String cpasswordd = cpassword.getText().toString();
                if(!passwordd.equals(cpasswordd)){
                    til2.setError("Password Doesn't Match");
                }
                else{
                    dbHelper = new DatabaseHelper(getContext());
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    dbHelper.AddUser(emaill, usernamee, passwordd, sqLiteDatabase);
                    Toast.makeText(getActivity(), "Registration Complete", Toast.LENGTH_SHORT).show();
                    email.setText("");
                    username.setText("");
                    password.setText("");
                    cpassword.setText("");
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
