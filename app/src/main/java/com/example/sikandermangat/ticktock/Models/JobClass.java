package com.example.sikandermangat.ticktock.Models;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sikander Mangat on 2018-02-04.
 */

public class JobClass {

    private String JobId;
    private String JobName;
    private String userId;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public JobClass(String jobId, String jobName) {
        JobId = jobId;
        JobName = jobName;
    }

    public JobClass() {


    }

    public String getJobId() {

        return JobId;
    }

    public void setJobId(String jobId) {
        JobId = jobId;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }
}
