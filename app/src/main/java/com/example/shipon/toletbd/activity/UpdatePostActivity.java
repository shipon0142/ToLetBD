package com.example.shipon.toletbd.activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UpdatePostActivity extends AppCompatActivity {
    String catagory[] = {"---Select Category---", "All", "Family", "Sublet", "Boys Only", "Girls Only", "Boys Hostel", "Girls Hostel"};
    String month[] = {"---Select Month---", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private static final String[] district = new String[]{
            "Dhaka", "Khulna", "Chittagong", "Sylhet", "Barisal", "Rajshai"

    };
    private static final String[] dhakaArea = new String[]{

            "Mirpur", "Mohammadpur", "Sher-E-Bangla Nagar", "Pallabi", "Adabor", "Kafrul", "Dhaka Cantonment"
            , "Tejgaon", "Gulshan", "Rampura", "Banani", "Khilkhet", "Badda", "Uttara", "Uttarkhan", "Dakkshinkhan"
            , "Paltan", "Sabujbagh", "Jatrabari", "Motijheel", "Dhaka Kotwali", "Sutrapur", "Bangsal", "Wari"
            , "Ramna", "Gendaria", "Lalbagh", "Hazaribagh", "Dhanmondi", "Shahbagh", "New Market"
            , "Khilgaon", "Kamrangirchar"
    };
    Spinner catagorySpinner;
    private ProgressDialog dialog;
    Spinner monthSpinner;
    private Uri imageUri1, imageUri2, imageUri3, imageUri4;
    ArrayList<Uri> uri = new ArrayList<>();
    String catagoryType, monthName;
    EditText costET, detailsET, houseET;
    AutoCompleteTextView districtET, areaET;
    TextView toolbarPost;
    public static String OWNER_NAME = "owner_name";
    public static String CONTACT = "contact";
    public static String DISTRICT = "district";
    public static String AREA = "area";
    public static String HOUSE_NO = "houseno";
    public static String CATAGORY = "catagory";
    public static String MONTH = "month";
    public static String COST = "cost";
    public static String DETAILS = "details";
    public static String IMG_SIZE = "size";
    String K;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference appertmentsRef = myDatabaseRef.child("Appertments");
    StorageReference store = FirebaseStorage.getInstance().getReference();
    DatabaseReference ownerRef = myDatabaseRef.child("Owners");
    DatabaseReference renterRef = myDatabaseRef.child("Renters");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);

        districtET = findViewById(R.id.DistrictETU);
        areaET = findViewById(R.id.AreaETU);
        costET = findViewById(R.id.CostETU);
        detailsET = findViewById(R.id.DetailsETU);
        houseET = findViewById(R.id.HouseETU);
    }

    @Override
    protected void onResume() {
        super.onResume();
        K=getIntent().getStringExtra("apkey");
        setALL(K);
    }

    private void setALL(final String K) {
        appertmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp:dataSnapshot.getChildren()){
                    if(dsp.getKey().equals(K)){
                        districtET.setText(dsp.child(DISTRICT).getValue().toString());
                        areaET.setText(dsp.child(AREA).getValue().toString());
                        houseET.setText(dsp.child(HOUSE_NO).getValue().toString());
                        costET.setText(dsp.child(COST).getValue().toString());
                        detailsET.setText(dsp.child(DETAILS).getValue().toString());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void UpdatePost(View view) {
       if(checkAll()){
           update();
       }


    }

    private void update() {
        DatabaseReference newRef=appertmentsRef.child(K);
        newRef.child(DISTRICT).setValue(districtET.getText().toString());
        newRef.child(AREA).setValue(areaET.getText().toString());
        newRef.child(HOUSE_NO).setValue(houseET.getText().toString());
        newRef.child(COST).setValue(costET.getText().toString());
        newRef.child(DETAILS).setValue(detailsET.getText().toString());
        Toast.makeText(this,"Updated Succesful",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    public void UpadatePostBackClick(View view) {
        super.onBackPressed();
    }
    private boolean checkAll() {
        boolean flag = true;
        if (districtET.getText().toString().equals("")) {
            districtET.setError("This field can,t be empty");
            flag = false;
        }
        if (areaET.getText().toString().equals("")) {
            areaET.setError("This field can,t be empty");
            flag = false;
        }
        if (houseET.getText().toString().equals("")) {
            houseET.setError("This field can,t be empty");
            flag = false;
        }
        if (costET.getText().toString().equals("")) {
            costET.setError("This field can,t be empty");
            flag = false;
        }

        if (detailsET.getText().toString().equals("")) {
            detailsET.setError("This field can,t be empty");
            flag = false;
        }
        return flag;

    }
}
