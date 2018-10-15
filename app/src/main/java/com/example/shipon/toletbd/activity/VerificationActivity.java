package com.example.shipon.toletbd.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.database.FirebaseClient;
import com.example.shipon.toletbd.models.Owner;
import com.example.shipon.toletbd.models.Renter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.EventObject;

import static com.example.shipon.toletbd.activity.RegisterActivity.SIGNUPAS;

public class VerificationActivity extends AppCompatActivity {
    private Button verify, cancel;
    private EditText editphone;
    private String phoneVerificationId;
    private PhoneAuthCredential credential;
    private String phoneNumber;
    private ProgressDialog progressDialog;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallBacks;
    private FirebaseAuth mAuth;
    String name,contact,occupation,status,income,password;
    String ownerName,ownerContact,ownerOccupation,ownerAddress,ownerPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        verify = findViewById(R.id.VerifyCode);
        cancel = findViewById(R.id.ccancel);
        editphone = findViewById(R.id.EditCode);
        mAuth = FirebaseAuth.getInstance();
        if(SIGNUPAS.equals("renter")) {
            phoneVerificationId = getIntent().getExtras().getString("phoneVerificationId");
            phoneNumber = getIntent().getExtras().getString("phoneNumber");
            name = getIntent().getExtras().getString("name");
            contact = getIntent().getExtras().getString("phoneNumber");
            occupation = getIntent().getExtras().getString("occupation");
            status = getIntent().getExtras().getString("status");
            income = getIntent().getExtras().getString("income");
            password = getIntent().getExtras().getString("password");
        }
        else {
            phoneVerificationId = getIntent().getExtras().getString("phoneVerificationId");
            ownerContact = getIntent().getExtras().getString("ownerphone");
            ownerName = getIntent().getExtras().getString("ownername");
            ownerOccupation = getIntent().getExtras().getString("owneroccupation");
            ownerAddress = getIntent().getExtras().getString("owneraddress");
            ownerPassword = getIntent().getExtras().getString("ownerpassword");
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editphone.getText().toString().equals("")) {
                    editphone.setError("Please input verification code");
                    return;
                }


                setUpVerification();
              progressDialog = new ProgressDialog(VerificationActivity.this);
                progressDialog.setMessage("Verifying...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                credential = PhoneAuthProvider.getCredential(phoneVerificationId.toString(), editphone.getText().toString());
                verificationCallBacks.onVerificationCompleted(credential);
                signInWithPhoneAuthCredential(credential);

            }
        });
    }
    private void setUpVerification() {
        // Toast.makeText(getContext(),"asce",Toast.LENGTH_SHORT).show();
        verificationCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                //signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Toast.makeText(getApplicationContext(), "sent", Toast.LENGTH_SHORT).show();
                phoneVerificationId = s;
                mResendToken = forceResendingToken;


            }
        };


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(SIGNUPAS.equals("renter")){
                                Renter renter=new Renter(name,contact,occupation,income,password,status);
                                FirebaseClient client=new FirebaseClient();
                                client.addRenter(renter);
                            }
                            else {
                                Owner owner=new Owner(ownerName,
                                        ownerContact,
                                        ownerOccupation,
                                        ownerAddress,
                                        ownerPassword);
                                FirebaseClient client=new FirebaseClient();
                                client.addOwner(owner);
                            }

                            progressDialog.cancel();
                            Toast.makeText(getApplicationContext(), "Signup Succesful", Toast.LENGTH_SHORT).show();
                            Intent ii=new Intent(VerificationActivity.this,LoginActivity.class);
                            startActivity(ii);
                            finish();

                        } else {
                            // Sign in failed, display a message and update the UI
                            //   Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                //     mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                                Toast.makeText(getApplicationContext(), "Verification code invalid.", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                            //[START_EXCLUDE silent]
                            // Update UI
                            //    updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });


    }
}
