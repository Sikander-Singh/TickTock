package com.example.sikandermangat.ticktock.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    private Button createAcc;
    private Button login;
    private EditText email;
    private EditText pass;
    private FirebaseAuth auth;
    private FirebaseDataBaseReferences firebaseObject;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progress;
    private TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        forget = (TextView) findViewById(R.id.forget);
        login = (Button) findViewById(R.id.login);
        createAcc = (Button) findViewById(R.id.createAcc);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Signing.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        getSupportActionBar().hide();

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ForgetPassword.class);

                startActivity(intent);


            }
        });


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if (firebaseAuth.getCurrentUser() != null) {

                    progress.dismiss();
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));


                }


            }
        };


        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent screen = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(screen);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SignIn();
                progress.show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        auth.addAuthStateListener(authStateListener);
    }

    private void SignIn() {

        String emailStr = email.getText().toString();
        String passStr = pass.getText().toString();

        if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passStr)) {

            progress.dismiss();
            Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();

        } else {

            auth.signInWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Sign in Problem", Toast.LENGTH_SHORT).show();
                    }


                    progress.dismiss();


                }
            });

        }


    }

    @Override
    public void onBackPressed() {


    }


}