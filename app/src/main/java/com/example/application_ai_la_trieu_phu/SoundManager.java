package com.example.application_ai_la_trieu_phu;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private static SoundManager instance;
    private MediaPlayer mediaPlayer;
    private Context context;

    private SoundManager(Context context) {
        this.context = context.getApplicationContext();
        this.mediaPlayer = new MediaPlayer();
    }

    public static SoundManager getInstance(Context context) {
        if (instance == null) {
            instance = new SoundManager(context);
        }
        return instance;
    }

    public void playMusic(int rawResourceId) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(context, rawResourceId);
        mediaPlayer.start();
    }

    public void stopMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }
    public void playSoundAnswer(int index) {
        if(index ==0 ){
            playMusic(R.raw.ans_a);
        } else if (index == 1) {
            playMusic(R.raw.ans_b);
        } else if (index == 2) {
            playMusic(R.raw.ans_c);
        } else if (index == 3) {
            playMusic(R.raw.ans_d);
        }
    }

    public void playSoundAnswerTrue(int index) {
        if(index ==0 ){
            playMusic(R.raw.true_a);
        } else if (index == 1) {
            playMusic(R.raw.true_b);
        } else if (index == 2) {
            playMusic(R.raw.true_c);
        } else if (index == 3) {
            playMusic(R.raw.true_d2);
        }
    }

    public void playSoundAnswerFault(int index) {
        if(index ==0 ){
            playMusic(R.raw.lose_a);
        } else if (index == 1) {
            playMusic(R.raw.lose_b);
        } else if (index == 2) {
            playMusic(R.raw.lose_c);
        } else if (index == 3) {
            playMusic(R.raw.lose_d2);
        }
    }
}
