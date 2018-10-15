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
import com.example.shipon.toletbd.activity.LoginActivity;
import com.example.shipon.toletbd.activity.Main2Activity;
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
import static com.example.shipon.toletbd.activity.Main2Activity.ownersaveLogin;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerSigninFragment extends Fragment {

    TextView ownerSigninTV;
    EditText ownerPhonenoET,ownerPasswordET;
    CheckBox ownerRememberPasswordCB;
    String Phoneno,Name;
    String Password;
    private SharedPreferences.Editor loginPrefsEditor;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference appertmentsRef = myDatabaseRef.child("Appertments");
    DatabaseReference ownerRef = myDatabaseRef.child("Owners");
    DatabaseReference renterRef = myDatabaseRef.child("Renters");
    public OwnerSigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_owner_signin, container, false);
        ownerSigninTV=view.findViewById(R.id.OwnerSigninTV);
        ownerPhonenoET=view.findViewById(R.id.OwnerPhoneNoET);
        ownerPasswordET=view.findViewById(R.id.OwnerPasswordET);
        ownerRememberPasswordCB=view.findViewById(R.id.OwnerRememberPasswordCB);
        loginPreferences = getContext().getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();


        ownersaveLogin = loginPreferences.getBoolean("saveOwnerLogin", false);
        if (ownersaveLogin == true) {
            ownerPhonenoET.setText(loginPreferences.getString("Ownerusername", ""));
            ownerPasswordET.setText(loginPreferences.getString("Ownerpassword", ""));
            ownerRememberPasswordCB.setChecked(true);


        }
        setOnClickListener();
    return view;
    }
    private void setOnClickListener() {
        ownerSigninTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean flag = false;
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                            Phoneno = (String) dsp.child("contact").getValue();
                           Name = (String) dsp.child("name").getValue();
                            Password = (String) dsp.child("password").getValue();
                            if (ownerPhonenoET.getText().toString().equals(Phoneno) && ownerPasswordET.getText().toString().equals(Password)) {
                                flag = true;
                                break;

                            }
                        }
                        if(flag==true){
                            USER="owner";
                            USER_PHONE=Phoneno;
                            USER_NAME=Name;

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
        if (ownerRememberPasswordCB.isChecked()) {
            loginPrefsEditor.putBoolean("saveOwnerLogin", true);
            loginPrefsEditor.putString("Ownerusername", ownerPhonenoET.getText().toString());
            loginPrefsEditor.putString("Ownerpassword", ownerPasswordET.getText().toString());

            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }
    }


}
