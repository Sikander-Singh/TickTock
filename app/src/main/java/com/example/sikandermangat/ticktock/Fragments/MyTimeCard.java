package com.example.sikandermangat.ticktock.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sikandermangat.ticktock.Activities.HomeActivity;
import com.example.sikandermangat.ticktock.Activities.MainActivity;
import com.example.sikandermangat.ticktock.Activities.JobList;
import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.Helpers.LocationService;
import com.example.sikandermangat.ticktock.Models.TimCardClass;
import com.example.sikandermangat.ticktock.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;




public class MyTimeCard extends Fragment{

    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference myRef;
    private EditText time;
    private EditText day;
    public static EditText job;
    public static String jobId;
    private EditText location;
    private EditText notes;
    private Button clockIn;
    private Button clockOut;
    private  String key;
    private static Chronometer chrono ;
    private static long LastTime;
    private static long timeStamp;
    private TimCardClass timCardObject;
    private LocationService locationObject;
    public MyTimeCard() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Time Card");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_my_time_card, container, false);
        firebaseObject=new FirebaseDataBaseReferences();
        myRef=firebaseObject.getMyRef();
        timCardObject=new TimCardClass();
        time = view.findViewById(R.id.time);
        time.setFocusable(false);
        day=view.findViewById(R.id.day);
        day.setFocusable(false);
        job=view.findViewById(R.id.selectJob);
        job.setFocusable(false);
        job.setClickable(true);
        location=view.findViewById(R.id.location);
        notes=view.findViewById(R.id.notes);
        clockIn=view.findViewById(R.id.clockInbtn);
        clockOut=view.findViewById(R.id.clockOutbtn);
        clockOut.setVisibility(View.GONE);
        chrono=view.findViewById(R.id.chronometer);

        //Get Time Stamp
        myRef.child("TimeStamp").child(firebaseObject.getFirebaseUserid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("timeStamp").exists()){
                    timeStamp= dataSnapshot.child("timeStamp").getValue(long.class);
                    key=dataSnapshot.child("key").getValue(String.class);

                }

                if(timeStamp!=0){

                    clockIn.setVisibility(View.GONE);
                    clockOut.setVisibility(View.VISIBLE);
                    chrono.setBase(timeStamp);
                    job.setText(dataSnapshot.child("jobName").getValue(String.class));
                    location.setText(dataSnapshot.child("location").getValue(String.class));
                    notes.setText(dataSnapshot.child("notes").getValue(String.class));
                    jobId=dataSnapshot.child("jobId").getValue(String.class);
                    chrono.start();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Get Time Stamp

        if(LastTime!=0) {

            clockIn.setVisibility(View.GONE);
            clockOut.setVisibility(View.VISIBLE);
            chrono.setBase(LastTime);
            chrono.start();
        }


        clockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(job.getText().toString().isEmpty()){

                    Toast.makeText(getContext(),"Select your job first",Toast.LENGTH_SHORT).show();
                }
                else {

                    chrono.setBase(SystemClock.elapsedRealtime());

                    chrono.start();

                    LastTime = SystemClock.elapsedRealtime();

                    timeStamp=SystemClock.elapsedRealtime();
                    String jobNameStr=job.getText().toString();
                    String timeStr=time.getText().toString();
                    String locationStr=location.getText().toString();
                    String notesStr=notes.getText().toString();
                    String dayStr=day.getText().toString();
                    key=timCardObject.InsertClockInDataIntoFirebase(timeStr,jobId,locationStr,notesStr,dayStr);

                    timCardObject.InsertTimeStampIntoFirebase(firebaseObject.getFirebaseUserid(),timeStamp,key,jobNameStr,jobId,locationStr,notesStr);

                    clockIn.setVisibility(View.GONE);

                    clockOut.setVisibility(View.VISIBLE);
                }

            }
        });

        clockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                chrono.stop();

                timeStamp=0;

                LastTime=0;

                float totalTime=timCardObject.CalculateTotalTime((float) (SystemClock.elapsedRealtime() - chrono.getBase()));
                chrono.setBase(SystemClock.elapsedRealtime());

                timCardObject.RemoveTimeStampFromFirebase(firebaseObject.getFirebaseUserid());

                timCardObject.InsertClockOutDataIntoFirebase(key,time.getText().toString(),jobId,location.getText().toString(),notes.getText().toString(),totalTime,day.getText().toString());

                timCardObject.ShowRatingDialogBox(getContext(),key);

                clockOut.setVisibility(View.GONE);

                clockIn.setVisibility(View.VISIBLE);
            }
        });

        CountDownTimer timeTimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Date date = new Date();
                SimpleDateFormat simpDate;
                simpDate = new SimpleDateFormat("kk:mm:ss");
                time.setText(simpDate.format(date));

            }
            public void onFinish() {

            }
        };
        timeTimer.start();

        CountDownTimer dayTimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Date date = new Date();
                System.out.println(date);
                SimpleDateFormat simpDate;
                simpDate = new SimpleDateFormat("dd-MMM-yyyy");
                day.setText(simpDate.format(date));

            }
            public void onFinish() {

            }
        };
         dayTimer.start();

        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent screen=new Intent(getActivity(),JobList.class);
                screen.putExtra("timeCard","MyTimeCard");
                startActivity(screen);
            }
        });


      location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String locationName="";
                try {
                    locationObject=new LocationService();
                    locationName=locationObject.location(getContext(),getActivity());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                location.setText(locationName);
            }
        });

        return view;
    }


}
