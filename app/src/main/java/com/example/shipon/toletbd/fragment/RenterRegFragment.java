package com.example.shipon.toletbd.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.activity.RegisterActivity;
import com.example.shipon.toletbd.activity.VerificationActivity;
import com.example.shipon.toletbd.database.FirebaseClient;
import com.example.shipon.toletbd.models.Renter;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.example.shipon.toletbd.activity.RegisterActivity.SIGNUPAS;

public class RenterRegFragment extends Fragment {

    EditText renterNameET, renterContactET, renterOccupationET, renterPasswordET, renterConfirmPasswordET, renterIncomeET;
    RadioButton marreidRG, bachelorRG;
    TextView renterSignupTV;
    private ProgressDialog progressD;
    private FirebaseAuth mAuth;
String name,contact,occupation,status,income,password;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallBacks;
    private String phoneVerificationId = null;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    public RenterRegFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renter_reg, container, false);
        renterNameET = view.findViewById(R.id.RenterNameET);
        renterContactET = view.findViewById(R.id.RenterContactET);
        renterOccupationET = view.findViewById(R.id.RenterOccupationET);
        renterIncomeET = view.findViewById(R.id.RenterMonthlyIncome);
        renterPasswordET = view.findViewById(R.id.RenterPasswordET);
        renterConfirmPasswordET = view.findViewById(R.id.RenterConfirmPasswordET);
        marreidRG = view.findViewById(R.id.RenterMarriedRG);
        bachelorRG = view.findViewById(R.id.RenterBachelorRG);
        renterSignupTV=view.findViewById(R.id.RenterRegSignUpTV);
        progressD = new ProgressDialog(getContext());
        progressD.setMessage("Sending verification code...");
        progressD.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        setClickListener();
        return view;
    }

    private void setClickListener() {
        renterSignupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAll()){
                    progressD.show();
                    startPhoneNumberVerification(contact);

                }
            }
        });
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        setUpVerification();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                verificationCallBacks);

        //mVerificationInProgress = true;
    }
    private void setUpVerification() {
        // Toast.makeText(getContext(),"asce",Toast.LENGTH_SHORT).show();
        verificationCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getContext(), "Verification code not sent.", Toast.LENGTH_SHORT).show();
                progressD.cancel();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Toast.makeText(getContext(), "Verification code sent.", Toast.LENGTH_SHORT).show();
                progressD.cancel();
                phoneVerificationId = s;
                mResendToken = forceResendingToken;


                Bundle data = new Bundle();

                data.putString("phoneVerificationId", phoneVerificationId);
                data.putString("phoneNumber", contact);
                data.putString("name", name);
                data.putString("occupation", occupation);
                data.putString("status", status);
                data.putString("income", income);
                data.putString("password", password);
                SIGNUPAS="renter";
                Intent intent =new Intent(getActivity(), VerificationActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }

        };


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
    private boolean checkAll(){
        boolean flag=true;
        if(renterNameET.getText().toString().equals("")){
            renterNameET.setError("This field can,t be empty");
            flag=false;
        }
        if(renterContactET.getText().toString().equals("")){
            renterContactET.setError("This field can,t be empty");
            flag=false;
        }
        if(renterOccupationET.getText().toString().equals("")){
            renterOccupationET.setError("This field can,t be empty");
            flag=false;
        }
        else if(!checkPhonenumber(renterContactET.getText().toString())){
            renterContactET.setError("Phone number not correct");
            flag=false;
        }

        if(renterContactET.getText().toString().equals("")){
            renterContactET.setError("This field can,t be empty");
            flag=false;
        }
        if(renterIncomeET.getText().toString().equals("")){
            renterIncomeET.setError("This field can,t be empty");
            flag=false;
        }
        if(renterPasswordET.getText().toString().equals("")){
            renterPasswordET.setError("This field can,t be empty");
            flag=false;
        }if(renterConfirmPasswordET.getText().toString().equals("")){
            renterConfirmPasswordET.setError("This field can,t be empty");
            flag=false;
        }
        if(renterPasswordET.getText().toString().length()<6){
            renterPasswordET.setError("Password at least six charecter.");
            flag=false;
        }
        if(marreidRG.isChecked()==false && bachelorRG.isChecked()==false && flag==true){
            renterConfirmPasswordET.setError("Please check your Status");
            flag=false;
        }
        if(flag==true){
            name=renterNameET.getText().toString();
            contact=renterContactET.getText().toString();
            occupation=renterOccupationET.getText().toString();
            if(marreidRG.isChecked())status="married";
            else status="bachelor";
            income=renterIncomeET.getText().toString();
            password=renterPasswordET.getText().toString();

        }

        return flag;
    }

}

