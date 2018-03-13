package com.example.sikandermangat.ticktock.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sikandermangat.ticktock.Activities.EditActivity;
import com.example.sikandermangat.ticktock.Models.JobClass;
import com.example.sikandermangat.ticktock.R;

import java.util.List;

/**
 * Created by Sikander Mangat on 2018-02-04.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder>{

    private List<JobClass> jobList;
    private Context c;

    public JobAdapter(List<JobClass> jobList){

        this.jobList=jobList;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_row, parent, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        JobClass job=jobList.get(position);

        holder.jobName.setText(job.getJobName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent screen=new Intent(c,EditActivity.class);
                screen.putExtra("JobId",jobList.get(position).getJobId());
                screen.putExtra("JobName",jobList.get(position).getJobName());
                c.startActivity(screen);
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
}
