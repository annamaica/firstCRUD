package com.example.asus.firstsimpleapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class FoodDashboard extends AppCompatActivity {

    Button addfood;
    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper dbHelper;
    Cursor cursor;
    FruitListAdapter fruitListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_dashboard);
        addfood = (Button) findViewById(R.id.button3);
        addfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (FoodDashboard.this, AddFood.class);
                startActivity(intent);
            }
        });
        listView = (ListView) findViewById(R.id.foodlist);
        fruitListAdapter = new FruitListAdapter(getApplicationContext(), R.layout.fruit_row_layout);
        listView.setAdapter(fruitListAdapter);
        dbHelper = new DatabaseHelper(this);
        sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = dbHelper.getInformation(sqLiteDatabase);


        if(cursor.moveToFirst()){
            do {
                String name, desc;
                byte [] image;
                name = cursor.getString(1);
                image= cursor.getBlob(2);
                desc = cursor.getString(3);

                Fruits fruits = new Fruits(name,image,desc);
                fruitListAdapter.add(fruits);
            }
            while (cursor.moveToNext());
        }

    }
}
