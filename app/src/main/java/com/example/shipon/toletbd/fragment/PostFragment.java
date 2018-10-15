package com.example.shipon.toletbd.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.activity.Main2Activity;
import com.example.shipon.toletbd.activity.MyPostActivity;
import com.example.shipon.toletbd.database.FirebaseClient;
import com.example.shipon.toletbd.models.Apartment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;
import static com.example.shipon.toletbd.activity.Main2Activity.USER_NAME;
import static com.example.shipon.toletbd.activity.Main2Activity.USER_PHONE;
import static com.example.shipon.toletbd.activity.Main2Activity.apartments;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    String catagory[] = {"---Select Category---", "All", "Family", "Sublet", "Boys Only", "Girls Only", "Boys Hostel", "Girls Hostel"};
    String month[] = {"---Select Month---", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static final int SELECTED_PICTURE1 = 1, SELECTED_PICTURE2 = 2, SELECTED_PICTURE3 = 3, SELECTED_PICTURE4 = 4;
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
    ImageView addPhoto1IV, addPhoto2IV, addPhoto3IV, addPhoto4IV;
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
    long child = 0;
    int im1 = 0, im2 = 0, im3 = 0, im4 = 0;
    DatabaseReference myDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference appertmentsRef = myDatabaseRef.child("Appertments");
    StorageReference store = FirebaseStorage.getInstance().getReference();
    DatabaseReference ownerRef = myDatabaseRef.child("Owners");
    DatabaseReference renterRef = myDatabaseRef.child("Renters");

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbarPost = getActivity().findViewById(R.id.PostText);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        catagorySpinner = view.findViewById(R.id.CatagorySpinnerPost);
        monthSpinner = view.findViewById(R.id.MonthSpinnerPost);
        districtET = view.findViewById(R.id.DistrictET);
        areaET = view.findViewById(R.id.AreaET);
        costET = view.findViewById(R.id.CostET);
        detailsET = view.findViewById(R.id.DetailsET);
        houseET = view.findViewById(R.id.HouseET);
        addPhoto1IV = view.findViewById(R.id.AddPhoto1IV);
        addPhoto2IV = view.findViewById(R.id.AddPhoto2IV);
        addPhoto3IV = view.findViewById(R.id.AddPhoto3IV);
        addPhoto4IV = view.findViewById(R.id.AddPhoto4IV);
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, district);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, dhakaArea);
        districtET.setAdapter(districtAdapter);
        areaET.setAdapter(areaAdapter);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Posting...");
        dialog.setCancelable(false);
        ArrayAdapter<String> catagoryArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, catagory);
        ArrayAdapter<String> monthArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, month);
        catagoryArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        monthArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        catagorySpinner.setAdapter(catagoryArrayAdapter);
        monthSpinner.setAdapter(monthArrayAdapter);
        setItemSelectedListener();
        setOnClickListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECTED_PICTURE1 && resultCode == RESULT_OK) {
            imageUri1 = data.getData();
            InputStream inputStream;
            try {
                inputStream = getActivity().getContentResolver().openInputStream(imageUri1);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                addPhoto1IV.setImageBitmap(image);
                im1 = 1;
                // Toast.makeText(getContext(),"YES",Toast.LENGTH_SHORT).show();
                // addPhoto.setText("Change Photo");
                addPhoto1IV.setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (Exception e) {
                e.printStackTrace();
                // Toast.makeText(getContext(),"NO",Toast.LENGTH_SHORT).show();

            }
        }
        if (requestCode == SELECTED_PICTURE2 && resultCode == RESULT_OK) {
            imageUri2 = data.getData();
            InputStream inputStream;
            try {
                im2 = 1;
                inputStream = getActivity().getContentResolver().openInputStream(imageUri2);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                addPhoto2IV.setImageBitmap(image);
                // Toast.makeText(getContext(),"YES",Toast.LENGTH_SHORT).show();
                // addPhoto.setText("Change Photo");
                addPhoto2IV.setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (Exception e) {
                e.printStackTrace();
                // Toast.makeText(getContext(),"NO",Toast.LENGTH_SHORT).show();

            }
        }
        if (requestCode == SELECTED_PICTURE3 && resultCode == RESULT_OK) {
            imageUri3 = data.getData();
            InputStream inputStream;
            try {
                im3 = 1;
                inputStream = getActivity().getContentResolver().openInputStream(imageUri3);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                addPhoto3IV.setImageBitmap(image);
                // Toast.makeText(getContext(),"YES",Toast.LENGTH_SHORT).show();
                // addPhoto.setText("Change Photo");
                addPhoto3IV.setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (Exception e) {
                e.printStackTrace();
                // Toast.makeText(getContext(),"NO",Toast.LENGTH_SHORT).show();

            }
        }
        if (requestCode == SELECTED_PICTURE4 && resultCode == RESULT_OK) {
            imageUri4 = data.getData();
            InputStream inputStream;
            try {
                im4 = 1;
                inputStream = getActivity().getContentResolver().openInputStream(imageUri4);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                addPhoto4IV.setImageBitmap(image);
                // Toast.makeText(getContext(),"YES",Toast.LENGTH_SHORT).show();
                // addPhoto.setText("Change Photo");
                addPhoto4IV.setScaleType(ImageView.ScaleType.FIT_XY);
            } catch (Exception e) {
                e.printStackTrace();
                // Toast.makeText(getContext(),"NO",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void setOnClickListener() {
        addPhoto1IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String path = picture.getPath();
                Uri data = Uri.parse(path);
                i.setDataAndType(data, "image/*");
                startActivityForResult(i, SELECTED_PICTURE1);
            }
        });
        addPhoto2IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String path = picture.getPath();
                Uri data = Uri.parse(path);
                i.setDataAndType(data, "image/*");
                startActivityForResult(i, SELECTED_PICTURE2);
            }
        });
        addPhoto3IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String path = picture.getPath();
                Uri data = Uri.parse(path);
                i.setDataAndType(data, "image/*");
                startActivityForResult(i, SELECTED_PICTURE3);
            }
        });
        addPhoto4IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String path = picture.getPath();
                Uri data = Uri.parse(path);
                i.setDataAndType(data, "image/*");
                startActivityForResult(i, SELECTED_PICTURE4);
            }
        });
        toolbarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAll()) {

                    String district = districtET.getText().toString();
                    String area = areaET.getText().toString();
                    String houseNo = houseET.getText().toString();
                    String catagory = catagoryType.toString();
                    String month = monthName.toString();
                    String cost = costET.getText().toString();
                    String details = detailsET.getText().toString();
                    Apartment newApartment = new Apartment(USER_NAME, USER_PHONE,
                            district, area, catagory, month, details, houseNo, cost);
                    if (im1 == 1) {
                        uri.add(imageUri1);
                        im1 = 0;
                    }
                    if (im2 == 1) {
                        uri.add(imageUri2);
                        im2 = 0;
                    }
                    if (im3 == 1) {
                        uri.add(imageUri3);
                        im3 = 0;
                    }
                    if (im4 == 1) {
                        uri.add(imageUri4);
                        im4 = 0;
                    }


                    if (uri.size() == 0) {
                        Toast.makeText(getContext(), "Please select at least one Photo", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.show();
                        addApartment(newApartment, uri);
                    }

                }


            }
        });
    }

    public long addApartment(final Apartment apartment, final ArrayList<Uri> uri) {
        appertmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                child = dataSnapshot.getChildrenCount();
                addPhoto(apartment, child, uri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return child;

    }

    private void addPhoto(final Apartment apartment, final long child, final ArrayList<Uri> uri) {
        StorageReference filepath = store.child("Photos").child("apartment_" + (child + 1));
        for (int i = 0; i < uri.size(); i++) {

            StorageReference filepath1 = filepath.child("img" + (i + 1));
            final int j = i;
            filepath1.putFile(uri.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (j == uri.size() - 1) {
                        appertmentsRef.child("apartment_" + (child + 1)).child(OWNER_NAME).setValue(apartment.getNameOwner());
                        appertmentsRef.child("apartment_" + (child + 1)).child(CONTACT).setValue(apartment.getContactOwner());
                        appertmentsRef.child("apartment_" + (child + 1)).child(DISTRICT).setValue(apartment.getDistrictOwner());
                        appertmentsRef.child("apartment_" + (child + 1)).child(AREA).setValue(apartment.getAreaOwner());
                        appertmentsRef.child("apartment_" + (child + 1)).child(HOUSE_NO).setValue(apartment.getHouseNo());
                        appertmentsRef.child("apartment_" + (child + 1)).child(CATAGORY).setValue(apartment.getHomeCatagory());
                        appertmentsRef.child("apartment_" + (child + 1)).child(COST).setValue(apartment.getCostHome());
                        appertmentsRef.child("apartment_" + (child + 1)).child(MONTH).setValue(apartment.getFromMonth());
                        appertmentsRef.child("apartment_" + (child + 1)).child(DETAILS).setValue(apartment.getDetailsOwnerHome());
                        appertmentsRef.child("apartment_" + (child + 1)).child(IMG_SIZE).setValue("yes");

                        apartments.clear();

                        retriveAllAppartment();

                    }
                }
            });

        }

    }
    public void retriveAllAppartment() {

        appertmentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String key = (String) dsp.getKey();
                    String ownername = (String) dsp.child(OWNER_NAME).getValue();
                    String area = (String) dsp.child(AREA).getValue();
                    String catagory = (String) dsp.child(CATAGORY).getValue();
                    String contact = (String) dsp.child(CONTACT).getValue();
                    String cost = (String) dsp.child(COST).getValue();
                    String details = (String) dsp.child(DETAILS).getValue();
                    String district = (String) dsp.child(DISTRICT).getValue();
                    String houseno = (String) dsp.child(HOUSE_NO).getValue();
                    String month = (String) dsp.child(MONTH).getValue();
                    String size = (String) dsp.child(IMG_SIZE).getValue();

                    apartments.add(new Apartment(ownername, contact, district, area, catagory, month, details, houseno, cost,key,size));
                }
                dialog.cancel();
                Toast.makeText(getContext(), "Succesfully posted", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), MyPostActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setItemSelectedListener() {
        catagorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catagoryType = catagory[position].toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthName = month[position].toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        if (catagoryType.toString().contains("Select")) {
            // Toast.makeText(getContext(),"Select catagory field",Toast.LENGTH_SHORT).show();
            flag = false;
        }
        if (monthName.toString().equals("Select")) {
            flag = false;
            // detailsET.setError("This field can,t be empty");
        }
        if (flag == false) {
            Toast.makeText(getContext(), "Select fill all the field", Toast.LENGTH_SHORT).show();
        }
        return flag;

    }

}
