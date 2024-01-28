package com.example.funtasktic_app_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class DeleteAnimation extends AppCompatActivity {

    private VideoView videoView;
    private String username;

    private String screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_animation);

        videoView = findViewById(R.id.videoView2);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME_EXTRA");
        screen = intent.getStringExtra("Screen");

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.deleteanimation;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

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
                videoView.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(DeleteAnimation.this, DoneActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                intent.putExtra("Screen_Extra",screen);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
    }
}
