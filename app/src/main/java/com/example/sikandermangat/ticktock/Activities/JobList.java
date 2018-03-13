package com.example.sikandermangat.ticktock.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sikandermangat.ticktock.Adapters.JobListAdapter;
import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.Models.JobClass;
import com.example.sikandermangat.ticktock.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobList extends AppCompatActivity {



    List<JobClass> list = new ArrayList<JobClass>();
    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference myRef;
    RecyclerView recyclerView;
    private JobListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseObject=new FirebaseDataBaseReferences();
        myRef=firebaseObject.getMyRef();
        setContentView(R.layout.activity_job_list);
        getSupportActionBar().setTitle("Select Job");
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewList);
        adapter = new JobListAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

       adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

       myRef.child("Jobs").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               list.clear();
               for (DataSnapshot jobDataSnapshot : dataSnapshot.getChildren()) {

                   JobClass jobObject = jobDataSnapshot.getValue(JobClass.class);

                   if(jobObject.getUserId().equals(firebaseObject.getFirebaseUserid())){

                       list.add(jobObject);

                   }
               }

               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }






}
