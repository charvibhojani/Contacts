package com.example.contacts;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class MyApp extends Application {

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    static SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("my", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        db = openOrCreateDatabase("my.db", MODE_PRIVATE, null);

        db.execSQL("create table if not exists register(id integer primary key autoincrement, profile_image text, name text, Address text, city text, Email text, mobile integer, password text, birthdate integer)");
        db.execSQL("create table if not exists con(id integer primary key autoincrement, path text, editname text, number text, address text, uid text)");
        db.execSQL("create table if not exists fav(id integer primary key autoincrement, cid text, uid text)");

    }

    public static void setStatus(boolean status) {
        editor.putBoolean("status", status).commit();
    }

    public static boolean getStatus() {
        return sharedPreferences.getBoolean("status", false);
    }

    public static void setEtEmail(String id) {
        editor.putString("id", id).commit();
    }

    public static String getEtEmail() {
        return sharedPreferences.getString("id", "");
    }

    public static void setPass(String Pass) {
        editor.putString("pass", Pass).commit();
    }

    public static String getPass() {
        return sharedPreferences.getString("pass", "");
    }

    public static void setUID(String UID) {
        editor.putString("UID", UID).commit();
    }

    public static String getUID() {
        return sharedPreferences.getString("UID", "");
    }

    public static void setCID(String CID) {
        editor.putString("CID", CID).commit();
    }

    public static String getCID() {
        return sharedPreferences.getString("CID", "");
    }
}
