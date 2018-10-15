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
import com.example.shipon.toletbd.fragment.OwnerSigninFragment;
import com.example.shipon.toletbd.fragment.RenterSignInFragment;

import static com.example.shipon.toletbd.activity.AccountActivity.check;

public class LoginActivity extends AppCompatActivity {
TextView loginTextTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_renter:
                    loginTextTitle.setText("Renter");
                    FragmentManager manage = getSupportFragmentManager();
                    FragmentTransaction transactio = manage.beginTransaction();
                    RenterSignInFragment renterSignInFragment=new RenterSignInFragment();
                    transactio.replace(R.id.RegisterMainLayout, renterSignInFragment).commit();
                    return true;
                case R.id.navigation_owner:
                    loginTextTitle.setText("Owner");
                   FragmentManager manager = getSupportFragmentManager();
                  FragmentTransaction transaction = manager.beginTransaction();
                   OwnerSigninFragment ownerSignInFragment=new OwnerSigninFragment();
                   transaction.replace(R.id.RegisterMainLayout, ownerSignInFragment).commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginTextTitle=findViewById(R.id.LoginTitleText);
        loginTextTitle.setText("Renter");
        FragmentManager manage = getSupportFragmentManager();
        FragmentTransaction transactio = manage.beginTransaction();
        RenterSignInFragment renterSignInFragment=new RenterSignInFragment();
        transactio.replace(R.id.RegisterMainLayout, renterSignInFragment).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void clickBackArrow(View view) {
        check=false;
        super.onBackPressed();
    }
}
