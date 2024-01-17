package com.example.funtasktic_app_;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.funtasktic_app_.R;

public class RegestrierenPage extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText passwordOneEditText;
    private EditText passwordTwoEditText;
    private Button confirmationButton;

    private Button backButton;



    private UserDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regestrierenpage);

        db = new UserDatabaseHandler(this);

        usernameEditText = findViewById(R.id.usernameET);
        passwordOneEditText = findViewById(R.id.passwordET1);
        passwordTwoEditText = findViewById(R.id.passwordET2);
        confirmationButton = findViewById(R.id.confirmation_btn);
        backButton = findViewById(R.id.back_Button_);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegestrierenPage.this ,LoginPage.class);
                startActivity(intent);
            }
        });

        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String passwordOne = passwordOneEditText.getText().toString();
                String passwordTwo = passwordTwoEditText.getText().toString();

                if (username.isEmpty() || passwordOne.isEmpty() || passwordTwo.isEmpty()) {
                   Toast.makeText(RegestrierenPage.this, "Bitte füllen Sie alle Felder aus.", Toast.LENGTH_SHORT).show();
                } else if (!passwordOne.equals(passwordTwo)) {
                    Toast.makeText(RegestrierenPage.this, "Die Passwörter stimmen nicht überein.", Toast.LENGTH_SHORT).show();
                }
                else if(db.checkUserExists(username, passwordOne)){
                    Toast.makeText(RegestrierenPage.this, "Es existiert bereits ein Konto mit diesen Daten.", Toast.LENGTH_SHORT).show();

                }
                else if(passwordOne.equals(passwordTwo) && (!username.isEmpty())) {
                    boolean check_add = db.addUser(username,passwordOne);
                    if(check_add) {
                        Toast.makeText(RegestrierenPage.this, "Regestrierung erfolgreich!.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegestrierenPage.this ,LoginPage.class);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(RegestrierenPage.this, "Es ist ein Fehler aufgetreten. Bitte versuche es später!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
