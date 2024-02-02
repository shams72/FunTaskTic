package com.example.funtasktic_app_;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.net.Uri;

import com.google.android.material.snackbar.Snackbar;

public class helppage extends AppCompatActivity {

    private VideoView videoView;
    private String username;

    private String screen;

    private String videotype;

    private String insert;
    private String Screen_Type;

    private Button back_button;

    private String Help="Help";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helppage);

        videoView = findViewById(R.id.videoView);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME_EXTRA");
        Screen_Type=intent.getStringExtra("Screen");

        insert = intent.getStringExtra("VideoTask"); // Use insert here, not user

        if ("Insert_Edit".equals(insert)) {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.newinserttask;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
        } else if ("Delete".equals(insert)) {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.deletealltaskkk;//delete from single page
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
        } else if ("Edit".equals(insert)) {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.edittaskkk;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
        } else if ("Alldelete".equals(insert)) {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.alltaskhelpvideo;//delette from erledigt page
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
        } else {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videohelp;
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
        }

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();  // Start video playback when prepared
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {


                Snackbar.make(videoView, "Video beendet. Drücken Sie Zurück, um fortzufahren", Snackbar.LENGTH_LONG).show();

                /*if("Home".equals(Screen_Type)) {
                    Intent intent = new Intent(helppage.this, MainActivity.class);
                    intent.putExtra("USERNAME_EXTRA", username);
                    intent.putExtra("Screen_Extra",screen);
                    intent.putExtra("Help",Help);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                if("Second".equals(Screen_Type)) {
                    Intent intent = new Intent(helppage.this, SecondActivity.class);
                    intent.putExtra("USERNAME_EXTRA", username);
                    intent.putExtra("Screen_Extra",screen);
                    intent.putExtra("Help",Help);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                if("Third".equals(Screen_Type)) {
                    Intent intent = new Intent(helppage.this, ThirdActivity.class);
                    intent.putExtra("USERNAME_EXTRA", username);
                    intent.putExtra("Screen_Extra",screen);
                    intent.putExtra("Help",Help);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }*/



            }
        });
        back_button =findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("Home".equals(Screen_Type)) {
                    Intent intent = new Intent(helppage.this, MainActivity.class);
                    intent.putExtra("USERNAME_EXTRA", username);
                    intent.putExtra("Screen_Extra",screen);
                    intent.putExtra("Help",Help);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                if("Second".equals(Screen_Type)) {
                    Intent intent = new Intent(helppage.this, SecondActivity.class);
                    intent.putExtra("USERNAME_EXTRA", username);
                    intent.putExtra("Screen_Extra",screen);
                    intent.putExtra("Help",Help);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
                if("Third".equals(Screen_Type)) {
                    Intent intent = new Intent(helppage.this, ThirdActivity.class);
                    intent.putExtra("USERNAME_EXTRA", username);
                    intent.putExtra("Screen_Extra",screen);
                    intent.putExtra("Help",Help);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            }

        });


    }
}
