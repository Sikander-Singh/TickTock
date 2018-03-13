package com.example.sikandermangat.ticktock.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sikandermangat.ticktock.Activities.AddJob;
import com.example.sikandermangat.ticktock.Activities.HomeActivity;
import com.example.sikandermangat.ticktock.Activities.MainActivity;
import com.example.sikandermangat.ticktock.Activities.SettingActivity;
import com.example.sikandermangat.ticktock.Adapters.JobAdapter;
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


public class MoreOpition extends Fragment {

    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference myRef;
    private ImageView addbtn;
    private List<JobClass> list=new ArrayList<JobClass>();
    private RecyclerView recyclerView;
    public MoreOpition() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Manage Jobs");

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_more_opition, container, false);
        firebaseObject=new FirebaseDataBaseReferences();
        myRef=firebaseObject.getMyRef();
        addbtn = view.findViewById(R.id.addbtn);
        recyclerView =view.findViewById(R.id.recycleView);
        final JobAdapter adapter = new JobAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        mLayoutManager.setItemPrefetchEnabled(false);

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

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent screen = new Intent(getContext(),AddJob.class);
                startActivity(screen);

            }
        });

        return view;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.navigation_settings) {

            Intent screen = new Intent(getContext(),SettingActivity.class);
            startActivity(screen);

        }
        return super.onOptionsItemSelected(item);
    }


}
