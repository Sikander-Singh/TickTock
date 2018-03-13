package com.example.sikandermangat.ticktock.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sikandermangat.ticktock.Fragments.MyTimeCard;
import com.example.sikandermangat.ticktock.Models.JobClass;
import com.example.sikandermangat.ticktock.R;

import java.util.List;



/**
 * Created by Sikander Mangat on 2018-02-08.
 */

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.MyViewHolder>{

    private List<JobClass> jobList;
    private Context c;
    private View.OnClickListener mClickListener;
    public JobListAdapter(List<JobClass> jobList){

        this.jobList=jobList;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_row, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

       final JobClass job=jobList.get(position);

        holder.jobName.setText(job.getJobName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyTimeCard.job.setText(job.getJobName());
                MyTimeCard.jobId = job.getJobId();
                MyTimeCard.job.setText(job.getJobName());
                MyTimeCard.jobId = job.getJobId();
                mClickListener.onClick(view);



            }

        });


    }


    @Override
    public int getItemCount() {

        return jobList.size();

    }





    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView jobName;
        RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            c=itemView.getContext();
            jobName=(TextView)itemView.findViewById(R.id.jobName);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout_job_row);
        }
    }

    public void setClickListener(View.OnClickListener callback) {
         mClickListener = callback;
    }
}
