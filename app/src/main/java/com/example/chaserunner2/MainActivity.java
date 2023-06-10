package com.example.chaserunner2;

import static java.security.AccessController.getContext;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    MediaPlayer calling;

    public static int ScreenHeight = getScreenHeight();
    public static int ScreenWidth = getScreenWidth();
    TextView highScoreTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity1.class);
            MainActivity.this.startActivity(intent);
        });
        highScoreTv = findViewById(R.id.txtHighScore);
        calling = MediaPlayer.create(this,R.raw.calling);
        calling.setLooping(true);
        highScoreTv.setText(String.valueOf("High Score: "+myCanvas.HighScore));
        getWindow().setStatusBarColor(getResources().getColor(R.color.skyBlue, this.getTheme()));
    }



    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    @Override
    protected void onPause() {
        super.onPause();
        calling.stop();

    }
    @Override
    protected void onResume() {
        super.onResume();
        calling.start();
    }
}