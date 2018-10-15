package com.example.shipon.toletbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.shipon.toletbd.activity.Main2Activity.USER_PHONE;

public class UpdateOwnerActivity extends AppCompatActivity {
    EditText ownerNameET,ownerOccupationET,ownerAddressET,ownerPasswordET;
    TextView ownerContactET;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference oRef = myDatabaseRef.child("Owners");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_owner);
        ownerNameET=findViewById(R.id.OwnerNameETU);
        ownerContactET=findViewById(R.id.OwnerContactETU);
        ownerOccupationET=findViewById(R.id.OwnerOccupationETU);
        ownerAddressET=findViewById(R.id.OwnerAddressETU);
        setAll();

    }

    public void UpdateOwnerClick(View view) {
        DatabaseReference U=oRef.child(USER_PHONE);
        if(checkAll()){
            U.child("name").setValue(ownerNameET.getText().toString());
            U.child("occupation").setValue(ownerOccupationET.getText().toString());
            U.child("address").setValue(ownerAddressET.getText().toString());
            Toast.makeText(getApplicationContext(),"Succesfully Updated",Toast.LENGTH_SHORT).show();
            this.finish();
        }

    }
    private void setAll() {
        oRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp:dataSnapshot.getChildren()){
                    if(dsp.child("contact").getValue().toString().equals(USER_PHONE)){
                        ownerNameET.setText(dsp.child("name").getValue().toString());
                        ownerOccupationET.setText(dsp.child("occupation").getValue().toString());
                        ownerContactET.setText(dsp.child("contact").getValue().toString());

                        ownerAddressET.setText(dsp.child("address").getValue().toString());



                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private boolean checkAll() {
        boolean flag=true;
        if(ownerNameET.getText().toString().equals("")){
            ownerNameET.setError("This field can,t be empty");
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


        return flag;
    }
}
