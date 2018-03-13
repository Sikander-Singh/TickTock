package com.example.sikandermangat.ticktock.Models;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.sikandermangat.ticktock.Activities.MainActivity;
import com.example.sikandermangat.ticktock.Fragments.MyTimeCard;
import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.R;
import com.google.firebase.database.DatabaseReference;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.io.Serializable;

/**
 * Created by Sikander Mangat on 2018-02-08.
 */

public class TimCardClass implements Serializable {

;
    private String StartTime;
    private String EndTime;
    private String Day;
    private String Notes;
    private String LocationName;
    private String JobId;
    private String UserId;
    private String endDate;
    private String TimeCardId;
    private float TotalTime;
    private float rating;
    private Dialog ratingDialog;
    public TimCardClass() {

    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public float getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(float totalTime) {
        TotalTime = totalTime;
    }


    public String getTimeCardId() {
        return TimeCardId;
    }

    public void setTimeCardId(String timeCardId) {
        TimeCardId = timeCardId;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getJobId() {
        return JobId;
    }

    public void setJobId(String jobId) {
        JobId = jobId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void InsertTimeStampIntoFirebase(String userId,long timeStamp,String key,String jobName,String jobId,String location,String notes){

        FirebaseDataBaseReferences firebaseObject;
        DatabaseReference myRef;
        firebaseObject = new FirebaseDataBaseReferences();
        myRef = firebaseObject.getMyRef();
        myRef.child("TimeStamp").child(firebaseObject.getFirebaseUserid()).child("userId").setValue(userId);
        myRef.child("TimeStamp").child(firebaseObject.getFirebaseUserid()).child("timeStamp").setValue(timeStamp);
        myRef.child("TimeStamp").child(firebaseObject.getFirebaseUserid()).child("key").setValue(key);
        myRef.child("TimeStamp").child(firebaseObject.getFirebaseUserid()).child("jobName").setValue(jobName);
        myRef.child("TimeStamp").child(firebaseObject.getFirebaseUserid()).child("jobId").setValue(jobId);
        myRef.child("TimeStamp").child(firebaseObject.getFirebaseUserid()).child("location").setValue(location);
        myRef.child("TimeStamp").child(firebaseObject.getFirebaseUserid()).child("notes").setValue(notes);
    }

    public void RemoveTimeStampFromFirebase(String userId){
        FirebaseDataBaseReferences firebaseObject;
        DatabaseReference myRef;
        firebaseObject = new FirebaseDataBaseReferences();
        myRef = firebaseObject.getMyRef();
        myRef.child("TimeStamp").child(userId).removeValue();
    }

    public String InsertClockInDataIntoFirebase(String startTime,String jobId,String location,String notes,String startDate){

        FirebaseDataBaseReferences firebaseObject;
        DatabaseReference myRef;
        firebaseObject = new FirebaseDataBaseReferences();
        myRef = firebaseObject.getMyRef();
        String key=myRef.child("TimeCard").push().getKey();
        TimCardClass timCardObject=new TimCardClass();
        timCardObject.setTimeCardId(key);
        timCardObject.setUserId(firebaseObject.getFirebaseUserid());
        timCardObject.setJobId(jobId);
        timCardObject.setDay(startDate);
        timCardObject.setEndDate(endDate);
        timCardObject.setStartTime(startTime);
        timCardObject.setEndTime(null);
        timCardObject.setLocationName(location);
        timCardObject.setNotes(notes);
        myRef.child("TimeCard").child(key).setValue(timCardObject);
        return key;

    }
    public void InsertClockOutDataIntoFirebase(String key,String endTime,String jobId,String location,String notes,float totalTime,String endDate){
        FirebaseDataBaseReferences firebaseObject;
        DatabaseReference myRef;
        firebaseObject = new FirebaseDataBaseReferences();
        myRef = firebaseObject.getMyRef();
        myRef.child("TimeCard").child(key).child("endTime").setValue(endTime);
        myRef.child("TimeCard").child(key).child("jobId").setValue(jobId);
        myRef.child("TimeCard").child(key).child("locationName").setValue(location);
        myRef.child("TimeCard").child(key).child("notes").setValue(notes);
        myRef.child("TimeCard").child(key).child("totalTime").setValue(totalTime);
        myRef.child("TimeCard").child(key).child("endDate").setValue(endDate);

    }

    public float CalculateTotalTime(float time ){

        float seconds=time/1000;
        float minute=seconds/60;
        return minute;
    }

    public void ShowRatingDialogBox(Context context, final String key){
        FirebaseDataBaseReferences firebaseObject;
        final DatabaseReference myRef;
        firebaseObject = new FirebaseDataBaseReferences();
        myRef = firebaseObject.getMyRef();
        ratingDialog=new Dialog(context, R.style.Theme_AppCompat_Dialog);
        ratingDialog.setContentView(R.layout.layout_rating);
        ratingDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT,600);
        ratingDialog.setCancelable(false);
        final String keyStr=key;
        final SimpleRatingBar ratingBar=(SimpleRatingBar)ratingDialog.findViewById(R.id.ratingBar);
        Button submit=(Button) ratingDialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.child("TimeCard").child(keyStr).child("rating").setValue(ratingBar.getRating());
                ratingDialog.dismiss();
            }
        });
        ratingDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {


            }
        });
       ratingDialog.show();
    }

}
