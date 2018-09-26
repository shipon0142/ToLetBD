package com.example.shipon.toletbd.database;

import com.example.shipon.toletbd.models.Apartment;
import com.example.shipon.toletbd.models.Owner;
import com.example.shipon.toletbd.models.Renter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.shipon.toletbd.activity.Main2Activity.apartments;

/**
 * Created by Shipon on 9/20/2018.
 */

public class FirebaseClient {
    public static String OWNER_NAME = "owner_name";
    public static String CONTACT = "contact";
    public static String DISTRICT = "district";
    public static String AREA = "area";
    public static String HOUSE_NO = "houseno";
    public static String CATAGORY = "catagory";
    public static String MONTH = "month";
    public static String COST = "cost";
    public static String DETAILS = "details";
    long child = 0;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference appertmentsRef = myDatabaseRef.child("Appertments");
    DatabaseReference ownerRef = myDatabaseRef.child("Owners");
    DatabaseReference renterRef = myDatabaseRef.child("Renters");

    public void addApartment(final Apartment apartment) {
        appertmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                child = dataSnapshot.getChildrenCount();
                appertmentsRef.child("apartment_" + (child + 1)).child(OWNER_NAME).setValue(apartment.getNameOwner());
                appertmentsRef.child("apartment_" + (child + 1)).child(CONTACT).setValue(apartment.getContactOwner());
                appertmentsRef.child("apartment_" + (child + 1)).child(DISTRICT).setValue(apartment.getDistrictOwner());
                appertmentsRef.child("apartment_" + (child + 1)).child(AREA).setValue(apartment.getAreaOwner());
                appertmentsRef.child("apartment_" + (child + 1)).child(HOUSE_NO).setValue(apartment.getHouseNo());
                appertmentsRef.child("apartment_" + (child + 1)).child(CATAGORY).setValue(apartment.getHomeCatagory());
                appertmentsRef.child("apartment_" + (child + 1)).child(COST).setValue(apartment.getCostHome());
                appertmentsRef.child("apartment_" + (child + 1)).child(MONTH).setValue(apartment.getFromMonth());
                appertmentsRef.child("apartment_" + (child + 1)).child(DETAILS).setValue(apartment.getDetailsOwnerHome());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    public void addOwner(Owner owner) {
        DatabaseReference ref = ownerRef.child(owner.getOwnerContact());
        ref.child("name").setValue(owner.getOwnerName());
        ref.child("contact").setValue(owner.getOwnerContact());
        ref.child("address").setValue(owner.getOwnerAddress());
        ref.child("occupation").setValue(owner.getOwnerOccupation());
        ref.child("password").setValue(owner.getOwnerPassword());
    }

    public void addRenter(Renter renter) {
        DatabaseReference ref = renterRef.child(renter.getRenterContact());
        ref.child("name").setValue(renter.getRenterName());
        ref.child("contact").setValue(renter.getRenterContact());
        ref.child("income").setValue(renter.getRenterMonthlyIncome());
        ref.child("status").setValue(renter.getRenterStatus());
        ref.child("occupation").setValue(renter.getRenterOccupation());
        ref.child("password").setValue(renter.getRenterPassword());
    }

    public void retriveAllAppartment() {

        appertmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String ownername = (String) dsp.child(OWNER_NAME).getValue();
                    String area = (String) dsp.child(AREA).getValue();
                    String catagory = (String) dsp.child(CATAGORY).getValue();
                    String contact = (String) dsp.child(CONTACT).getValue();
                    String cost = (String) dsp.child(COST).getValue();
                    String details = (String) dsp.child(DETAILS).getValue();
                    String district = (String) dsp.child(DISTRICT).getValue();
                    String houseno = (String) dsp.child(HOUSE_NO).getValue();
                    String month = (String) dsp.child(MONTH).getValue();
                    apartments.add(new Apartment(ownername, contact, district, area, catagory, month, details, houseno, cost));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
