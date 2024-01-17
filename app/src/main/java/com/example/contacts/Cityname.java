package com.example.contacts;

import android.app.Activity;

public class Cityname extends Activity {

    String name;

    public Cityname(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   }
