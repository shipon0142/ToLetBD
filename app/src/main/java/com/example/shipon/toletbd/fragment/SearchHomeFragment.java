package com.example.shipon.toletbd.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.adopter.ApartmentAdopter;
import com.example.shipon.toletbd.models.Apartment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchHomeFragment extends Fragment {
String name[]= {"---Select Category---","All","Family","Sublet","Boys Only","Girls Only","Boys Hostel","Girls Hostel"};
Spinner catagorySpinner;

TextView searchTV;
    public SearchHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search_home, container, false);
        catagorySpinner=view.findViewById(R.id.CatagorySpinner);
        searchTV=view.findViewById(R.id.SearchTV);

        searchTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                SearchResultFragment searchResultFragment = new SearchResultFragment();
                transaction.replace(R.id.mainLayout, searchResultFragment).commit();
            }
        });
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getContext(), R.layout.spinner_layout,
                        name); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        catagorySpinner.setAdapter(spinnerArrayAdapter);

        return view;
    }

}
