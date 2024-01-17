package com.example.funtasktic_app_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import android.net.Uri;

public class helppage extends AppCompatActivity {

    private Button buttonback;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helppage);

        // Button initialisieren
        buttonback = findViewById(R.id.back_button);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(helppage.this, MainActivity.class);
                startActivity(intent);
            }
        });


        videoView = findViewById(R.id.videoView);


        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videohelp;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);


        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }
}
