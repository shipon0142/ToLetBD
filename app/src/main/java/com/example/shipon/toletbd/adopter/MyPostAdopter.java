package com.example.shipon.toletbd.adopter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.MyCallbackClass;
import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.activity.DetailsActivity;
import com.example.shipon.toletbd.activity.MyPostActivity;
import com.example.shipon.toletbd.activity.RenterProfileActivity;
import com.example.shipon.toletbd.activity.UpdateOwnerActivity;
import com.example.shipon.toletbd.activity.UpdatePostActivity;
import com.example.shipon.toletbd.models.Apartment;
import com.example.shipon.toletbd.models.CustomRenterLoginClass;
import com.example.shipon.toletbd.models.RenterRequest;
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

import java.util.ArrayList;

import static com.example.shipon.toletbd.activity.Main2Activity.USER;
import static com.example.shipon.toletbd.activity.Main2Activity.apartment1;
import static com.example.shipon.toletbd.activity.Main2Activity.apartments;


/**
 * Created by Shipon on 9/25/2018.
 */

public class MyPostAdopter extends RecyclerView.Adapter<MyPostAdopter.ViewHolder> {

    boolean flag=false;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference requestRef=myDatabaseRef.child("request");
    String name,phone,request,requestKey,renterPhone;


    Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<Apartment>apartments;
    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();
    //private StorageReference firebaseStorage=FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/khaidai-65b30.appspot.com/o/foodimage%2F110.png?alt=media&token=13e72acb-044a-484c-b021-f9c2cb1c7867");
    // data is passed into the constructor
    public MyPostAdopter(Context context, ArrayList<Apartment> apartments) {
        this.mInflater = LayoutInflater.from(context);
        this.apartments=apartments;
        this.mContext = context;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.mypost_recycler, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // String animal = mData.get(position);

             setAll(position,holder);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return apartments.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mainImage,smallImg1,smallImg2,smallImg3,smallImg4;
        TextView addressTV,monthTV,catagoryTV,costTV,editTV,contactTV,viewRenterProfileTV,rentedByTV;
        RecyclerView requestRecyclerView;
        private ProgressBar progressBar1;

        ViewHolder(View itemView) {
            super(itemView);
            mainImage=itemView.findViewById(R.id.MainImageIV);
            smallImg1=itemView.findViewById(R.id.SmallImg1);
            smallImg2=itemView.findViewById(R.id.SmallImg2);
            smallImg3=itemView.findViewById(R.id.SmallImg3);
            smallImg4=itemView.findViewById(R.id.SmallImg4);

            addressTV=itemView.findViewById(R.id.DAddressTV);
            monthTV=itemView.findViewById(R.id.DMonthTV);
            catagoryTV=itemView.findViewById(R.id.DCatagoryTV);
            costTV=itemView.findViewById(R.id.DCostTV);
            rentedByTV=itemView.findViewById(R.id.RentedByTV);
            editTV=itemView.findViewById(R.id.DEditTV);
            viewRenterProfileTV=itemView.findViewById(R.id.ViewRenterProfileTV);

            progressBar1=itemView.findViewById(R.id.DProgressBar);
            requestRecyclerView=itemView.findViewById(R.id.RequestRV);
            rentedByTV.setVisibility(View.GONE);
            requestRecyclerView.setVisibility(View.GONE);

        }


    }


