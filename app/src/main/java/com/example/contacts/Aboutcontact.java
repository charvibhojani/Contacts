package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Aboutcontact extends Activity {

    ImageView iv, call, message, videocall, edit, like;
    TextView name, mobile, address, email;
    String Name, Mobile, Address, Email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutcontact);

        iv = findViewById(R.id.iv);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        edit = findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Aboutcontact.this, Editcontact.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
