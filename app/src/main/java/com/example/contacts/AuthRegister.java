package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRegister extends AppCompatActivity {

    EditText name, number, mail, pass;
    String user_name, user_num, user_mail, user_pass;
    Button register;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_registeration);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_name = name.getText().toString().trim();
                user_num = number.getText().toString().trim();
                user_mail = mail.getText().toString().trim();
                user_pass = pass.getText().toString().trim();

                if (user_name.equals("")) {
                    name.setError("Please write your name");
                } else if (user_num.equals("")) {
                    number.setError("Please write your number");
                } else if (user_mail.equals("")) {
                    mail.setError("Please write your number");
                } else if (user_pass.equals("")) {
                    pass.setError("Please write your number");
                } else {
                    startActivity(new Intent(AuthRegister.this, AuthLogin.class));

                }

                firebaseAuth.createUserWithEmailAndPassword(user_mail, user_pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {


                                firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid())
                                        .set(new UserModel(user_name, user_num, user_mail, user_pass));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(AuthRegister.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

    }
}
