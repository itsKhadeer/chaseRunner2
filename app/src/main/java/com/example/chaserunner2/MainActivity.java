package com.example.chaserunner2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new myCanvas(this));
        getWindow().setStatusBarColor(getResources().getColor(R.color.skyBlue, this.getTheme()));
    }

}