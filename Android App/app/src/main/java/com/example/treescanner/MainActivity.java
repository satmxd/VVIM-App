package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CardView qrscan;
    CardView exitbtn;
    CardView searchbtn;
    CardView quizbtn;
    CardView factsbtn;
    CardView gallerybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();


        qrscan = findViewById(R.id.QRScanbutton);
        exitbtn = findViewById(R.id.ExitButton);
        searchbtn = findViewById(R.id.searchbtn);
        quizbtn = findViewById(R.id.Quizbtn);
        factsbtn= findViewById(R.id.FactsButton);
        gallerybtn = findViewById(R.id.GalleryButton);
        qrscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,QRScanActivity.class));
                    finish();



            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TreeMenu.class));
                finish();
            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        quizbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QuizActivityMain.class));
                finish();
            }
        });

        factsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FactsActivity.class));
                finish();
            }
        });

        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected){
                    startActivity(new Intent(MainActivity.this,GalleryActivity.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Please connect to a wifi network", Toast.LENGTH_SHORT).show();
                }

            }
        });

        }


    }
