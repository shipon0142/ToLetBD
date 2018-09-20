package com.example.shipon.toletbd.database;

import com.example.shipon.toletbd.models.Apartment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Shipon on 9/20/2018.
 */

public class FirebaseClient{

    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference appertmentsRef=myDatabaseRef.child("Appertments");
    public void addApartment(Apartment apartment){

    }

}
