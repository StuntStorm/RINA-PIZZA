package com.example.rinapizza;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashVideoActivity extends AppCompatActivity implements AudioManager.OnAudioFocusChangeListener {

    private static final int SPLASH_DELAY = 60000; // 1 minute
    private static final int SKIP_DELAY = 5000; // 5 seconds (time before skip button appears)

    private VideoView videoView;
    private Button skipButton;
    private AudioManager audioManager;
    private AudioFocusRequest audioFocusRequest;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_video);

        videoView = findViewById(R.id.videoView);
        skipButton = findViewById(R.id.skipButton);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus
        AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes)
                .setWillPauseWhenDucked(true)
                .setOnAudioFocusChangeListener(this)
                .build();

        int result = audioManager.requestAudioFocus(audioFocusRequest);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            startVideoPlayback();
        }

        // Delay before showing the skip button
        new Handler().postDelayed(() -> skipButton.setVisibility(View.VISIBLE), SKIP_DELAY);

        // Set a listener for the skip button
        skipButton.setOnClickListener(v -> startMainActivity());
    }

    private void startVideoPlayback() {
        // Set the path of the video file (without extension)
        String path = "android.resource://" + getPackageName() + "/" + R.raw.your_video_file; // Replace with your video file name

        // Set the URI of the video file
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);

        // Start playing the video
        videoView.start();

        // Set a listener for the completion of the video
        videoView.setOnCompletionListener(mp -> startMainActivity());
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // Resume playback
                if (mediaPlayer == null) {
                    startVideoPlayback();
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Stop playback
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Pause playback
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
        }
    }

    private void startMainActivity() {
        // Release audio focus before starting the main activity
        audioManager.abandonAudioFocusRequest(audioFocusRequest);

        // Start the main activity
        Intent intent = new Intent(SplashVideoActivity.this, MainActivity2.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Release audio focus when the activity is stopped
        audioManager.abandonAudioFocusRequest(audioFocusRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release audio focus and release MediaPlayer when the activity is destroyed
        audioManager.abandonAudioFocusRequest(audioFocusRequest);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
