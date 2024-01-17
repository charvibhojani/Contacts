package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Changepassword extends Activity {

    TextView Done;
    EditText etcurrentpassword, etnewpassword, etconfirmpassword;
    String currentpassword, newpassword, confirmpassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);

        Done = findViewById(R.id.Done);
        etcurrentpassword = findViewById(R.id.etcurrentpassword);
        etnewpassword = findViewById(R.id.etnewpassword);
        etconfirmpassword = findViewById(R.id.etconfirmpassword);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentpassword = etcurrentpassword.getText().toString();
                newpassword = etnewpassword.getText().toString();
                confirmpassword = etconfirmpassword.getText().toString();

                Log.d("TAG", "onClick: " + currentpassword);
                Log.d("TAG", "onClick: " + newpassword);
                Log.d("TAG", "onClick: " + confirmpassword);

                if (currentpassword.equals("")) {
                    etcurrentpassword.setError("Please write your current password");
                    Toast.makeText(Changepassword.this, "Please write your current password", Toast.LENGTH_SHORT).show();

                } else if (newpassword.equals("")) {
                    etnewpassword.setError("Please write your new password");
                    Toast.makeText(Changepassword.this, "Please write your new password", Toast.LENGTH_SHORT).show();

                } else if (confirmpassword.equals("")) {
                    etconfirmpassword.setError("Please confirm your new password");
                    Toast.makeText(Changepassword.this, "Please confirm your new password", Toast.LENGTH_SHORT).show();

                } else {

                    Cursor cursor = MyApp.db.rawQuery("select * from register where password='" + currentpassword + "' and id='" + MyApp.getUID() + "'", null);
                    if (cursor != null) {

                        if (cursor.moveToNext()) {

                            MyApp.db.execSQL("update register set password='" + newpassword + "'where id='" + MyApp.getUID() + "'");
                            MyApp.setUID("");
                            Intent intent = new Intent(Changepassword.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(Changepassword.this, "Successfully changed your password", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Changepassword.this, "Your old password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }

//                currentpassword=etcurrentpassword.getText().toString();
//                newpassword=etnewpassword.getText().toString();
//                confirmpassword=etconfirmpassword.getText().toString();

//                MyApp.db.execSQL("update con set path='"+path+"',editname='"+name+"',number='"+mobile+"' where id='"+MyApp.getCID()+"'");

                }
            }
        });
    }
}
