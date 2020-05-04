package com.example.freshtext.Entity;

import android.app.Application;

import java.io.Serializable;

public class ShoppingCart extends Application implements Serializable {
    private int id;
    private String name;
    private boolean enabled;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
