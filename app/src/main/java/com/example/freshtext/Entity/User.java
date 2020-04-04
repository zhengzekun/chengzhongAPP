package com.example.freshtext.Entity;

import android.app.Application;

import java.io.Serializable;

//用户
public class User extends Application implements Serializable {
    private int id;
    private String name;
    private String password;
    private int phone;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    private User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