    // convenience method for getting data at click position
    String getItem(int id) {
        return apartments.get(id).getNameOwner();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    private void setAll(final int i, final ViewHolder holder) {
        if(USER.equals("owner")) retriveRequest(i,holder);

        holder.addressTV.setText(apartments.get(i).getHouseNo()+","+apartments.get(i).getAreaOwner()+","+apartments.get(i).getDistrictOwner());
        holder.monthTV.setText(apartments.get(i).getFromMonth());
        holder.catagoryTV.setText(apartments.get(i).getHomeCatagory());
        holder.costTV.setText(apartments.get(i).getCostHome());
        holder.progressBar1.setVisibility(View.VISIBLE);
        holder.editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apkey=apartments.get(i).getKey().toString();
                Intent ii=new Intent(mContext, UpdatePostActivity.class);
                ii.putExtra("apkey",apkey);
                mContext.startActivity(ii);
            }
        });
        holder.viewRenterProfileTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, RenterProfileActivity.class);
                i.putExtra("phn", phone);
                mContext.startActivity(i);
            }
        });
        StorageReference storageRef1 = firebaseStorage.child("Photos").child(apartments.get(i).getKey()).child("img1");
        Task<Uri> uri1 = storageRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString())
                holder.smallImg1.setAlpha((float) 1);
                holder.smallImg2.setAlpha((float) .3);
                holder.smallImg3.setAlpha((float) .3);
                holder.smallImg4.setAlpha((float) .3);
                Picasso.with(mContext).load(uri).into(holder.mainImage);
                Picasso.with(mContext).load(uri).into(holder.smallImg1);
                holder.progressBar1.setVisibility(View.GONE);

            }
        });
        StorageReference storageRef2 = firebaseStorage.child("Photos").child(apartments.get(i).getKey()).child("img2");
        Task<Uri> uri2 = storageRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString())

                Picasso.with(mContext).load(uri).into(holder.smallImg2);
                holder.progressBar1.setVisibility(View.GONE);

            }
        });
        StorageReference storageRef3 = firebaseStorage.child("Photos").child(apartments.get(i).getKey()).child("img3");
        Task<Uri> uri3 = storageRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString())

                Picasso.with(mContext).load(uri).into(holder.smallImg3);
                holder.progressBar1.setVisibility(View.GONE);

            }
        });
        StorageReference storageRef4 = firebaseStorage.child("Photos").child(apartments.get(i).getKey()).child("img4");
        Task<Uri> uri4 = storageRef4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString())

                Picasso.with(mContext).load(uri).into(holder.smallImg4);
                holder.progressBar1.setVisibility(View.GONE);

            }
        });
        holder.smallImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.smallImg1.setAlpha((float) 1);
                holder.smallImg2.setAlpha((float) .3);
                holder.smallImg3.setAlpha((float) .3);
                holder. smallImg4.setAlpha((float) .3);
                holder. progressBar1.setVisibility(View.VISIBLE);
                StorageReference storageRef = firebaseStorage.child("Photos").child(apartments.get(i).getKey()).child("img1");
                Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //   Log.e("URI", uri.toString())
                        Picasso.with(mContext).load(uri).into(holder.mainImage);
                        holder.progressBar1.setVisibility(View.GONE);

                    }
                });
            }
        });
        holder.smallImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.smallImg1.setAlpha((float) .3);
                holder.smallImg2.setAlpha((float) 1);
                holder.smallImg3.setAlpha((float) .3);
                holder.smallImg4.setAlpha((float) .3);

                holder.progressBar1.setVisibility(View.VISIBLE);
                StorageReference storageRef = firebaseStorage.child("Photos").child(apartments.get(i).getKey()).child("img2");
                Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //   Log.e("URI", uri.toString())
                        Picasso.with(mContext).load(uri).into(holder.mainImage);
                        holder.progressBar1.setVisibility(View.GONE);

                    }
                });
            }
        });
        holder.smallImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.smallImg1.setAlpha((float) .3);
                holder.smallImg2.setAlpha((float) .3);
                holder.smallImg3.setAlpha((float) 1);
                holder.smallImg4.setAlpha((float) .3);
                holder.progressBar1.setVisibility(View.VISIBLE);
                StorageReference storageRef = firebaseStorage.child("Photos").child(apartments.get(i).getKey()).child("img3");
                Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //   Log.e("URI", uri.toString())
                        Picasso.with(mContext).load(uri).into(holder.mainImage);
                        holder.progressBar1.setVisibility(View.GONE);

                    }
                });
            }
        });
        holder.smallImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.smallImg1.setAlpha((float) .3);
                holder.smallImg2.setAlpha((float) .3);
                holder. smallImg3.setAlpha((float) .3);
                holder.smallImg4.setAlpha((float) 1);
                holder.progressBar1.setVisibility(View.VISIBLE);
                StorageReference storageRef = firebaseStorage.child("Photos").child(apartments.get(i).getKey()).child("img4");
                Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //   Log.e("URI", uri.toString())
                        Picasso.with(mContext).load(uri).into(holder.mainImage);
                        holder.progressBar1.setVisibility(View.GONE);

                    }
                });
            }
        });
    }

    private void retriveRequest(final int position, final ViewHolder holder) {

        final ArrayList<RenterRequest> renterRequests=new ArrayList<>();
        requestRef.child(apartments.get(position).getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp: dataSnapshot.getChildren()){
                    requestKey=dsp.getKey();
                    name=dsp.child("rentername").getValue().toString();
                    phone=dsp.child("renterphone").getValue().toString();
                    request=dsp.child("request").getValue().toString();
                    if(request.equals("accept")){
                        renterRequests.clear();
                        flag=true;
                        break;
                    }
                    if(request.equals("pending")){
                        renterRequests.add(new RenterRequest(dsp.child("rentername").getValue().toString()
                                ,dsp.child("renterphone").getValue().toString()
                                ,dsp.child("request").getValue().toString(),dsp.getKey().toString()));
                    }

                }
                if(flag==false) {
                    holder.requestRecyclerView.setVisibility(View.VISIBLE);
                    holder.rentedByTV.setVisibility(View.GONE);
                    holder.viewRenterProfileTV.setVisibility(View.GONE);
                    int size=renterRequests.size();
                    RequestAdopter myAdapter = new RequestAdopter(mContext, renterRequests, apartments.get(position).getKey().toString());
                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    holder.requestRecyclerView.setLayoutManager(llm);
                    holder.requestRecyclerView.setAdapter(myAdapter);
                    holder.editTV.setVisibility(View.VISIBLE);
                }
                else {
                    flag=false;
                    holder.requestRecyclerView.setVisibility(View.GONE);
                    holder.rentedByTV.setVisibility(View.VISIBLE);
                    holder.viewRenterProfileTV.setVisibility(View.VISIBLE);
                    holder.rentedByTV.setText("Rented by "+name);
holder.editTV.setVisibility(View.GONE);
                    renterRequests.clear();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
