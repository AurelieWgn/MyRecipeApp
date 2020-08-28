package com.example.a3wresto.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a3wresto.R;

public class SplashScreenActivity extends AppCompatActivity {
    private static int TIME_OUT = 5000;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_activity);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                pref = getApplicationContext().getSharedPreferences("connectedUser", Context.MODE_PRIVATE);

                if (pref.getBoolean("isConnected", false)) {

                    //Redirection
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {

                    Intent i = new Intent(SplashScreenActivity.this, ConnexionActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, TIME_OUT);
    }
}
