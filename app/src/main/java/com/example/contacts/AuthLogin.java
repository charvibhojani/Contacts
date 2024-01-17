package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthLogin extends AppCompatActivity {

    EditText mail, pass;
    Button login, register, otp;
    TextView forgot_pass, or;
    String user_mail, user_pass;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    SignInButton google;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgot_pass = findViewById(R.id.forgot_pass);
        google = findViewById(R.id.google);
        or = findViewById(R.id.or);
        otp = findViewById(R.id.otp);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AuthLogin.this, AuthRegister.class));

            }
        });

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthLogin.this, VerifyOTP.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_mail = mail.getText().toString().trim();
                user_pass = pass.getText().toString().trim();

                if (user_mail.equals("")) {
                    mail.setError("Please write your number");
                } else if (user_pass.equals("")) {
                    pass.setError("Please write your number");
                } else {

                    startActivity(new Intent(AuthLogin.this, HomeActivity.class));

                }

                firebaseAuth.signInWithEmailAndPassword(user_mail, user_pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

//                                Toast.makeText(AuthLogin.this, "Successfully LogIn", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AuthLogin.this, HomeActivity.class));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(AuthLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_mail = mail.getText().toString().trim();

                firebaseAuth.sendPasswordResetEmail(user_mail)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(AuthLogin.this, "Email Sent", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(AuthLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }

    private void SignIn() {

        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                home();
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void home() {
        finish();

        Intent intent = new Intent(AuthLogin.this, HomeActivity.class);
        startActivity(intent);
    }
}
