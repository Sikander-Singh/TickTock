package com.example.sikandermangat.ticktock.Helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

/**
 * Created by Sikander Mangat on 2018-03-11.
 */

public class FirebaseDataBaseReferences  {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference connectedRef;
    private FirebaseAuth auth;
    private String userId;
    public FirebaseDataBaseReferences() {


        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        myRef=database.getReference();
        connectedRef=database.getReference(".info/connected");
        userId=auth.getCurrentUser().getUid();
    }
    public DatabaseReference getMyRef(){

        return myRef;
    }
    public DatabaseReference getConnectedRef(){

        return connectedRef;
    }
    public String getFirebaseUserid(){

        return userId;
    }
    public FirebaseAuth getAuth(){

        return auth;
    }
}
