package com.example.shipon.toletbd.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.activity.Main2Activity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment{

    String catagory[]= {"---Select Category---","All","Family","Sublet","Boys Only","Girls Only","Boys Hostel","Girls Hostel"};
    String month[]= {"---Select Month---","January","February","March","April","May","June","July","August","September","October","November","December"};

    Spinner catagorySpinner;
    Spinner monthSpinner;
    String catagoryType,monthName;
    EditText districtET,areaET,costET,detailsET;
      TextView toolbarPost;
    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbarPost = getActivity().findViewById(R.id.PostText);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post, container, false);
        catagorySpinner=view.findViewById(R.id.CatagorySpinnerPost);
        monthSpinner=view.findViewById(R.id.MonthSpinnerPost);
        districtET=view.findViewById(R.id.DistrictET);
        areaET=view.findViewById(R.id.AreaET);
        costET=view.findViewById(R.id.CostET);
        detailsET=view.findViewById(R.id.DistrictET);
        ArrayAdapter<String> catagoryArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, catagory);
        ArrayAdapter<String> monthArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, month);
        catagoryArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        monthArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        catagorySpinner.setAdapter(catagoryArrayAdapter);
        monthSpinner.setAdapter(monthArrayAdapter);
        setItemSelectedListener();
        setOnClickListener();
        return view;
    }

    private void setOnClickListener() {
        toolbarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(getContext(),"ok",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setItemSelectedListener() {
        catagorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catagoryType=catagory[position].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthName=month[position].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
