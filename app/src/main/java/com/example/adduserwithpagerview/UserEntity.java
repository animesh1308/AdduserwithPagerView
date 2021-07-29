package com.example.adduserwithpagerview;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.material.tabs.TabItem;

import java.io.Serializable;

@Entity(tableName = "user_detail")
public class UserEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_name")
    private String user_name;

    @ColumnInfo(name = "user_mobileno")
    private String user_mobileno;

    @ColumnInfo(name = "user_dob")
    private String user_dob;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobileno() {
        return user_mobileno;
    }

    public void setUser_mobileno(String user_mobileno) {
        this.user_mobileno = user_mobileno;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "image",typeAffinity = ColumnInfo.BLOB)
    private byte[] image;
}
