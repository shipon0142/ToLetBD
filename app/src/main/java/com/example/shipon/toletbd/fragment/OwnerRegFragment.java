package com.example.shipon.toletbd.fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.database.FirebaseClient;
import com.example.shipon.toletbd.models.Owner;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerRegFragment extends Fragment {
TextInputEditText ownerNameET,ownerContactET,ownerOccupationET,ownerAddressET,ownerPasswordET,ownerConfirmPasswordET;
TextView ownerSignupTV;

    public OwnerRegFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_owner_reg, container, false);
        ownerNameET=view.findViewById(R.id.OwnerNameET);
        ownerContactET=view.findViewById(R.id.OwnerContactET);
        ownerOccupationET=view.findViewById(R.id.OwnerOccupationET);
        ownerAddressET=view.findViewById(R.id.OwnerAddressET);
        ownerPasswordET=view.findViewById(R.id.OwnerPasswordET);
        ownerConfirmPasswordET=view.findViewById(R.id.OwnerConfirmPasswordET);
        ownerSignupTV=view.findViewById(R.id.OwnerRegSignUpTV);
        setClickListener();
        return view;
    }

    private void setClickListener() {
        ownerSignupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAll()){
                    Owner owner=new Owner(ownerNameET.getText().toString(),
                                          ownerContactET.getText().toString(),
                                          ownerOccupationET.getText().toString(),
                                          ownerAddressET.getText().toString(),
                                          ownerPasswordET.getText().toString());
                    FirebaseClient client=new FirebaseClient();
                    client.addOwner(owner);
                    Toast.makeText(getContext(),"Registration succesfull",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkAll() {
        boolean flag=true;
        if(ownerNameET.getText().toString().equals("")){
            ownerNameET.setError("This field can,t be empty");
            flag=false;
        }
        if(ownerContactET.getText().toString().equals("")){
            ownerContactET.setError("This field can,t be empty");
            flag=false;
        }
       else if(!checkPhonenumber(ownerContactET.getText().toString())){
            ownerContactET.setError("Phone number not correct");
            flag=false;
        }
        if(ownerOccupationET.getText().toString().equals("")){
            ownerOccupationET.setError("This field can,t be empty");
            flag=false;
        }
        if(ownerAddressET.getText().toString().equals("")){
            ownerAddressET.setError("This field can,t be empty");
            flag=false;
        }
        if(ownerPasswordET.getText().toString().equals("")){
            ownerPasswordET.setError("This field can,t be empty");
            flag=false;
        }if(ownerConfirmPasswordET.getText().toString().equals("")){
            ownerConfirmPasswordET.setError("This field can,t be empty");
            flag=false;
        }
        if(ownerPasswordET.getText().toString().length()<6){
            ownerPasswordET.setError("Password at least six charecter.");
            flag=false;
        }
        if(!ownerPasswordET.getText().toString().equals(ownerConfirmPasswordET.getText().toString())){
            ownerConfirmPasswordET.setError("Password doesn,t match");
            flag=false;
        }


        return flag;
    }
    private boolean checkPhonenumber(String s) {

        String s2 = "+88";
        String sPhoneNumbe = "+8801942040142";
        //String sPhoneNumber = "605-888999A";

        if (s.length() == 11) {
            s2 = s2.concat(s);
            if (s2.contains("+88019")) return true;
            else if (s2.contains("+88015")) return true;
            else if (s2.contains("+88017")) return true;
            else if (s2.contains("+88018")) return true;
            else if (s2.contains("+88016")) return true;
            else return false;
        } else if (s.length() == 14) {
            s2 = s;
            if (s2.contains("+88019")) return true;
            else if (s2.contains("+88015")) return true;
            else if (s2.contains("+88017")) return true;
            else if (s2.contains("+88018")) return true;
            else if (s2.contains("+88016")) return true;
            else return false;
        } else {
            return false;

        }
    }

}
