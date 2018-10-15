package com.example.shipon.toletbd.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.adopter.ApartmentAdopter;
import com.example.shipon.toletbd.database.FirebaseClient;
import com.example.shipon.toletbd.models.Apartment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.shipon.toletbd.activity.Main2Activity.SEARCH_AREA;
import static com.example.shipon.toletbd.activity.Main2Activity.SEARCH_CATAGORY;
import static com.example.shipon.toletbd.activity.Main2Activity.SEARCH_DISTRICT;
import static com.example.shipon.toletbd.activity.Main2Activity.apartments;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    public static String OWNER_NAME = "owner_name";
    public static String CONTACT = "contact";
    public static String DISTRICT = "district";
    public static String AREA = "area";
    public static String HOUSE_NO = "houseno";
    public static String CATAGORY = "catagory";
    public static String MONTH = "month";
    public static String COST = "cost";
    public static String DETAILS = "details";
    public static String IMG_SIZE = "size";
    RecyclerView RV;
    ApartmentAdopter myAdapter;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference appertmentsRef = myDatabaseRef.child("Appertments");

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        RV = view.findViewById(R.id.ApartmentRV);

        setAdopter();

        return view;
    }

    private void setAdopter() {
        ArrayList<Apartment> apprt = new ArrayList<>();
        for (int i = 0; i < apartments.size(); i++) {
            if (SEARCH_CATAGORY.equals("null") && SEARCH_DISTRICT.equals("null") && SEARCH_AREA.equals("null")) {
                apprt = apartments;
            } else if (!SEARCH_CATAGORY.equals("null") && SEARCH_DISTRICT.equals("null") && SEARCH_AREA.equals("null")) {
                if (apartments.get(i).getHomeCatagory().equals(SEARCH_CATAGORY))
                    apprt.add(apartments.get(i));
            } else if (SEARCH_CATAGORY.equals("null") && !SEARCH_DISTRICT.equals("null") && SEARCH_AREA.equals("null")) {
                if (apartments.get(i).getDistrictOwner().equals(SEARCH_DISTRICT))
                    apprt.add(apartments.get(i));
            } else if (SEARCH_CATAGORY.equals("null") && SEARCH_DISTRICT.equals("null") && !SEARCH_AREA.equals("null")) {
                if (apartments.get(i).getAreaOwner().equals(SEARCH_AREA))
                    apprt.add(apartments.get(i));
            } else if (!SEARCH_CATAGORY.equals("null") && !SEARCH_DISTRICT.equals("null") && SEARCH_AREA.equals("null")) {
                if (apartments.get(i).getHomeCatagory().equals(SEARCH_CATAGORY) && apartments.get(i).getDistrictOwner().equals(SEARCH_DISTRICT))
                    apprt.add(apartments.get(i));
            } else if (!SEARCH_CATAGORY.equals("null") && SEARCH_DISTRICT.equals("null") && !SEARCH_AREA.equals("null")) {
                if (apartments.get(i).getHomeCatagory().equals(SEARCH_CATAGORY) && apartments.get(i).getAreaOwner().equals(SEARCH_AREA))
                    apprt.add(apartments.get(i));
            } else if (SEARCH_CATAGORY.equals("null") && !SEARCH_DISTRICT.equals("null") && !SEARCH_AREA.equals("null")) {
                if (apartments.get(i).getDistrictOwner().equals(SEARCH_DISTRICT) && apartments.get(i).getAreaOwner().equals(SEARCH_AREA))
                    apprt.add(apartments.get(i));
            } else if (!SEARCH_CATAGORY.equals("null") && !SEARCH_DISTRICT.equals("null") && !SEARCH_AREA.equals("null")) {
                if (apartments.get(i).getHomeCatagory().equals(SEARCH_CATAGORY) && apartments.get(i).getDistrictOwner().equals(SEARCH_DISTRICT) && apartments.get(i).getAreaOwner().equals(SEARCH_AREA))
                    apprt.add(apartments.get(i));
            }
        }
        myAdapter = new ApartmentAdopter(getActivity(), apprt);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RV.setLayoutManager(llm);
        RV.setAdapter(myAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void retriveAllAppartment() {
        apartments.clear();
        appertmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String key = (String) dsp.getKey();
                    String ownername = (String) dsp.child(OWNER_NAME).getValue();
                    String area = (String) dsp.child(AREA).getValue();
                    String catagory = (String) dsp.child(CATAGORY).getValue();
                    String contact = (String) dsp.child(CONTACT).getValue();
                    String cost = (String) dsp.child(COST).getValue();
                    String details = (String) dsp.child(DETAILS).getValue();
                    String district = (String) dsp.child(DISTRICT).getValue();
                    String houseno = (String) dsp.child(HOUSE_NO).getValue();
                    String month = (String) dsp.child(MONTH).getValue();
                    String size = (String) dsp.child(IMG_SIZE).getValue();
                    apartments.add(new Apartment(ownername, contact, district, area, catagory, month, details, houseno, cost, key, size));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
