package com.example.myapplication.room_database.TelevisionModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tv_database")
public class Television {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo
    String tv_name;

    @ColumnInfo
    String tv_url;

    @ColumnInfo
    String tv_img;

    @ColumnInfo
    boolean is_favourite_tv;

    @ColumnInfo
    boolean bgGledai;


    public Television(String tv_name, String tv_url, String tv_img, boolean is_favourite_tv, boolean bgGledai) {
        this.tv_name = tv_name;
        this.tv_url = tv_url;
        this.tv_img = tv_img;
        this.is_favourite_tv = is_favourite_tv;
        this.bgGledai = bgGledai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_url() {
        return tv_url;
    }

    public void setTv_url(String tv_url) {
        this.tv_url = tv_url;
    }

    public String getTv_img() {
        return tv_img;
    }

    public void setTv_img(String tv_img) {
        this.tv_img = tv_img;
    }

    public boolean isIs_favourite_tv() {
        return is_favourite_tv;
    }

    public void setIs_favourite_tv(boolean is_favourite_tv) {
        this.is_favourite_tv = is_favourite_tv;
    }


    public boolean isBgGledai() {
        return bgGledai;
    }

    public void setBgGledai(boolean bgGledai) {
        this.bgGledai = bgGledai;
    }
}
