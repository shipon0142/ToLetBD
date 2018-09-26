package com.example.shipon.toletbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.shipon.toletbd.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public void clickAccountClose(View view) {
        this.finish();
        //overridePendingTransition(R.anim.slide_out_up ,R.anim.slide_in_up );
    }
}
