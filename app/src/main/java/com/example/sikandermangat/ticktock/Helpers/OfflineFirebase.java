package com.example.sikandermangat.ticktock.Helpers;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sikander Mangat on 2018-03-12.
 */

public class OfflineFirebase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
