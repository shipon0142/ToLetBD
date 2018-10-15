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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.adopter.ApartmentAdopter;
import com.example.shipon.toletbd.models.Apartment;

import java.util.ArrayList;

import static com.example.shipon.toletbd.activity.Main2Activity.SEARCH_AREA;
import static com.example.shipon.toletbd.activity.Main2Activity.SEARCH_CATAGORY;
import static com.example.shipon.toletbd.activity.Main2Activity.SEARCH_DISTRICT;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchHomeFragment extends Fragment {
String name[]= {"---Select Category---","All","Family","Sublet","Boys Only","Girls Only","Boys Hostel","Girls Hostel"};
Spinner catagorySpinner;
AutoCompleteTextView districtSearchET,areaSearchET;
    private static final String[] district = new String[] {
            "Dhaka", "Khulna", "Chittagong", "Sylhet", "Barisal","Rajshai"

    };
    private static final String[] dhakaArea = new String[] {

            "Mirpur", "Mohammadpur", "Sher-E-Bangla Nagar", "Pallabi", "Adabor", "Kafrul", "Dhaka Cantonment"
            , "Tejgaon", "Gulshan", "Rampura", "Banani", "Khilkhet", "Badda", "Uttara", "Uttarkhan", "Dakkshinkhan"
            ,"Paltan", "Sabujbagh", "Jatrabari", "Motijheel", "Dhaka Kotwali", "Sutrapur", "Bangsal", "Wari"
            , "Ramna", "Gendaria", "Lalbagh", "Hazaribagh", "Dhanmondi", "Shahbagh", "New Market"
            , "Khilgaon", "Kamrangirchar"
    };
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
        districtSearchET=view.findViewById(R.id.DistrictSearchET);
        areaSearchET=view.findViewById(R.id.AreaSearchET);
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, district);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, dhakaArea);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getContext(), R.layout.spinner_layout,
                        name); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        catagorySpinner.setAdapter(spinnerArrayAdapter);
        districtSearchET.setAdapter(districtAdapter);
        areaSearchET.setAdapter(areaAdapter);
        SEARCH_DISTRICT="null";
        SEARCH_AREA="null";
        SEARCH_CATAGORY="null";
        catagorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SEARCH_CATAGORY=name[position].toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SEARCH_CATAGORY.contains("Select"))SEARCH_CATAGORY="null";
                if(!districtSearchET.getText().toString().equals(""))SEARCH_DISTRICT=districtSearchET.getText().toString();
                if(!areaSearchET.getText().toString().equals(""))SEARCH_AREA=areaSearchET.getText().toString();

                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                SearchResultFragment searchResultFragment = new SearchResultFragment();
                transaction.replace(R.id.mainLayout, searchResultFragment).commit();
            }
        });


        return view;
    }

}
