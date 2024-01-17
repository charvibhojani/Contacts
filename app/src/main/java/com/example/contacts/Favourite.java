package com.example.contacts;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

public class Favourite extends AppCompatActivity implements ShowAdapter.Myclick {

    RecyclerView rv;
    String path;

    ArrayList<ShowItem> showItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_favourite);

        rv = findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        dataload();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dataload();
    }

    public void dataload() {

        Cursor cursor = MyApp.db.rawQuery("select * from fav", null);
        if (cursor != null) {

            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String cid = cursor.getString(1);
                String uid = cursor.getString(2);

//                Glide.with(this).load(path).into(profile);

                showItems.add(mycon(cid));

//                Log.d("TAG", "dataload: " + id + path + name + number);

            }

            ShowAdapter showAdapter = new ShowAdapter(showItems, Favourite.this, Favourite.this);
            rv.setAdapter(showAdapter);
        }
    }

    public ShowItem mycon(String cid) {

        ShowItem showItem = null;

        Cursor cursor = MyApp.db.rawQuery("select * from con where id='" + cid + "'", null);
        if (cursor != null) {

            if (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String name = cursor.getString(2);
                String number = cursor.getString(3);
                String address = cursor.getString(4);
                String uid = cursor.getString(5);
                Log.d("TAG", "dataload: " + id);

                showItem = new ShowItem(id, path, name, number, address, uid);

            }

        }

        return showItem;
    }

    @Override
    public void getmypos(int pos, View view) {

    }

    @Override
    public void more(int pos, View view) {

    }
}
