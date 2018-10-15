package com.example.shipon.toletbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.shipon.toletbd.R;

public class SplashActivity extends AppCompatActivity {
ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=findViewById(R.id.LOGO);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.myanim);
        logo.startAnimation(animation);
        new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    Intent iuu = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(iuu);
                    finish();
                }
            }
        }.start();
    }
}
