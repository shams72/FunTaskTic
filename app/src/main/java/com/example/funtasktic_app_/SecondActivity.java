package com.example.funtasktic_app_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    private Button buttonToMain;
    private Button buttonToThird;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        buttonToMain = findViewById(R.id.buttonToMain);
        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this ,MainActivity.class);
                startActivity(intent);
            }
        });

        buttonToThird = findViewById(R.id.buttonToThird);
        buttonToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this ,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }
}