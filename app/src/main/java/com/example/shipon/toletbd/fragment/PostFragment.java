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
import com.example.shipon.toletbd.database.FirebaseClient;
import com.example.shipon.toletbd.models.Apartment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment{

    String catagory[]= {"---Select Category---","All","Family","Sublet","Boys Only","Girls Only","Boys Hostel","Girls Hostel"};
    String month[]= {"---Select Month---","January","February","March","April","May","June","July","August","September","October","November","December"};

    Spinner catagorySpinner;
    Spinner monthSpinner;
    String catagoryType,monthName;
    EditText districtET,areaET,costET,detailsET,houseET;
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
        houseET=view.findViewById(R.id.HouseET);
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
                if(checkAll()){
                    String district=districtET.getText().toString();
                    String area=areaET.getText().toString();
                    String houseNo=houseET.getText().toString();
                    String catagory=catagoryType.toString();
                    String month=monthName.toString();
                    String cost=costET.getText().toString();
                    String details=detailsET.getText().toString();
                    Apartment newApartment=new Apartment("Shipon Sarder","01942040142",
                            district,area,catagory,month,details,houseNo,cost);
                    FirebaseClient client=new FirebaseClient();
                    client.addApartment(newApartment);
                    Toast.makeText(getContext(),"All ok",Toast.LENGTH_SHORT).show();
                }
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
    private boolean checkAll(){
        boolean flag=true;
        if(detailsET.getText().toString().equals("")){
            detailsET.setError("This field can,t be empty");
            flag=false;
        }
        if(areaET.getText().toString().equals("")){
            areaET.setError("This field can,t be empty");
            flag=false;
        }
        if(houseET.getText().toString().equals("")){
            houseET.setError("This field can,t be empty");
            flag=false;
        }
        if(costET.getText().toString().equals("")){
            costET.setError("This field can,t be empty");
            flag=false;
        }
        if(detailsET.getText().toString().equals("")){
            detailsET.setError("This field can,t be empty");
            flag=false;
        }
        if(catagoryType.toString().contains("Select")){
            // Toast.makeText(getContext(),"Select catagory field",Toast.LENGTH_SHORT).show();
            flag=false;
        }
        if(monthName.toString().equals("Select")){
            flag=false;
            // detailsET.setError("This field can,t be empty");
        }
        if(flag==false){
            Toast.makeText(getContext(),"Select fill all the field",Toast.LENGTH_SHORT).show();
        }
        return flag;

    }

}
