package com.example.shipon.toletbd.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.activity.VerificationActivity;
import com.example.shipon.toletbd.database.FirebaseClient;
import com.example.shipon.toletbd.models.Owner;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import static com.example.shipon.toletbd.activity.RegisterActivity.SIGNUPAS;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerRegFragment extends Fragment {
EditText ownerNameET,ownerContactET,ownerOccupationET,ownerAddressET,ownerPasswordET,ownerConfirmPasswordET;
TextView ownerSignupTV;
    private ProgressDialog progressD;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallBacks;
    private String phoneVerificationId = null;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    String ownerName,ownerContact,ownerOccupation,ownerAddress,ownerPassword;
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
        progressD = new ProgressDialog(getContext());
        progressD.setMessage("Sending verification code...");
        progressD.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        setClickListener();
        return view;
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
                data.putString("ownerphone", ownerContact);
                data.putString("ownername", ownerName);
                data.putString("owneroccupation", ownerOccupation);
                data.putString("owneraddress", ownerAddress);
                data.putString("ownerpassword", ownerPassword);
                SIGNUPAS="owner";
                Intent intent =new Intent(getActivity(), VerificationActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }

        };


    }
    private void setClickListener() {
        ownerSignupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAll()){
                  /*  Owner owner=new Owner(ownerNameET.getText().toString(),
                                          ownerContactET.getText().toString(),
                                          ownerOccupationET.getText().toString(),
                                          ownerAddressET.getText().toString(),
                                          ownerPasswordET.getText().toString());
                    FirebaseClient client=new FirebaseClient();
                    client.addOwner(owner);
                    Toast.makeText(getContext(),"Registration succesfull",Toast.LENGTH_SHORT).show();*/

                    progressD.show();
                    startPhoneNumberVerification(ownerContact);
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
        if(flag==true){
            ownerName=ownerNameET.getText().toString();
                    ownerContact=ownerContactET.getText().toString();
                            ownerOccupation=ownerOccupationET.getText().toString();
                                    ownerAddress=ownerAddressET.getText().toString();
                                            ownerPassword=ownerPasswordET.getText().toString();
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
