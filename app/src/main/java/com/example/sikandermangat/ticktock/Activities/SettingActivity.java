package com.example.sikandermangat.ticktock.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    private Button signOut;
    private FirebaseDataBaseReferences firebaseObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        firebaseObject=new FirebaseDataBaseReferences();
        getSupportActionBar().setTitle("Settings");
        signOut=(Button)findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                firebaseObject.getAuth().signOut();

                Intent screen=new Intent(SettingActivity.this,MainActivity.class);


                startActivity(screen);


            }
        });


    }

}
