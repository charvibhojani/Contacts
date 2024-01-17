package com.example.contacts;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextView forgotpassword;
    Button login, registration;
    TextInputEditText etemail, etpassword;
    String email, password;
    CheckBox cb;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

//       getSupportActionBar().hide();

        login = findViewById(R.id.login);
        forgotpassword = findViewById(R.id.forgotpassword);
        registration = findViewById(R.id.registration);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        cb = findViewById(R.id.cb);

        mypermission();

        cb.setChecked(MyApp.getStatus());

        if (MyApp.getStatus()) {

            etemail.setText(MyApp.getEtEmail());

            etpassword.setText(MyApp.getPass());

        }

        if (!MyApp.getUID().equals("")) {

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

            startActivity(intent);

            finish();

        } else {
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etemail.getText().toString().trim();
                password = etpassword.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                        startActivity(intent);

                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(LoginActivity.this, "Try again", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etemail.getText().toString();

                firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

//        forgotpassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                showfpdialog();

//                Cursor cursor = MyApp.db.rawQuery("select * from register" ,  null);
//
//                if (cursor != null) {
//
//                    if (cursor.moveToNext()) {
//                        String etemail = cursor.getString(7);
//                        String etpassword = cursor.getString(8);
//
//                        Log.d("tag", "onclick: " + etemail);
//                        Log.d("tag", "onclick: " + etpassword);
//                    }
//                }


//            }
//        });

//        registration.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
//
//                startActivity(intent);
//
//            }
//        });

    public void showcustomDialog() {
        Dialog dialog = new Dialog(LoginActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.setCancelable(false);
        dialog.setTitle("Forgot Password");

        TextInputEditText etmail = dialog.findViewById(R.id.etmail);
        TextView fpbtn = dialog.findViewById(R.id.fpbtn);

        fpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etmail.getText().toString().trim();

                if (email.equals("")) {
                    etmail.setError("Please write your Email");
                    Toast.makeText(LoginActivity.this, "Please write your Email", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();

    }

    public void showfpdialog() {

        Dialog dialog = new Dialog(LoginActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.setTitle("Forgot password");
        dialog.setCancelable(false);

        TextInputEditText etmail = dialog.findViewById(R.id.etmail);
        Button fpbtn = dialog.findViewById(R.id.fpbtn);

        fpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = etmail.getText().toString().trim();

                if (data.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please write Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor cursor = MyApp.db.rawQuery("select * from register where email='" + data + "'", null);

                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        String number = cursor.getString(6);
                        String password = cursor.getString(7);

                        try {
                            String msg = "Your Password " + password;
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, "", msg, null, null);

//                        Toast.makeText(LoginActivity.this, "" + number, Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "SMS send successfully", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, "Please allow permission", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                    }
                }

                dialog.dismiss();

            }
        });

        dialog.show();

    }

    public void exitdialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder1.setMessage("Are you sure you want to exit ?");

        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

                finish();
            }
        });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();

        alert11.show();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        exitdialog();
    }

    public void mypermission() {

        PermissionX.init(this)

                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE)

                .request(new RequestCallback() {

                    @Override

                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {

                    }
                });
    }
}
