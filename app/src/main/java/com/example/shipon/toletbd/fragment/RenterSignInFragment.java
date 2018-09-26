package com.example.shipon.toletbd.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.activity.Main2Activity;
import com.example.shipon.toletbd.activity.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.shipon.toletbd.activity.Main2Activity.USER;
import static com.example.shipon.toletbd.activity.Main2Activity.USER_PHONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RenterSignInFragment extends Fragment {
TextView renterSigninTV;
EditText renterPhonenoET,renterPasswordET;
CheckBox renterRememberPasswordCB;
    String Phoneno;
    String Password;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference appertmentsRef = myDatabaseRef.child("Appertments");
    DatabaseReference ownerRef = myDatabaseRef.child("Owners");
    DatabaseReference renterRef = myDatabaseRef.child("Renters");
    public RenterSignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_renter_sign_in, container, false);
       renterSigninTV=view.findViewById(R.id.RenterSigninTV);
       renterPhonenoET=view.findViewById(R.id.RenterPhoneNoET);
       renterPasswordET=view.findViewById(R.id.RenterPasswordET);
       renterRememberPasswordCB=view.findViewById(R.id.RenterRememberPasswordCB);
      setOnClickListener();
       return view;
    }

    private void setOnClickListener() {
        renterSigninTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renterRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean flag = false;
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                   Phoneno = (String) dsp.child("contact").getValue();
                            Password = (String) dsp.child("password").getValue();
                            if (renterPhonenoET.getText().toString().equals(Phoneno) && renterPasswordET.getText().toString().equals(Password)) {
                                flag = true;

                            }
                        }
                        if(flag==true){
                            USER="owner";
                            USER_PHONE=Phoneno;

                            Toast.makeText(getContext(),"Succesful",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),Main2Activity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else {
                            Toast.makeText(getContext(),"not",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

}
