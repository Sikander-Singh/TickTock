package com.example.sikandermangat.ticktock.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {

    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference myRef;
    private EditText jobName;
    private String key;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        jobName=(EditText)findViewById(R.id.editJob);

        Intent intent=getIntent();
        key=intent.getStringExtra("JobId");
        jobName.setText(intent.getStringExtra("JobName"));
        firebaseObject=new FirebaseDataBaseReferences();
        myRef=firebaseObject.getMyRef();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.delete, menu);
        getMenuInflater().inflate(R.menu.save, menu);
        getSupportActionBar().setTitle("Edit Job");


        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.navigation_save) {

            String jobStr=jobName.getText().toString();

            myRef.child("Jobs").child(key).child("JobName").setValue(jobStr);



            finish();
        }

        if (item.getItemId() == R.id.navigation_delete) {


            myRef.child("TimeCard").orderByChild("jobId").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                  if(dataSnapshot.exists()){

                    builder = new AlertDialog.Builder(EditActivity.this);
                    builder.setTitle("Warning");

                   builder.setMessage("Job is already in use").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {

                     builder.setCancelable(false);
                 }});
                      builder.show();
                  }
                  else{
                      myRef.child("Jobs").child(key).removeValue();
                      finish();
                  }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        return super.onOptionsItemSelected(item);
    }
}
