package com.example.sikandermangat.ticktock.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.Models.JobClass;
import com.example.sikandermangat.ticktock.R;
import com.example.sikandermangat.ticktock.Models.TimCardClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditTimeCard extends AppCompatActivity {

    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference myRef;
    private EditText editStartTime;
    private EditText editEndTime;
    public static EditText jobTitle;
    public static String jobid;
    private EditText location;
    private EditText note;
    private EditText  startDate;
    private EditText endDate;
    private String startDateTime;
    private String endDateTime;
    private float totalTime;
    private TimCardClass timCardClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_card);
        getSupportActionBar().setTitle("Edit Time Card");
        firebaseObject=new FirebaseDataBaseReferences();
        myRef=firebaseObject.getMyRef();
        jobTitle = (EditText) findViewById(R.id.editJobTitle);
        note = (EditText) findViewById(R.id.editNote);
        location = (EditText) findViewById(R.id.editLocation);
        startDate=(EditText)findViewById(R.id.editDate);
        endDate=(EditText)findViewById(R.id.editEndDate);
        editStartTime = (EditText) findViewById(R.id.editStartTime);
        editEndTime = (EditText) findViewById(R.id.editEndTime);

         Bundle bundle=getIntent().getBundleExtra("timeBundle");

         timCardClass= (TimCardClass) bundle.getSerializable("object");
         jobid=timCardClass.getJobId();
         myRef.child("Jobs").orderByChild("jobId").equalTo(timCardClass.getJobId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                JobClass jobClass=dataSnapshot.getValue(JobClass.class);

                jobTitle.setText(jobClass.getJobName());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        startDate.setText(timCardClass.getDay());
         endDate.setText(timCardClass.getEndDate());
         editStartTime.setText(timCardClass.getStartTime());
         editEndTime.setText(timCardClass.getEndTime());
         location.setText(timCardClass.getLocationName());
         note.setText(timCardClass.getNotes());


        editStartTime.setFocusable(false);
        editStartTime.setClickable(true);
        editEndTime.setFocusable(false);
        editEndTime.setClickable(true);
        jobTitle.setFocusable(false);
        jobTitle.setClickable(true);
        startDate.setFocusable(false);
        startDate.setClickable(true);
        endDate.setFocusable(false);
        endDate.setClickable(true);


        editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SimpleDateFormat fr=new SimpleDateFormat("hh:mm:ss");
                Date dateStr=null;
                try {
                    dateStr=fr.parse(editStartTime.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final int hour =dateStr.getHours();
                final int minute =dateStr.getMinutes();
                final int second = dateStr.getSeconds();
                final MyTimePickerDialog timePickerDialog = new MyTimePickerDialog(EditTimeCard.this, new MyTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {

                        editStartTime.setText(hourOfDay + ":" + minute + ":"+seconds);
                    }
                }, hour, minute, second, false);
                timePickerDialog.show();
            }

        });
        editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat fr=new SimpleDateFormat("hh:mm:ss");
                Date dateStr=null;
                try {
                    dateStr=fr.parse(editEndTime.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final int hour =dateStr.getHours();
                final int minute =dateStr.getMinutes();
                final int second = dateStr.getSeconds();


                MyTimePickerDialog timePickerDialog = new MyTimePickerDialog(EditTimeCard.this, new MyTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {

                        editEndTime.setText(hourOfDay + ":" + minute + ":"+seconds);



                    }
                },hour,minute,second,false);
                timePickerDialog.show();
            }

        });


        startDate.setOnClickListener(new View.OnClickListener() {


            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog=new DatePickerDialog(EditTimeCard.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        Date d = null;
                        int monthValue=0;
                        month=datePicker.getMonth()+1;
                        //System.out.println(datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear());
                        String str=datePicker.getDayOfMonth()+"-"+month+"-"+datePicker.getYear();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);

                        try {
                             d=simpleDateFormat.parse(str);
                            SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
                            startDate.setText(format.format(d));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },year,month,day);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
                Date date=null;
                try {
                    date=simpleDateFormat.parse(endDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePickerDialog.getDatePicker().setMaxDate(date.getTime());
                datePickerDialog.show();

            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            @Override
            public void onClick(View view) {

               DatePickerDialog datePickerDialog=new DatePickerDialog(EditTimeCard.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                       Date d = null;
                       int monthValue=0;
                       month=datePicker.getMonth()+1;
                       //System.out.println(datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+datePicker.getYear());
                       String str=datePicker.getDayOfMonth()+"-"+month+"-"+datePicker.getYear();
                       SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);

                       try {
                           d=simpleDateFormat.parse(str);
                           SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
                           endDate.setText(format.format(d));
                       } catch (ParseException e) {
                           e.printStackTrace();
                       }



                   }
               },year,month,day);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
                Date date=null;
                try {
                    date=simpleDateFormat.parse(startDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePickerDialog.getDatePicker().setMinDate(date.getTime());
                datePickerDialog.show();

            }
        });


        jobTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent screen = new Intent(EditTimeCard.this, JobList.class);

                screen.putExtra("timeCard", "TimeCard");
                startActivity(screen);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save, menu);
        getMenuInflater().inflate(R.menu.delete, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.navigation_save) {

            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

            Date startTime=null;
            Date endTime=null;
            try {
                startTime=format.parse(startDate.getText().toString()+" "+editStartTime.getText());
                endTime=format.parse(endDate.getText().toString()+" "+editEndTime.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(startTime.compareTo(endTime)>0){

                final AlertDialog.Builder   builder = new AlertDialog.Builder(EditTimeCard.this);
                builder.setTitle("Warning");
                builder.setMessage("End date should be greater than start date").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }});
                builder.show();

            }
            else if(startTime.compareTo(endTime)<0 || startTime.compareTo(endTime)==0){

                CalTotalTime();
                TimCardClass object = new TimCardClass();
                object.setJobId(jobid);
                object.setStartTime(editStartTime.getText().toString());
                object.setEndTime(editEndTime.getText().toString());
                object.setLocationName(location.getText().toString());
                object.setNotes(note.getText().toString());
                object.setDay(startDate.getText().toString());
                object.setEndDate(endDate.getText().toString());
                object.setUserId(timCardClass.getUserId());
                object.setTimeCardId(timCardClass.getTimeCardId());
                object.setTotalTime(totalTime);
                object.setRating(timCardClass.getRating());
                myRef.child("TimeCard").child(timCardClass.getTimeCardId()).setValue(object);
                finish();


            }

        }
        if (item.getItemId() == R.id.navigation_delete) {

            final ProgressDialog progressDialog = new ProgressDialog(EditTimeCard.this);
            progressDialog.setMessage("Deleting....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            myRef.child("TimeCard").child(timCardClass.getTimeCardId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        progressDialog.dismiss();
                        Toast.makeText(EditTimeCard.this, "Deleted", Toast.LENGTH_SHORT);
                        finish();
                    } else {

                        progressDialog.dismiss();

                        Toast.makeText(EditTimeCard.this, "Server failed", Toast.LENGTH_SHORT);
                    }
                }
            });



        }
        return super.onOptionsItemSelected(item);
    }

    public void CalTotalTime(){


         startDateTime=startDate.getText()+" "+editStartTime.getText().toString();

         endDateTime=endDate.getText()+" "+editEndTime.getText().toString();


         System.out.println("Checkingggggggggggg"+startDateTime);

         System.out.println("Checkingggggggggggg"+endDateTime);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

        Date d1 = null ;
        Date d2 = null;
        try {
            d1 = format.parse(startDateTime);
            d2 = format.parse(endDateTime);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays+ " days, ");
            System.out.print(diffHours+ " hours, ");
            System.out.print(diffMinutes+ " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            totalTime=diffDays*24*60*60+diffHours*60*60+diffMinutes*60+diffSeconds;

            totalTime=totalTime/60;
            System.out.println("Resultttttttttttttttttttttt"+totalTime);


        } catch (Exception e) {
            e.printStackTrace();
        }




    }

}




