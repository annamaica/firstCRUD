package com.example.asus.firstsimpleapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Composer extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    Button edit, delete;
    String fruitid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composer);
        dbHelper = new DatabaseHelper(this);
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Bundle bundle = getIntent().getExtras();
        final String fruitID = bundle.getString("fruitID");
        cursor = dbHelper.searchData(fruitID, sqLiteDatabase);
        if(cursor.moveToFirst()){
            do{
                String name, desc;
                byte [] image;
                fruitid = cursor.getString(0);
                name = cursor.getString(1);
                image = cursor.getBlob(2);
                desc = cursor.getString(3);

                TextView txtName = (TextView) findViewById(R.id.textView11);
                TextView txtDesc = (TextView) findViewById(R.id.textView12);
                txtName.setText(name);
                txtDesc.setText(desc);
                ImageView imageView = (ImageView) findViewById(R.id.imageView3);
                imageView.setImageBitmap(getImage(image));
            }
            while (cursor.moveToNext());
        }
        edit = (Button) findViewById(R.id.button6);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Composer.this, AddFood.class);
                Bundle bundle = new Bundle();
                bundle.putString("fruitID", fruitid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        delete = (Button) findViewById(R.id.button7);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Composer.this);
                alert.setTitle("Delete fruit");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dbHelper.deleteFruit(fruitid,sqLiteDatabase);
                        Toast.makeText(getApplicationContext(),"Fruit Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Composer.this, FoodDashboard.class);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();

            }
        });
    }
    public static Bitmap getImage (byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
