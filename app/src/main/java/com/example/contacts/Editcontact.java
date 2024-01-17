package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class Editcontact extends Activity {

    ImageView img;
    EditText nme, cityy, add;
    Button savebtn;
    String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcontact);

        img = findViewById(R.id.img);
        nme = findViewById(R.id.nme);
        cityy = findViewById(R.id.cityy);
        add = findViewById(R.id.add);
        savebtn = findViewById(R.id.savebtn);

        Cursor cursor = MyApp.db.rawQuery("select * from register where id='"+MyApp.getUID()+"'", null);
        if (cursor!=null) {
            if (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String name = cursor.getString(2);
                String Address = cursor.getString(3);
                String city = cursor.getString(4);
                String Email = cursor.getString(5);
                String mobile = cursor.getString(6);
                String password = cursor.getString(7);
                String birthdate = cursor.getString(8);

                nme.setText(name);
                cityy.setText(city);
                add.setText(Address);

                Glide.with(this).load(path).into(img);
            }
        }

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Editcontact.this, ShowActivity.class);
                startActivity(intent);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(Editcontact.this)
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

            Glide.with(this).load(uri).into(img);
        } else {
            Toast.makeText(this, "try again", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
