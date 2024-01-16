package com.example.funtasktic_app_;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private UserDatabaseHandler db;

    private Button loginButtonsecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        db = new UserDatabaseHandler(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.button3);
        loginButtonsecond = findViewById(R.id.button2);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    boolean check_user=  db.checkUserExists(username, password);
                    if(check_user){

                        Toast.makeText(LoginPage.this, "Anmeldung erfolgreich!.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPage.this ,MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginPage.this, "falsche Benutzername oder Passwort!.", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(LoginPage.this, "Bitte füllen Sie alle Felder aus.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginButtonsecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginPage.this, RegestrierenPage.class);
                startActivity(intent);
            }
        });
    }
}
