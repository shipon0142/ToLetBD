package com.example.shipon.toletbd.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.shipon.toletbd.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchHomeFragment extends Fragment {
String name[]= {"---Select Category---","All","Family","Sublet","Boys Only","Girls Only","Boys Hostel","Girls Hostel"};
Spinner catagorySpinner;

    public SearchHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search_home, container, false);
        catagorySpinner=view.findViewById(R.id.CatagorySpinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getContext(), R.layout.spinner_layout,
                        name); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        catagorySpinner.setAdapter(spinnerArrayAdapter);
        return view;
    }

}
