package com.example.chaserunner2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SplashScreenActivity extends AppCompatActivity {
    private TextView tipTextView;
    public static boolean homeStart = false;
    public static boolean gameStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreenlayout);
        tipTextView = findViewById(R.id.tipsTV);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-obstacle-dodge.vercel.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        MyApi api = retrofit.create(MyApi.class);


// Make the GET request
        Call<TipResponse> getCall = api.getTip();

        getCall.enqueue(new Callback<TipResponse>() {
            @Override
            public void onResponse(Call<TipResponse> call, Response<TipResponse> response) {
                if (response.isSuccessful()) {
                    // GET request successful
                    TipResponse tipResponse = response.body();
                    String tip = tipResponse.getTip();

                    // Update the UI with the tip
                    tipTextView.setText(tip);
                }
                startActivity();
            }

            @Override
            public void onFailure(Call<TipResponse> call, Throwable t) {
                // Handle GET request failure
                startActivity();
            }
        });
    }
    public void startActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                if(homeStart) {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    homeStart = false;
                    startActivity(i);
                    overridePendingTransition( R.anim.fade_in, R.anim.fade_out);

                } else if (gameStart) {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity1.class);
                    gameStart = false;
                    startActivity(i);
                    overridePendingTransition( R.anim.fade_in, R.anim.fade_out);

                }
                else  {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition( R.anim.fade_in, R.anim.fade_out);
                }
                // close this activity
                finish();
            }
        }, 3000);

    }
}

