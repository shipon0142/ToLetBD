package com.example.shipon.toletbd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static com.example.shipon.toletbd.activity.Main2Activity.USER_NAME;
import static com.example.shipon.toletbd.activity.Main2Activity.USER_PHONE;
import static com.example.shipon.toletbd.activity.Main2Activity.apartment1;

public class DetailsActivity extends AppCompatActivity {
ImageView mainImage,smallImg1,smallImg2,smallImg3,smallImg4;
TextView addressTV,monthTV,catagoryTV,costTV,ownerNameTV,contactTV,descriptionTV;
private ProgressBar progressBar1;
AlertDialog alertDialog;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference requestRef=myDatabaseRef.child("request");
    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String position=getIntent().getStringExtra("position");
        mainImage=findViewById(R.id.MainImageIV);
        smallImg1=findViewById(R.id.SmallImg1);
        smallImg2=findViewById(R.id.SmallImg2);
        smallImg3=findViewById(R.id.SmallImg3);
        smallImg4=findViewById(R.id.SmallImg4);

        addressTV=findViewById(R.id.DAddressTV);
        monthTV=findViewById(R.id.DMonthTV);
        catagoryTV=findViewById(R.id.DCatagoryTV);
        costTV=findViewById(R.id.DCostTV);
        ownerNameTV=findViewById(R.id.DOwnerNameTV);
        contactTV=findViewById(R.id.DContactTV);
        descriptionTV=findViewById(R.id.DDescriptionTV);
        progressBar1=findViewById(R.id.DProgressBar);
        setAll();
    }

    private void setAll() {
        addressTV.setText(apartment1.getHouseNo()+","+apartment1.getAreaOwner()+","+apartment1.getDistrictOwner());
        monthTV.setText(apartment1.getFromMonth());
        catagoryTV.setText(apartment1.getHomeCatagory());
        costTV.setText("Cost: "+apartment1.getCostHome()+" BDT  ");
        ownerNameTV.setText("Owner name : "+apartment1.getNameOwner());
        contactTV.setText("Contact : "+apartment1.getContactOwner());
        descriptionTV.setText(apartment1.getDetailsOwnerHome());
        progressBar1.setVisibility(View.VISIBLE);
        StorageReference storageRef1 = firebaseStorage.child("Photos").child(apartment1.getKey()).child("img1");
        Task<Uri> uri1 = storageRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString())
                smallImg1.setAlpha((float) 1);
                smallImg2.setAlpha((float) .3);
                smallImg3.setAlpha((float) .3);
                smallImg4.setAlpha((float) .3);
                Picasso.with(getApplicationContext()).load(uri).into(mainImage);
                Picasso.with(getApplicationContext()).load(uri).into(smallImg1);
             progressBar1.setVisibility(View.GONE);

            }
        });
        StorageReference storageRef2 = firebaseStorage.child("Photos").child(apartment1.getKey()).child("img2");
        Task<Uri> uri2 = storageRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString())

                Picasso.with(getApplicationContext()).load(uri).into(smallImg2);
                progressBar1.setVisibility(View.GONE);

            }
        });
        StorageReference storageRef3 = firebaseStorage.child("Photos").child(apartment1.getKey()).child("img3");
        Task<Uri> uri3 = storageRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString())

                Picasso.with(getApplicationContext()).load(uri).into(smallImg3);
                progressBar1.setVisibility(View.GONE);

            }
        });
        StorageReference storageRef4 = firebaseStorage.child("Photos").child(apartment1.getKey()).child("img4");
        Task<Uri> uri4 = storageRef4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString())

                Picasso.with(getApplicationContext()).load(uri).into(smallImg4);
                progressBar1.setVisibility(View.GONE);

            }
        });
        smallImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallImg1.setAlpha((float) 1);
                smallImg2.setAlpha((float) .3);
                smallImg3.setAlpha((float) .3);
                smallImg4.setAlpha((float) .3);
                progressBar1.setVisibility(View.VISIBLE);
                StorageReference storageRef = firebaseStorage.child("Photos").child(apartment1.getKey()).child("img1");
                Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //   Log.e("URI", uri.toString())
                        Picasso.with(getApplicationContext()).load(uri).into(mainImage);
                        progressBar1.setVisibility(View.GONE);

                    }
                });
            }
        });
        smallImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallImg1.setAlpha((float) .3);
                smallImg2.setAlpha((float) 1);
                smallImg3.setAlpha((float) .3);
                smallImg4.setAlpha((float) .3);

                progressBar1.setVisibility(View.VISIBLE);
                StorageReference storageRef = firebaseStorage.child("Photos").child(apartment1.getKey()).child("img2");
                Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //   Log.e("URI", uri.toString())
                        Picasso.with(getApplicationContext()).load(uri).into(mainImage);
                        progressBar1.setVisibility(View.GONE);

                    }
                });
            }
        });
        smallImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallImg1.setAlpha((float) .3);
                smallImg2.setAlpha((float) .3);
                smallImg3.setAlpha((float) 1);
                smallImg4.setAlpha((float) .3);
                progressBar1.setVisibility(View.VISIBLE);
                StorageReference storageRef = firebaseStorage.child("Photos").child(apartment1.getKey()).child("img3");
                Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //   Log.e("URI", uri.toString())
                        Picasso.with(getApplicationContext()).load(uri).into(mainImage);
                        progressBar1.setVisibility(View.GONE);

                    }
                });
            }
        });
        smallImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallImg1.setAlpha((float) .3);
                smallImg2.setAlpha((float) .3);
                smallImg3.setAlpha((float) .3);
                smallImg4.setAlpha((float) 1);
                progressBar1.setVisibility(View.VISIBLE);
                StorageReference storageRef = firebaseStorage.child("Photos").child(apartment1.getKey()).child("img4");
                Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //   Log.e("URI", uri.toString())
                        Picasso.with(getApplicationContext()).load(uri).into(mainImage);
                        progressBar1.setVisibility(View.GONE);

                    }
                });
            }
        });
    }

    public void clickRequest(View view) {
       alertDialog= new AlertDialog.Builder(this)
                .setMessage("Are you sure to sent request?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sent", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Toast.makeText(MainActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                        sentRequest();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void sentRequest() {

        requestRef.child(apartment1.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ii= (int) dataSnapshot.getChildrenCount();
                requestRef.child(apartment1.getKey()).child("r"+(ii+1)).child("rentername").setValue(USER_NAME);
                requestRef.child(apartment1.getKey()).child("r"+(ii+1)).child("renterphone").setValue(USER_PHONE);
                requestRef.child(apartment1.getKey()).child("r"+(ii+1)).child("request").setValue("pending").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(DetailsActivity.this,"Request sent Succesful",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void clickDetailsBackArrow(View view) {
        super.onBackPressed();
    }

    public void ClickCallDetails(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", apartment1.getContactOwner(), null));
        startActivity(intent);
    }
}
