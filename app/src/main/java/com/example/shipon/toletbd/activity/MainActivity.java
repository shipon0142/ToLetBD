package com.example.shipon.toletbd.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.database.FirebaseClient;

import static com.example.shipon.toletbd.activity.Main2Activity.apartments;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickSearch(View view) {
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
      //  this.finish();
      //  overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }

    public void createAccount(View view) {
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
        //this.finish();
      //  overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }

    public void clickSignin(View view) {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        //this.finish();
       // overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }
    boolean doubleBackToExitPressedOnce1 = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce1) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());


            System.exit(1);

            finish();
            return;
        }

        this.doubleBackToExitPressedOnce1 = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce1 = false;
            }
        }, 2000);
    }
}
