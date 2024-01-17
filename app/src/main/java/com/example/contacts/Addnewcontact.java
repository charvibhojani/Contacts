package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;

public class Addnewcontact extends Activity {

    Button Done, back;
    TextInputEditText etname, etmobile, etaddress;
    String a, name, mobile, address;
    ImageView profile_image;
    String path = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewcontact);

        profile_image = findViewById(R.id.profile_image);
        etname = findViewById(R.id.etname);
        etmobile = findViewById(R.id.etmobile);
        etaddress = findViewById(R.id.etaddress);
        Done = findViewById(R.id.Done);
//        back = findViewById(R.id.back);

        Glide.with(this).load(path).into(profile_image);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etname.getText().toString();
                mobile = etmobile.getText().toString();
                address = etaddress.getText().toString();

                Log.d("my12", "onClick: " + path);
                Log.d("my12", "onClick: " + name);
                Log.d("my12", "onClick: " + mobile);
                Log.d("my12", "onClick: " + address);

                Log.d("my12", "onClick: " + MyApp.getUID());

                if (name.equals("")) {
                    etname.setError("Please write your Name");
                    Toast.makeText(Addnewcontact.this, "Please write your Name", Toast.LENGTH_SHORT).show();

                } else if (mobile.equals("")) {
                    etmobile.setError("Please write your Number");
                    Toast.makeText(Addnewcontact.this, "Please write your Number", Toast.LENGTH_SHORT).show();

                } else {
                    Cursor cursor = MyApp.db.rawQuery("select * from con where number='" + mobile + "' and uid = '" + MyApp.getUID() + "'", null);

                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            Toast.makeText(Addnewcontact.this, "Your Number already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            MyApp.db.execSQL("insert into con(path, editname, number, address, uid) values ('" + path + "','" + name + "','" + mobile + "','" + address + "','" + MyApp.getUID() + "')");

                            Toast.makeText(Addnewcontact.this, "Done", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Addnewcontact.this, ShowActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                }
            }

        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.with(Addnewcontact.this)
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

            Glide.with(this).load(uri).into(profile_image);
        } else {
            Toast.makeText(this, "try again", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}

//        Done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String a = etname.getText().toString();
//
//                name=etname.getText().toString();
//                mobile=etmobile.getText().toString();
//                address=etaddress.getText().toString();
//
//                if (name.equals("")){
//                    Toast.makeText(Addnewcontact.this, "Please enter your name", Toast.LENGTH_SHORT).show();
//                }else if (mobile.equals("")){
//                    Toast.makeText(Addnewcontact.this, "Please enter your number", Toast.LENGTH_SHORT).show();
//                }else if (address.equals("")){
//                    Toast.makeText(Addnewcontact.this, "Please enter your address", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(Addnewcontact.this, "Done", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

//        });

//}
