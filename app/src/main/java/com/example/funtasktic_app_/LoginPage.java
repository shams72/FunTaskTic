package com.example.funtasktic_app_;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LoginPage  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        TextView username = (TextView) findViewById(R.id.usernameEditText);
        TextView password = (TextView) findViewById(R.id.passwordEditText);
        Button loginButton = (Button) findViewById(R.id.button3);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    Toast.makeText(LoginPage.this,"LOGIN SUCCESFULL",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(LoginPage.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginPage.this,"Login Unsuccessfull.Please Try Again!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
