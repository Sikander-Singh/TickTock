package com.example.sikandermangat.ticktock.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sikandermangat.ticktock.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateActivity extends AppCompatActivity {

   private EditText createEmail;
   private EditText newPass;
    private EditText confirmPass;
   private Button createbtn;
   private FirebaseAuth createAuth;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        createEmail=findViewById(R.id.createEmail);
        newPass=findViewById(R.id.newPass);
        confirmPass=findViewById(R.id.confirmPass);
        createbtn=(Button)findViewById(R.id.createbtn);
        createAuth=FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Create Account");
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress = new ProgressDialog(CreateActivity.this);
                progress.setMessage("Creating......");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();
               String email=createEmail.getText().toString();
               String pass=newPass.getText().toString();
               String confirm=confirmPass.getText().toString();
               if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(pass)){

                   Toast.makeText(getApplicationContext(), "Feilds are empty", Toast.LENGTH_SHORT).show();
               }
               else {


                   if(pass.equals(confirm)) {

                       createAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (!task.isSuccessful()) {

                                   progress.dismiss();
                                   final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CreateActivity.this);
                                   builder.setTitle("Message");

                                   builder.setMessage("May be email is already exits or invalid").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int id) {

                                           builder.setCancelable(false);
                                       }});
                                   builder.show();
                               } else {


                                   Toast.makeText(getApplicationContext(), "Account is Created", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(CreateActivity.this, MainActivity.class));
                                   progress.dismiss();
                               }
                           }
                       });
                   }

                   else{

                       progress.dismiss();
                       final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CreateActivity.this);
                       builder.setTitle("Message");

                       builder.setMessage("New password is not same as confirm password").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {

                               builder.setCancelable(false);
                           }});
                       builder.show();
                   }
               }

            }
        });



    }
}
