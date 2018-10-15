package com.example.shipon.toletbd.adopter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.example.shipon.toletbd.models.Apartment;
import com.example.shipon.toletbd.models.CustomRenterLoginClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.shipon.toletbd.activity.Main2Activity.USER;
import static com.example.shipon.toletbd.activity.Main2Activity.apartment1;


/**
 * Created by Shipon on 9/25/2018.
 */

public class ApartmentAdopter extends RecyclerView.Adapter<ApartmentAdopter.ViewHolder> {

    Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList<Apartment>apartments;
    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();
    //private StorageReference firebaseStorage=FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/khaidai-65b30.appspot.com/o/foodimage%2F110.png?alt=media&token=13e72acb-044a-484c-b021-f9c2cb1c7867");
    // data is passed into the constructor
    public ApartmentAdopter(Context context, ArrayList<Apartment> apartments) {
        this.mInflater = LayoutInflater.from(context);
        this.apartments=apartments;
        this.mContext = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.apartment, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // String animal = mData.get(position);
         holder.locationTV.setText(apartments.get(position).getDistrictOwner()+","+apartments.get(position).getAreaOwner());
         holder.monthTV.setText(apartments.get(position).getFromMonth());
         holder.catagoryTV.setText(apartments.get(position).getHomeCatagory());
         holder.costTV.setText("Cost: "+apartments.get(position).getCostHome()+" BDT");
         if(apartments.get(position).getSize().equals("yes")){
             holder.ablTV.setText("Available");
             holder.ablTV.setTextColor(Color.parseColor("#008000"));
         }
         else {
             holder.ablTV.setText("Not Available");
             holder.ablTV.setTextColor(Color.parseColor("#FF0000"));
         }
         holder.progressBar.setVisibility(View.VISIBLE);
        holder.detailsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(apartments.get(position).getSize().equals("no")){
                    Toast.makeText(mContext,"Thias Apartment Not Available",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(USER.equals("null") || USER.equals("owner")){
                    Toast.makeText(mContext,"Please Login as Renter",Toast.LENGTH_SHORT).show();
                    CustomRenterLoginClass customLoginClass=new CustomRenterLoginClass(mContext, "r", new MyCallbackClass() {
                        @Override
                        public void callback(String value) {
                            if(value.equals("ok")){
                                apartment1 = apartments.get(position);
                                Intent intent = new Intent(mContext, DetailsActivity.class);
                                intent.putExtra("position", position);
                                mContext.startActivity(intent);
                            }
                        }
                    });
                    customLoginClass.show();
                    Window window = customLoginClass .getWindow();
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                else {
                    apartment1 = apartments.get(position);
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                }


            }
        });

        StorageReference storageRef = firebaseStorage.child("Photos").child(apartments.get(position).getKey()).child("img1");
        Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString());
                Picasso.with(mContext).load(uri).into(holder.roomImg1IV);
                holder.progressBar.setVisibility(View.GONE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.detailsTV.setText(e.getMessage().toString());
            }
        });
        //   Picasso.with(holder.photo.getContext()).load().into(holder.photo);
        // Glide.with(mContext)
        //     .using(new FirebaseImageLoader())
        //    .load(firebaseStorage)
        //  .into(holder.photo);


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return apartments.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView locationTV;
        TextView monthTV;
        TextView catagoryTV,costTV,detailsTV,ablTV;
        LinearLayout linearLayout;
        ProgressBar progressBar;
        ImageView roomImg1IV;

        ViewHolder(View itemView) {
            super(itemView);
            locationTV = (TextView) itemView.findViewById(R.id.LocationTV);
            monthTV = (TextView) itemView.findViewById(R.id.MonthTV);
            catagoryTV = (TextView) itemView.findViewById(R.id.CatagoryTV);
            costTV= (TextView) itemView.findViewById(R.id.CostTV);
            detailsTV= (TextView) itemView.findViewById(R.id.DetailsTV);
            progressBar=  itemView.findViewById(R.id.ProgressBar);
            roomImg1IV=  itemView.findViewById(R.id.RoomImg1IV);
            ablTV=  itemView.findViewById(R.id.AblTV);
             //poffer = (TextView) itemView.findViewById(R.id.idOffer);
           // pdeadline = (TextView) itemView.findViewById(R.id.idDeadline);



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

}
