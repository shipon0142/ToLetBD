package com.example.shipon.toletbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.database.FirebaseClient;

import static com.example.shipon.toletbd.activity.Main2Activity.apartments;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apartments.clear();

        FirebaseClient obj=new FirebaseClient();
        obj.retriveAllAppartment();
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
}
