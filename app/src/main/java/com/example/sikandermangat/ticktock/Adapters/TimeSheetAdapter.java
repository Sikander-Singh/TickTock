package com.example.sikandermangat.ticktock.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sikandermangat.ticktock.Activities.EditTimeCard;
import com.example.sikandermangat.ticktock.Fragments.MyTimeSheet;
import com.example.sikandermangat.ticktock.Helpers.FirebaseDataBaseReferences;
import com.example.sikandermangat.ticktock.Models.JobClass;
import com.example.sikandermangat.ticktock.Models.TimCardClass;
import com.example.sikandermangat.ticktock.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Sikander Mangat on 2018-02-16.
 */

public class TimeSheetAdapter extends RecyclerView.Adapter<TimeSheetAdapter.MyViewHolder>{


    private FirebaseDataBaseReferences firebaseObject;
    private DatabaseReference myRef;
    private List<TimCardClass> jobList;
    private Context c;
    private View.OnClickListener mClickListener;
    private String jobStr;
    public TimeSheetAdapter(List<TimCardClass> jobList){

        this.jobList=jobList;
        firebaseObject=new FirebaseDataBaseReferences();
        myRef=firebaseObject.getMyRef();


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timesheet_row, parent, false);

        c=itemView.getContext();
        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final TimeSheetAdapter.MyViewHolder holder, final int position) {


        final TimCardClass job = jobList.get(position);

           //Get Job data on basis of jobId

        myRef.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot jobDataSnapshot : dataSnapshot.getChildren()) {

                    JobClass jobObject = jobDataSnapshot.getValue(JobClass.class);


                    if (jobObject.getJobId().equals(job.getJobId())) {


                        jobStr = jobObject.getJobName();

                        holder.jobName.setText(jobStr);
                        holder.ratingBar.setRating(job.getRating());

                    }
                }
            }

            //Get Job data on basis of jobId
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DecimalFormat df=new DecimalFormat("###.# ");
        if (job.getTotalTime() < 1) {

            double value= Double.parseDouble(df.format(job.getTotalTime()*60));
            holder.jobTime.setText(value + "s");


        } else {

            holder.jobTime.setText( Double.parseDouble(df.format(job.getTotalTime())) + "m");

        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TimCardClass timCardClass = jobList.get(position);

                if(timCardClass.getTotalTime()!=0){

                    Intent screen = new Intent(c, EditTimeCard.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("object", timCardClass);
                    screen.putExtra("timeBundle", bundle);
                    c.startActivity(screen);
                }
                else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setTitle("Warning");

                    builder.setMessage("Job is running").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            builder.setCancelable(false);
                        }});
                    builder.show();


                }
            }

        });

    }

    @Override
    public int getItemCount() {

        return jobList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView jobName,jobTime;
        public SimpleRatingBar ratingBar;
        RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            c=itemView.getContext();
            jobName=(TextView)itemView.findViewById(R.id.shiftTitle);
            jobTime=(TextView)itemView.findViewById(R.id.shiftTime);
            ratingBar=(SimpleRatingBar)itemView.findViewById(R.id.timeRatingBar);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout_time_sheet);
            ratingBar.setFocusable(false);
            ratingBar.setIndicator(true);
        }
    }






}
