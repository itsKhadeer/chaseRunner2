package com.example.chaserunner2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.GnssAntennaInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity1 extends AppCompatActivity {



    boolean heroThemeIsPlaying = false,
            villainThemeIsPlaying = false,
            jumpSoundIsPlaying = false,
            painSoundIsPlaying = false,
            deathSoundIsPlaying = false,
            powerUpSoundIsPlaying = false,
            powerDownSoundIsPlaying = false;            ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new myCanvas(this));
        getWindow().setStatusBarColor(getResources().getColor(R.color.skyBlue, this.getTheme()));


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(myCanvas.heroTheme.isPlaying()) {
            myCanvas.heroTheme.pause();
            heroThemeIsPlaying = true;
        }
        if(myCanvas.villainTheme.isPlaying()) {
            myCanvas.villainTheme.pause();
            villainThemeIsPlaying = true;
        }
        if(myCanvas.jumpSound.isPlaying()) {
            myCanvas.jumpSound.pause();
            jumpSoundIsPlaying = true;
        }
        if(myCanvas.painSound.isPlaying()) {
            myCanvas.painSound.pause();
            painSoundIsPlaying = true;
        }
        if(myCanvas.deathSound.isPlaying()) {
            myCanvas.deathSound.pause();
            deathSoundIsPlaying = true;
        }
        if(myCanvas.powerUpSound.isPlaying()) {
            myCanvas.powerUpSound.pause();
            powerUpSoundIsPlaying = true;
        }
        if(myCanvas.powerDownSound.isPlaying()) {
            myCanvas.powerDownSound.pause();
            powerDownSoundIsPlaying = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (jumpSoundIsPlaying) {
            myCanvas.jumpSound.start();
            jumpSoundIsPlaying = false;
        }
        if (heroThemeIsPlaying) {
            myCanvas.heroTheme.start();
            heroThemeIsPlaying = false;
        }
        if (villainThemeIsPlaying) {
            myCanvas.villainTheme.start();
            villainThemeIsPlaying = false;
        }
        if (deathSoundIsPlaying) {
            myCanvas.deathSound.start();
            deathSoundIsPlaying = false;
        }
        if (painSoundIsPlaying) {
            myCanvas.painSound.start();
            painSoundIsPlaying = false;
        }
        if (powerUpSoundIsPlaying) {
            myCanvas.powerUpSound.start();
            powerUpSoundIsPlaying = false;
        }
        if (powerDownSoundIsPlaying) {
            myCanvas.powerDownSound.start();
            powerDownSoundIsPlaying = false;
        }
    }
}