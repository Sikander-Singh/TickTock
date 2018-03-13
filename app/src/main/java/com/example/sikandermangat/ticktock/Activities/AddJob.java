package com.example.sikandermangat.ticktock.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.Models.JobClass;
import com.example.sikandermangat.ticktock.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddJob extends AppCompatActivity {

    private EditText jobTitle;
    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        jobTitle = findViewById(R.id.jobTitle);
        firebaseObject = new FirebaseDataBaseReferences();
        myRef = firebaseObject.getMyRef();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.save, menu);

        getSupportActionBar().setTitle("Add Job");

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.navigation_save) {

            String jobStr=jobTitle.getText().toString();

            JobClass jobObject=new JobClass();

            jobObject.setJobName(jobStr);
            jobObject.setUserId(firebaseObject.getFirebaseUserid());

            String key = myRef.child("Jobs").push().getKey();

            jobObject.setJobId(key);

            myRef.child("Jobs").child(key).setValue(jobObject);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}


