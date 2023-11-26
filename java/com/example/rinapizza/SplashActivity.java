package com.example.rinapizza;

// SplashActivity.java
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get the VideoView
        VideoView splashVideo = findViewById(R.id.splashVideo);

        // Set the video file from the raw folder
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.your_video2_file);
        splashVideo.setVideoURI(videoUri);

        // Start playing the video
        splashVideo.start();

        // Set a listener to move to the main activity when the video finishes
        splashVideo.setOnCompletionListener(mp -> {
            // Start the main activity
            startActivity(new Intent(SplashActivity.this, MainActivity2.class));
            finish();
        });
    }
}
