package com.example.asus.firstsimpleapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodDashboard extends AppCompatActivity {

    Button addfood;
    GridView listView;
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
        listView = (GridView) findViewById(R.id.fruitlist);
        fruitListAdapter = new FruitListAdapter(getApplicationContext(), R.layout.fruit_row_layout);
        listView.setAdapter(fruitListAdapter);
        dbHelper = new DatabaseHelper(this);
        sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = dbHelper.getInformation(sqLiteDatabase);


        if(cursor.moveToFirst()){
            do {
                String id, name, desc;
                byte [] image;
                id = cursor.getString(0);
                name = cursor.getString(1);
                image= cursor.getBlob(2);
                desc = cursor.getString(3);

                Fruits fruits = new Fruits(id,name,image,desc);
                fruitListAdapter.add(fruits);
            }
            while (cursor.moveToNext());
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv1 = view.findViewById(R.id.textView10);
                String fruitid = tv1.getText().toString();
                Intent intent = new Intent(FoodDashboard.this, Composer.class);
                Bundle bundle = new Bundle();
                bundle.putString("fruitID", fruitid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
