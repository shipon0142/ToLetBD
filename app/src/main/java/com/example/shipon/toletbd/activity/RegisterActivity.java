package com.example.shipon.toletbd.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.fragment.OwnerRegFragment;
import com.example.shipon.toletbd.fragment.RenterRegFragment;

public class RegisterActivity extends AppCompatActivity {

    private TextView registerTextTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_renter:
                    registerTextTitle.setText("Register as renter");
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    RenterRegFragment renterRegFragment=new RenterRegFragment();
                    transaction.replace(R.id.RegisterMainLayout, renterRegFragment).commit();
                    return true;
                case R.id.navigation_owner:
                    registerTextTitle.setText("Register as owner");
                    FragmentManager manage = getSupportFragmentManager();
                    FragmentTransaction transactio = manage.beginTransaction();
                    OwnerRegFragment ownerRegFragment=new OwnerRegFragment();
                    transactio.replace(R.id.RegisterMainLayout, ownerRegFragment).commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerTextTitle=findViewById(R.id.RegisterTitleText);
        registerTextTitle.setText("Register as renter");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RenterRegFragment renterRegFragment=new RenterRegFragment();
        transaction.replace(R.id.RegisterMainLayout, renterRegFragment).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


}
