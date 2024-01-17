package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class UpdateActivity extends AppCompatActivity {

    TextView save;
    ImageView profile;
    TextInputEditText etname, etmobile;
    String name, mobile, path;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        save = findViewById(R.id.save);
        etname = findViewById(R.id.etname);
        etmobile = findViewById(R.id.etmobile);
        profile = findViewById(R.id.profile);

        dataload();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etname.getText().toString();
                mobile = etmobile.getText().toString();

                UpdateMode updateMode = new UpdateMode(name, mobile);

                db = FirebaseDatabase.getInstance();

                reference = db.getReference("UpdateMode");

                reference.child(name).setValue(updateMode).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });


//                Log.d("TAG", "onClick: " + name);
//                Log.d("TAG", "onClick: " + mobile);

//                if (name.equals("")) {
//                    etname.setError("Please fill your name");
//                    Toast.makeText(UpdateActivity.this, "Please write your name", Toast.LENGTH_SHORT).show();
//                } else if (mobile.equals("")) {
//                    etmobile.setError("Please fill your number");
//                    Toast.makeText(UpdateActivity.this, "Please write your number", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(UpdateActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();

                MyApp.db.execSQL("update con set path='" + path + "',editname='" + name + "',number='" + mobile + "' where id='" + MyApp.getCID() + "'");
                finish();
            }
//            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(UpdateActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();

            path = uri.getPath();

            Glide.with(this).load(uri).into(profile);
        } else {
            Toast.makeText(this, "try again", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dataload();
    }

    public void dataload() {

        Cursor cursor = MyApp.db.rawQuery("select * from con where id='" + MyApp.getCID() + "'", null);
        if (cursor != null) {

            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                path = cursor.getString(1);
                String name = cursor.getString(2);
                String number = cursor.getString(3);
                String uid = cursor.getString(5);

                etname.setText(name);
                etmobile.setText(number);

                Glide.with(this).load(path).into(profile);

                Log.d("TAG", "dataload: " + id + path + name + number);
            }
        }
    }


}
