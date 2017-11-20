package com.example.asus.firstsimpleapp;

/**
 * Created by Asus on 11/16/2017.
 */

public class Fruits {
    String name;
    byte[] image;
    String desc;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Fruits (String id, String name, byte[]image, String desc){
        this.id = id;
        this.name = name;
        this.image = image;
        this.desc = desc;

    }
}
