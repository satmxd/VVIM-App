package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class SplashScreen extends AppCompatActivity {
Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        String[] fx = {"Our school has more than 80 different kinds of trees!", "Trees help keep the air cool!", "Trees are known to help reduce stress!", "App works best with Wi-fi enabled!"};
        Collections.shuffle(Arrays.asList(fx));
        handler = new Handler();
        TextView txt = findViewById(R.id.splashscreentext);
        txt.setText(fx[0]);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}