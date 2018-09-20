package com.example.shipon.toletbd.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.fragment.PostFragment;
import com.example.shipon.toletbd.fragment.SearchHomeFragment;

public class Main2Activity extends AppCompatActivity {


LinearLayout mainLayout;

Toolbar toolbar;
TextView postText,titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        postText=findViewById(R.id.PostText);
        titleText=findViewById(R.id.TitleText);
        titleText.setText("Search by location");
        postText.setText("");
        FragmentManager manage = getSupportFragmentManager();
        FragmentTransaction transactio = manage.beginTransaction();
        SearchHomeFragment searchHomeFragment=new SearchHomeFragment();
        transactio.replace(R.id.mainLayout, searchHomeFragment).commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    titleText.setText("Post as owner");
                    postText.setText("Post");
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    PostFragment postFragment = new PostFragment();
                    transaction.replace(R.id.mainLayout, postFragment).commit();

                    return true;
                case R.id.navigation_notifications:
                    titleText.setText("Search by location");
                    postText.setText("");
                    FragmentManager manage = getSupportFragmentManager();
                    FragmentTransaction transactio = manage.beginTransaction();
                    SearchHomeFragment searchHomeFragment=new SearchHomeFragment();
                    transactio.replace(R.id.mainLayout, searchHomeFragment).commit();
                    return true;
            }
            return false;
        }
    };

    public void clickPost(View view) {
    }
}
