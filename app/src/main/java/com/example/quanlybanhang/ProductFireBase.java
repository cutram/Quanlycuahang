package com.example.quanlybanhang;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class ProductFireBase {
    private String id,name,price,mota,avatar;
    public ProductFireBase() {
    }

    public ProductFireBase(String name, String price, String mota, String avatar) {
        this.name = name;
        this.price = price;
        this.mota = mota;
        this.avatar = avatar;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
