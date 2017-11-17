package com.example.asus.firstsimpleapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddFood extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Button browse, save;
    ImageView imgView;
    EditText edName, edDesc;
    private static final int RESULT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        browse = (Button) findViewById(R.id.button4);
        edName = (EditText) findViewById(R.id.editText5);
        edDesc = (EditText) findViewById(R.id.editText6);
        imgView = (ImageView) findViewById(R.id.imageView);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(AddFood.this,
                        new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        RESULT_IMAGE);
            }
        });
        save = (Button) findViewById(R.id.button5);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFruit();
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == RESULT_IMAGE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent, RESULT_IMAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_IMAGE && resultCode == RESULT_OK && data!=null){
            Uri selectedimage = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(selectedimage);
                Bitmap yourselectedimage = BitmapFactory.decodeStream(inputStream);
                imgView.setImageBitmap(yourselectedimage);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void addFruit (){
        String namee = edName.getText().toString();
        String descc = edDesc.getText().toString();
        byte [] data = getimagebyte(imgView);

        dbHelper = new DatabaseHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.AddFruit(namee,data,descc,sqLiteDatabase);
        Toast.makeText(getApplicationContext(), "Fruit Information Saved", Toast.LENGTH_SHORT).show();

    }
    public static byte[] getimagebyte (ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte [] bytearray = stream.toByteArray();
        return bytearray;
    }
}
