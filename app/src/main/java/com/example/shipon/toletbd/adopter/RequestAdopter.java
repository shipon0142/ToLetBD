package com.example.shipon.toletbd.adopter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.example.shipon.toletbd.models.Apartment;
import com.example.shipon.toletbd.models.CustomRenterLoginClass;
import com.example.shipon.toletbd.models.RenterRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.shipon.toletbd.activity.Main2Activity.USER;
import static com.example.shipon.toletbd.activity.Main2Activity.apartment1;


/**
 * Created by Shipon on 9/25/2018.
 */

public class RequestAdopter extends RecyclerView.Adapter<RequestAdopter.ViewHolder> {

    Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();
    private ArrayList<RenterRequest> renterRequests;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference requestRef=myDatabaseRef.child("request");
    DatabaseReference appertmentsRef = myDatabaseRef.child("Appertments");
    String apartmentNumber;
    //private StorageReference firebaseStorage=FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/khaidai-65b30.appspot.com/o/foodimage%2F110.png?alt=media&token=13e72acb-044a-484c-b021-f9c2cb1c7867");
    // data is passed into the constructor
    public RequestAdopter(Context context, ArrayList<RenterRequest> renterRequests, String apartmentNumber) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.renterRequests = renterRequests;
        this.apartmentNumber=apartmentNumber;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.request_recycler_view, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.nameRequestRenterNameTV.setText(renterRequests.get(position).getName());
        holder.acceptRequestTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setMessage("Are you sure to accept rent request?")

                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Toast.makeText(MainActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                                appertmentsRef.child(apartmentNumber).child("size").setValue("no");
                                requestRef.child(apartmentNumber).child(renterRequests.get(position).getKey()).child("request").setValue("accept");
                                Toast.makeText(mContext,"You succesfully Accepted "+renterRequests.get(position).getName()+",s request",Toast.LENGTH_SHORT);
                                   renterRequests.clear();
                                ((Activity)mContext).finish();
                                ((Activity)mContext).startActivity(new Intent(mContext,MyPostActivity.class));
                            }})
                        .setNegativeButton(android.R.string.no, null).show();


            }
        });
        holder.rejectRequestTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setMessage("Are you sure to reject this Renter?")

                        .setPositiveButton("Reject", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Toast.makeText(MainActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                                requestRef.child(apartmentNumber).child(renterRequests.get(position).getKey()).child("request").setValue("reject");
                                renterRequests.remove(position);
                                Toast.makeText(mContext,"You Rejected "+renterRequests.get(position).getName()+",s request",Toast.LENGTH_SHORT);
                                notifyDataSetChanged();

                            }})
                        .setNegativeButton(android.R.string.no, null).show();


            }
        });
        holder.goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, RenterProfileActivity.class);
                i.putExtra("phn", renterRequests.get(position).getContact());
                mContext.startActivity(i);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return renterRequests.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameRequestRenterNameTV, rejectRequestTV, acceptRequestTV,goToProfile;

        ViewHolder(View itemView) {
            super(itemView);
            nameRequestRenterNameTV = itemView.findViewById(R.id.NameRequestRenterTV);
            rejectRequestTV = itemView.findViewById(R.id.RejectRequestTV);
            acceptRequestTV = itemView.findViewById(R.id.AcceptRequestTV);
            goToProfile = itemView.findViewById(R.id.GoToProfileTV);
        }


    }


    // convenience method for getting data at click position
    String getItem(int id) {
        return renterRequests.get(id).getContact();
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
