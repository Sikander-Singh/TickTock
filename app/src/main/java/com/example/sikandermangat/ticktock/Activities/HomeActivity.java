package com.example.sikandermangat.ticktock.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sikandermangat.ticktock.Fragments.MoreOpition;
import com.example.sikandermangat.ticktock.Fragments.MyOverview;
import com.example.sikandermangat.ticktock.Fragments.MyTimeCard;
import com.example.sikandermangat.ticktock.Fragments.MyTimeSheet;
import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static javax.xml.datatype.DatatypeFactory.newInstance;

public class HomeActivity extends AppCompatActivity  {

    Fragment frag =new MyTimeCard();
    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference connectedRef;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseObject=new FirebaseDataBaseReferences();
        connectedRef=firebaseObject.getConnectedRef();
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);
                if (connected) {

                    Toast.makeText(HomeActivity.this,"online",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(HomeActivity.this,"offline",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.overview:
                        selectedFragment = new MyOverview();
                        transaction.replace(R.id.content,selectedFragment);
                        transaction.commit();
                        break;
                    case R.id.cardTime:

                        transaction.replace(R.id.content,frag);
                        transaction.commit();
                        break;
                    case R.id.timeSheet:
                        selectedFragment = new MyTimeSheet();
                        transaction.replace(R.id.content,selectedFragment);
                        transaction.commit();
                        break;
                    case R.id.more:
                        selectedFragment = new MoreOpition() ;
                        transaction.replace(R.id.content,selectedFragment);
                        transaction.commit();
                        break;
                }

                return true;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new MyOverview());
        transaction.commit();
    }





}
