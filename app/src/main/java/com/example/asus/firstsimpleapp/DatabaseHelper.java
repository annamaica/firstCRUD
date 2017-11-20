package com.example.asus.firstsimpleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Asus on 11/16/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "usertable";
    public static final String COL_1 = "user_email";
    public static final String COL_2 = "user_uname";
    public static final String COL_3 = "user_password";

    private static final String DATABASE_NAME = "Simpleapp.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("Database Operations", "Database Created");

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE usertable (userID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, user_email VARCHAR(100) NOT NULL, user_uname VARCHAR(100) NOT NULL, user_password VARCHAR(100) NOT NULL)");
        Log.e("Table Operations",  "User Table Created");
        sqLiteDatabase.execSQL("CREATE TABLE fruittable (foodID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fruitName VARCHAR(100), fruitImg BLOB, fruitDesc VARCHAR(100))");
        Log.e("Table Operations", "Fruit Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS fruittable");
        onCreate(sqLiteDatabase);
        Log.e("Table Operations",  "Table Dropped");
    }
    public void AddUser(String email, String username, String pass, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, email);
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, pass);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Log.e("TABLE OPERATIONS","One user is inserted");

    }
    public void AddFruit(String fruitname, byte[]image, String fruitDesc, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put("fruitName", fruitname);
        contentValues.put("fruitImg", image);
        contentValues.put("fruitDesc", fruitDesc);
        sqLiteDatabase.insert("fruittable", null, contentValues);
        Log.e("TABLE OPERATIONS", "One fruit is inserted");
    }
    public int saveFruit(String fruitId, String fruitname, byte[]image, String fruitDesc, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put("fruitName", fruitname);
        contentValues.put("fruitImg", image);
        contentValues.put("fruitDesc", fruitDesc);
        String selection = "foodID LIKE ?";
        String[] selection_args = {fruitId};
        int count = sqLiteDatabase.update("fruittable",contentValues,selection,selection_args);
        return count;
    }
    public void deleteFruit(String fruitID, SQLiteDatabase sqLiteDatabase){
        String selection = "foodID LIKE ?";
        String[] selection_args = {fruitID};
        sqLiteDatabase.delete("fruittable",selection,selection_args);
    }
    public Cursor loginData(String username, String password, SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM usertable WHERE user_uname = '"+username+"' AND user_password = '"+password+"'", null);
        return cursor;

    }
    public Cursor searchData(String id, SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM fruittable WHERE foodID = '"+id+"'", null);
        return cursor;

    }
    public Cursor getInformation(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM fruittable", null);
        return cursor;
    }
}
