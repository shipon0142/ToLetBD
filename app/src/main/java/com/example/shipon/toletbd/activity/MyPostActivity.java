package com.example.shipon.toletbd.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.adopter.ApartmentAdopter;
import com.example.shipon.toletbd.adopter.MyPostAdopter;
import com.example.shipon.toletbd.adopter.RequestAdopter;
import com.example.shipon.toletbd.models.Apartment;
import com.example.shipon.toletbd.models.RenterRequest;
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

import java.util.ArrayList;

import static com.example.shipon.toletbd.activity.Main2Activity.USER;
import static com.example.shipon.toletbd.activity.Main2Activity.USER_PHONE;
import static com.example.shipon.toletbd.activity.Main2Activity.apartment1;
import static com.example.shipon.toletbd.activity.Main2Activity.apartments;

public class MyPostActivity extends AppCompatActivity {
    String name,phone,request,requestKey;

    RecyclerView rv;
    TextView rentOrPostTitle;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference requestRef = myDatabaseRef.child("request");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
            rentOrPostTitle=findViewById(R.id.RentOrPostTitle);
        rv = findViewById(R.id.PrecyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAll();
    }

    private void setAll() {
        ArrayList<Apartment> apart = new ArrayList<>();
        if (USER.equals("owner")) {
            rentOrPostTitle.setText("My post");
            for (int i = 0; i < apartments.size(); i++) {
                if (apartments.get(i).getContactOwner().equals(USER_PHONE)) {
                    apart.add(apartments.get(i));
                    // break;
                }
                if (i == apartments.size() - 1) {
                    MyPostAdopter myAdapter = new MyPostAdopter(MyPostActivity.this, apart);
                    LinearLayoutManager llm = new LinearLayoutManager(MyPostActivity.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    rv.setLayoutManager(llm);
                    rv.setAdapter(myAdapter);
                }
            }
        } else {
            rentOrPostTitle.setText("My rent");
            final ArrayList<Apartment> apartments2=new ArrayList<>();
            for (int i = 0; i < apartments.size(); i++) {
                final int finalI = i;
                requestRef.child(apartments.get(i).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp: dataSnapshot.getChildren()) {
                            requestKey=dsp.getKey();
                            name=dsp.child("rentername").getValue().toString();
                            phone=dsp.child("renterphone").getValue().toString();
                            request=dsp.child("request").getValue().toString();
                           if(phone.equals(USER_PHONE) && request.equals("accept")){
                               apartments2.add(apartments.get(finalI));
                           }

                        }
                        MyPostAdopter myAdapter = new MyPostAdopter(MyPostActivity.this, apartments2);
                        LinearLayoutManager llm = new LinearLayoutManager(MyPostActivity.this);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        rv.setLayoutManager(llm);
                        rv.setAdapter(myAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        }
    }

    public void myPostBackArrowClick(View view) {
        super.onBackPressed();
    }
}

