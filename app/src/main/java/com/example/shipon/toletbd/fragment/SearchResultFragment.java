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

import java.util.ArrayList;

import static com.example.shipon.toletbd.activity.Main2Activity.apartments;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    RecyclerView RV;
    ApartmentAdopter myAdapter;
    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_result, container, false);
        RV=view.findViewById(R.id.ApartmentRV);


        /*apartments.add(new Apartment("Shipon","01942040142",
                "Khulna","bachelor","hostel","October","Somethings"
                ,"sd","12000"));
        apartments.add(new Apartment("Shipon","01942040142",
                "Khulna","bachelor","hostel","October","Somethings"
                ,"sd","12000"));*/

    myAdapter = new ApartmentAdopter(getActivity(), apartments);
    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
    llm.setOrientation(LinearLayoutManager.VERTICAL);
    RV.setLayoutManager(llm);

    RV.setAdapter(myAdapter);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

    }


}
