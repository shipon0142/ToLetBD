package com.example.shipon.toletbd.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shipon.toletbd.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static com.example.shipon.toletbd.activity.Main2Activity.apartment1;

public class DetailsActivity extends AppCompatActivity {
ImageView mainImage,smallImg1,smallImg2,smallImg3,smallImg4;
TextView addressTV,monthTV,catagoryTV,costTV,ownerNameTV,contactTV,descriptionTV;
private ProgressBar progressBar1;
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
        costTV.setText(apartment1.getCostHome());
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
}
