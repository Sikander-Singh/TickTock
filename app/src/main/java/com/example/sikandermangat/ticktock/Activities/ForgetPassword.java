package com.example.sikandermangat.ticktock.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sikandermangat.ticktock.R;

public class ForgetPassword extends AppCompatActivity {


    private TextView recEmail;
    private Button recEmailbtn;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().setTitle("Forget Password");
        recEmail=findViewById(R.id.recEmail);
        recEmailbtn=findViewById(R.id.recEmailbtn);
        progress = new ProgressDialog(ForgetPassword.this);
        progress.setMessage("Sending Request.....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        recEmailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress.show();

            }
        });







    }
}
