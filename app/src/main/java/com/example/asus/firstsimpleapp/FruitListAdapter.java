package com.example.asus.firstsimpleapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 11/16/2017.
 */

public class FruitListAdapter extends ArrayAdapter{
    List list = new ArrayList();

    public FruitListAdapter(Context context, int resource){
        super (context, resource);
    }
    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.fruit_row_layout,parent,false);
        TextView Name =  row.findViewById(R.id.textView8);
        TextView Desc = row.findViewById(R.id.textView9);
        TextView id = row.findViewById(R.id.textView10);
        ImageView Image = row.findViewById(R.id.imageView2);

        Fruits fruits = (Fruits)this.getItem(position);
        Name.setText(fruits.getName());
        Desc.setText(fruits.getDesc());
        id.setText(fruits.getId());
        Image.setImageBitmap(getImage(fruits.getImage()));
        return row;

    }
    public static Bitmap getImage (byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
