package com.example.funtasktic_app_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        final Intent i = new Intent(SplashActivity.this, LoginPage.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(i);
               finish();
            }
        },1000);

    }
}