package com.example.rinapizza;

// MyAudioService.java

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyAudioService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Your audio file resource ID
        int audioResourceId = R.raw.your_audio_file;

        // Create MediaPlayer
        mediaPlayer = MediaPlayer.create(this, audioResourceId);

        // Set looping
        mediaPlayer.setLooping(true);

        // Start playback
        mediaPlayer.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Release the MediaPlayer when the Service is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
