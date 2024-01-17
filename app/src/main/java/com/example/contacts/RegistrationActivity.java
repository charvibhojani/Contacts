package com.example.contacts;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity implements Spinneradapter.Myclick {

    Button Registration;
    TextInputEditText etname, etAddress, etmobile, etEmail, etpassword, etcpassword, dob;
    String name, Address, mobile, Email, password, cpassword, city = "", birthdate;
    RadioButton radio1, radio2;
    Spinner sp;
    CheckBox cb;
    int y, m, d;
    ImageView profile_image;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    String gender = "";
    String path = "";

    ArrayList<Cityname> citynames = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Registration = findViewById(R.id.Registration);
        etname = findViewById(R.id.etname);
        etAddress = findViewById(R.id.etAddress);
        etmobile = findViewById(R.id.etmobile);
        etEmail = findViewById(R.id.etmail);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        sp = findViewById(R.id.sp);
        etpassword = findViewById(R.id.etpassword);
        etcpassword = findViewById(R.id.etcpassword);
        cb = findViewById(R.id.cb);
        profile_image = findViewById(R.id.profile_image);
        dob = findViewById(R.id.dob);

        cb.setChecked(true);

        citynames.add(new Cityname("Select city"));
        citynames.add(new Cityname("Surat"));
        citynames.add(new Cityname("Junagadh"));
        citynames.add(new Cityname("Anand"));
        citynames.add(new Cityname("Ahmedabad"));

        Spinneradapter spinneradapter = new Spinneradapter(citynames, RegistrationActivity.this, RegistrationActivity.this);
        sp.setAdapter(spinneradapter);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(RegistrationActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = citynames.get(i).getName();

                if (city.equalsIgnoreCase("Select city")) {
                    return;
                }
                Toast.makeText(RegistrationActivity.this, "" + city, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                y = calendar.get(Calendar.YEAR);
                m = calendar.get(Calendar.MONTH);
                d = calendar.get(Calendar.DAY_OF_MONTH);

                Log.d("TAG", "onClick: " + d + "/" + (m + 1) + "/" + y);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                        Log.d("TAG", "onClick: " + d + "/" + (m + 1) + "/" + (y - 1));

                        dob.setText(d + "/" + (m + 1) + "/" + y);
                    }
                }, y, m, d);

                datePickerDialog.show();
            }
        });

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etname.getText().toString().trim();
                Address = etAddress.getText().toString().trim();
                mobile = etmobile.getText().toString().trim();
                Email = etEmail.getText().toString().trim();
                password = etpassword.getText().toString().trim();
                cpassword = etcpassword.getText().toString().trim();
                birthdate = dob.getText().toString().trim();

//                firebaseAuth.createUserWithEmailAndPassword(Email, password)
//                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                            @Override
//                            public void onSuccess(AuthResult authResult) {
//
//
//                                firebaseFirestore.collection("User").document(FirebaseAuth.getInstance().getUid())
//                                        .set(new UserModel(name, mobile, Email));
//
//                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });
//            }
//        });
//    }

    //
//
                RegisterModel registerModel = new RegisterModel(name, Address, mobile, Email, password, cpassword, birthdate);

                db = FirebaseDatabase.getInstance();

                reference = db.getReference("RegisterModel");

                reference.child(name).setValue(registerModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

                if (radio1.isChecked()) {
                    gender = "Female";
                } else {
                    gender = "Male";
                }

                if (profile_image.equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please select your profile photo", Toast.LENGTH_SHORT).show();
                } else if (name.equals("")) {
                    etname.setError("Please write your name");
                    Toast.makeText(RegistrationActivity.this, "Please write your name", Toast.LENGTH_SHORT).show();
                } else if (Address.equals("")) {
                    etname.setError("Please write your address");
                    Toast.makeText(RegistrationActivity.this, "Please write your address", Toast.LENGTH_SHORT).show();
                } else if (mobile.equals("")) {
                    etname.setError("Please write your number");
                    Toast.makeText(RegistrationActivity.this, "Please write your number ", Toast.LENGTH_SHORT).show();
                } else if (birthdate.equals("")) {
                    etname.setError("Please select your date of birth");
                    Toast.makeText(RegistrationActivity.this, "Please select your date of birth", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    etname.setError("Please write your password");
                    Toast.makeText(RegistrationActivity.this, "Please write your password", Toast.LENGTH_SHORT).show();
                } else if (cpassword.equals("")) {
                    etname.setError("Confirm your password");
                    Toast.makeText(RegistrationActivity.this, "Confirm your password", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(RegistrationActivity.this, "Registered your account successfully", Toast.LENGTH_SHORT).show();

                    Log.d("my12", "onClick: " + profile_image);
                    Log.d("my12", "onClick: " + name);
                    Log.d("my12", "onClick: " + Address);
                    Log.d("my12", "onClick: " + city);
                    Log.d("my12", "onClick: " + Email);
                    Log.d("my12", "onClick: " + mobile);
                    Log.d("my12", "onClick: " + password);
                    Log.d("my12", "onClick: " + cpassword);
                    Log.d("my12", "onClick: " + birthdate);

                    Cursor cursor = MyApp.db.rawQuery("select * from register where email='" + Email + "'", null);

                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            Toast.makeText(RegistrationActivity.this, "Your EMail already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            MyApp.db.execSQL("insert into register(profile_image, name, Address, city, Email, mobile, password, birthdate) values ('" + path + "','" + name + "','" + Address + "','" + city + "','" + Email + "','" + mobile + "', '" + password + "', '" + birthdate + "' )");

                            Toast.makeText(RegistrationActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Registration.setVisibility(View.VISIBLE);
                } else {
                    Registration.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();

            path = uri.getPath();

            Glide.with(this).load(uri).into(profile_image);
        } else {
            Toast.makeText(this, "try again", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getpos(int pos) {

    }
}
//}
