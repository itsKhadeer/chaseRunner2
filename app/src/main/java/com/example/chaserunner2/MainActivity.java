package com.example.chaserunner2;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    MediaPlayer calling;


    public static int ScreenHeight = Math.min(getScreenHeight(),getScreenWidth());
    public static int ScreenWidth = Math.max(getScreenHeight(),getScreenWidth());
    TextView highScoreTv;
    ImageView charDisplay;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        charDisplay = findViewById(R.id.showCharacters);

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            SplashScreenActivity.gameStart = true;
            MainActivity.this.startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });
        charDisplay.setOnClickListener(v -> {
            Intent intent = new Intent(this, characterSelect.class);

            MainActivity.this.startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });
        highScoreTv = findViewById(R.id.txtHighScore);
        calling = MediaPlayer.create(this,R.raw.calling);
        calling.setLooping(true);
        sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int HighScore = sharedPreferences.getInt("HighScore", 0);
        highScoreTv.setText(String.valueOf("High Score: "+HighScore));
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
        calling.pause();

    }
    @Override
    protected void onResume() {
        super.onResume();
        calling.start();
    }

}