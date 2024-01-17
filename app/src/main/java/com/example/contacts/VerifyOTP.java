package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    EditText input_num;
    Button otpbtn;
    ProgressBar pbotp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyotp);

        input_num = findViewById(R.id.input_num);
        otpbtn = findViewById(R.id.otpbtn);
        pbotp = findViewById(R.id.pbotp);

        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!input_num.getText().toString().trim().isEmpty()) {
                    if ((input_num.getText().toString().trim()).length() == 10) {

                        pbotp.setVisibility(View.VISIBLE);

                        otpbtn.setVisibility(View.INVISIBLE);


                        PhoneAuthProvider.getInstance().verifyPhoneNumber
                                ("+91" + input_num.getText().toString(), 60, TimeUnit.SECONDS
                                        , VerifyOTP.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                                pbotp.setVisibility(View.GONE);

                                                otpbtn.setVisibility(View.VISIBLE);

                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                                pbotp.setVisibility(View.GONE);

                                                otpbtn.setVisibility(View.VISIBLE);

                                                Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                pbotp.setVisibility(View.GONE);

                                                otpbtn.setVisibility(View.VISIBLE);

                                                Intent intent = new Intent(VerifyOTP.this, EnterOTP.class);
                                                intent.putExtra("mobile", input_num.getText().toString());
                                                intent.putExtra("s", s);
                                                startActivity(intent);

                                            }
                                        });

                    } else {
                        Toast.makeText(VerifyOTP.this, "Please enter correct mobile number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(VerifyOTP.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
