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

public class OwnerProfileActivity extends AppCompatActivity {
    TextView pnameTV,poccupationTV,pcontactTV,paddressTV,callNowTV,profileTitleTV,editTV;
    String P;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference oRef = myDatabaseRef.child("Owners");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);
        pnameTV=findViewById(R.id.OnameTV);
        poccupationTV=findViewById(R.id.OoccupationTV);
        pcontactTV=findViewById(R.id.OcontactTV);
        paddressTV=findViewById(R.id.OaddressTV);
        callNowTV=findViewById(R.id.OCallNowTV);
        profileTitleTV=findViewById(R.id.OProfileTitleTV);
        editTV=findViewById(R.id.EditTV);
        P=getIntent().getStringExtra("phn");
        if(P.equals(USER_PHONE)){
            editTV.setVisibility(View.VISIBLE);
            callNowTV.setVisibility(View.GONE);
            profileTitleTV.setText("Profile");
        }
        else {
           editTV.setVisibility(View.GONE);
            callNowTV.setVisibility(View.VISIBLE);

        }
        callNowTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", P, null));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(P.equals(USER_PHONE)){
            getMenuInflater().inflate(R.menu.update, menu);
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.UpdateId:
                Intent i=new Intent(this,UpdateOwnerActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setAll() {
        oRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp:dataSnapshot.getChildren()){
                    if(dsp.child("contact").getValue().toString().equals(P)){
                        pnameTV.setText(dsp.child("name").getValue().toString());
                        poccupationTV.setText(dsp.child("occupation").getValue().toString());
                        pcontactTV.setText(dsp.child("contact").getValue().toString());

                        paddressTV.setText(dsp.child("address").getValue().toString());

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



    public void OmyProfileBackArrowClick(View view) {
        super.onBackPressed();
    }

    public void Edit(View view) {
        Intent i=new Intent(this,UpdateOwnerActivity.class);
        startActivity(i);
    }
}
