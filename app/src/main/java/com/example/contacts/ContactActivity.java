package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity implements ContactAdapter.Myclick {

    TextView etname;
    Button btn;
    String name;
    FloatingActionButton addnewaccount;

    ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewcontact);

        etname = findViewById(R.id.etname);


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart");
    }

    @Override
    public void getpos(int pos, View view) {

        Toast.makeText(ContactActivity.this, (CharSequence) addnewaccount, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ContactActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();

    }
}
