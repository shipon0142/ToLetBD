package com.example.shipon.toletbd.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.shipon.toletbd.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.shipon.toletbd.activity.Main2Activity.USER_PHONE;

public class RenterProfileActivity extends AppCompatActivity {
TextView pnameTV,poccupationTV,pcontactTV,pincomeTV,pstatusTV,callNowTV,profileTitleTV;
String P;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference rRef = myDatabaseRef.child("Renters");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_profile);
        pnameTV=findViewById(R.id.PnameTV);
        poccupationTV=findViewById(R.id.PoccupationTV);
        pcontactTV=findViewById(R.id.PcontactTV);
        pincomeTV=findViewById(R.id.PincomeTV);
        pstatusTV=findViewById(R.id.PstatusTV);
        callNowTV=findViewById(R.id.CallNowTV);
        profileTitleTV=findViewById(R.id.ProfileTitleTV);

        P=getIntent().getStringExtra("phn");
        if(P.equals(USER_PHONE)){
            callNowTV.setVisibility(View.GONE);
            profileTitleTV.setText("Profile");
        }
        else {
            callNowTV.setVisibility(View.VISIBLE);

        }
        callNowTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", P, null));
                startActivity(intent);
            }
        });
        setAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!P.equals(USER_PHONE)){
            getMenuInflater().inflate(R.menu.update, menu);
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.UpdateId:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setAll() {
        rRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp:dataSnapshot.getChildren()){
                    if(dsp.child("contact").getValue().toString().equals(P)){
                        pnameTV.setText(dsp.child("name").getValue().toString());
                        poccupationTV.setText(dsp.child("occupation").getValue().toString());
                        pcontactTV.setText(dsp.child("contact").getValue().toString());
                        pincomeTV.setText(dsp.child("income").getValue().toString());
                        pstatusTV.setText(dsp.child("status").getValue().toString());
                        if(!P.equals(USER_PHONE)){
                            profileTitleTV.setText(dsp.child("name").getValue().toString());
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void myProfileBackArrowClick(View view) {
        super.onBackPressed();
    }
}
