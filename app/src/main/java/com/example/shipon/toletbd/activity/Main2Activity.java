package com.example.shipon.toletbd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.MyCallbackClass;
import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.database.FirebaseClient;
import com.example.shipon.toletbd.fragment.PostFragment;
import com.example.shipon.toletbd.fragment.SearchHomeFragment;
import com.example.shipon.toletbd.fragment.SearchResultFragment;
import com.example.shipon.toletbd.models.Apartment;
import com.example.shipon.toletbd.models.CustomRenterLoginClass;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {


    LinearLayout mainLayout;

    Toolbar toolbar;
    public static SharedPreferences loginPreferences;
    public static Boolean rentersaveLogin;
    public static Boolean ownersaveLogin;

    TextView postText, titleText;
    public static String USER = "null";
    public static String USER_PHONE = "null";
    public static String USER_NAME = "null";
    public static String SEARCH_DISTRICT="null";
    public static String SEARCH_AREA="null";
    public static String SEARCH_CATAGORY="null";
    public static ArrayList<Apartment> apartments = new ArrayList<>();
    public static Apartment apartment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        postText = findViewById(R.id.PostText);
        titleText = findViewById(R.id.TitleText);
        titleText.setText("Search by location");
        postText.setText("");
    

        FragmentManager manage = getSupportFragmentManager();
        FragmentTransaction transactio = manage.beginTransaction();
        SearchHomeFragment searchHomeFragment = new SearchHomeFragment();
        transactio.replace(R.id.mainLayout, searchHomeFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {

        super.onResume();
        apartments.clear();
        FirebaseClient obj=new FirebaseClient();
        obj.retriveAllAppartment();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    titleText.setText("Search by location");
                    postText.setText("");
                    FragmentManager manage = getSupportFragmentManager();
                    FragmentTransaction transactio = manage.beginTransaction();
                    SearchHomeFragment searchHomeFragment = new SearchHomeFragment();
                    transactio.replace(R.id.mainLayout, searchHomeFragment).commit();







                    //transaction1.replace(R.id.mainLayout, searchResultFragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    if(USER.equals("null") || USER.equals("renter")){

                        CustomRenterLoginClass customLoginClass=new CustomRenterLoginClass(Main2Activity.this, "o", new MyCallbackClass() {
                            @Override
                            public void callback(String value) {
                                if(value.equals("ok"))
                                titleText.setText("Post as owner");
                                postText.setText("Post");
                                FragmentManager manager = getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                PostFragment postFragment = new PostFragment();
                                transaction.replace(R.id.mainLayout, postFragment).commit();
                            }

                        });
                        customLoginClass.show();
                        Window window = customLoginClass .getWindow();
                        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        /*Toast.makeText(getApplicationContext(),"You have to log in as Owner",Toast.LENGTH_SHORT);
                        Intent intent = new Intent(Main2Activity.this,LoginActivity.class);
                        startActivity(intent);*/

                    }
                    if(USER.equals("owner")) {
                        titleText.setText("Post as owner");
                        postText.setText("Post");
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        PostFragment postFragment = new PostFragment();
                        transaction.replace(R.id.mainLayout, postFragment).commit();
                    }

                    return true;
                case R.id.navigation_notifications:
                    titleText.setText("All apartment");
                    postText.setText("");
                    SEARCH_DISTRICT="null";
                    SEARCH_AREA="null";
                    SEARCH_CATAGORY="null";
                    FragmentManager manager1 = getSupportFragmentManager();
                    FragmentTransaction transaction1 = manager1.beginTransaction();
                    SearchResultFragment searchResultFragment = new SearchResultFragment();
                    transaction1.replace(R.id.mainLayout, searchResultFragment).commit();
                    return true;
            }
            return false;
        }
    };


    public void clickAccount(View view) {
        Intent intent = new Intent(Main2Activity.this, AccountActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (USER.equals("null")) {
            super.onBackPressed();
        } else {

            if (doubleBackToExitPressedOnce) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());


                System.exit(1);

                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


}
