package com.example.shipon.toletbd.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.shipon.toletbd.activity.AccountActivity;
import com.example.shipon.toletbd.activity.Main2Activity;
import com.example.shipon.toletbd.activity.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shipon.toletbd.activity.AccountActivity.check;
import static com.example.shipon.toletbd.activity.Main2Activity.USER;
import static com.example.shipon.toletbd.activity.Main2Activity.USER_NAME;
import static com.example.shipon.toletbd.activity.Main2Activity.USER_PHONE;
import static com.example.shipon.toletbd.activity.Main2Activity.loginPreferences;
import static com.example.shipon.toletbd.activity.Main2Activity.rentersaveLogin;


/**
 * A simple {@link Fragment} subclass.
 */
public class RenterSignInFragment extends Fragment {
TextView renterSigninTV;
EditText renterPhonenoET,renterPasswordET;
CheckBox renterRememberPasswordCB;
    String Phoneno,Name;
    String Password;
    private SharedPreferences.Editor loginPrefsEditor;
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
        loginPreferences = getActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();


        rentersaveLogin = loginPreferences.getBoolean("saveRenterLogin", false);
        if (rentersaveLogin == true) {
            renterPhonenoET.setText(loginPreferences.getString("Renterusername", ""));
            renterPasswordET.setText(loginPreferences.getString("Renterpassword", ""));
            renterRememberPasswordCB.setChecked(true);



        }
      setOnClickListener();
       return view;
    }

    private void setOnClickListener() {
        renterSigninTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                renterRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean flag = false;
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Name = (String) dsp.child("name").getValue();
                            Phoneno = (String) dsp.child("contact").getValue();
                            Password = (String) dsp.child("password").getValue();
                            if (renterPhonenoET.getText().toString().equals(Phoneno) && renterPasswordET.getText().toString().equals(Password)) {
                                flag = true;

                            }
                        }
                        if(flag==true){
                            USER="renter";
                            USER_PHONE=Phoneno;
                            USER_NAME=Name;
                            save();
                            Toast.makeText(getContext(),"Succesful",Toast.LENGTH_SHORT).show();
                            if(check==true){
                                Intent intent = new Intent(getActivity(),AccountActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(getActivity(),Main2Activity.class);
                                startActivity(intent);
                            }
                            check=false;
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

    private void save() {
        if (renterRememberPasswordCB.isChecked()) {
            loginPrefsEditor.putBoolean("saveRenterLogin", true);
            loginPrefsEditor.putString("Renterusername", renterPhonenoET.getText().toString());
            loginPrefsEditor.putString("Renterpassword", renterPasswordET.getText().toString());

            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }
    }

}
