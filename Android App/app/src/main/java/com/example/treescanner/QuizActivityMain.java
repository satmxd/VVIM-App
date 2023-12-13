package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class QuizActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        ImageView backbtn = findViewById(R.id.backbutton);
        CardView commonbtn = findViewById(R.id.QuizCommonNames);
        CardView sciencebtn = findViewById(R.id.QuizScientific);
        CardView drugbtn = findViewById(R.id.QuizMedicine);
        CardView imagebtn = findViewById(R.id.QuizPicture);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivityMain.this, MainActivity.class);
                startActivity(intent);
            }
        });

        commonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivityMain.this, QuizActivityResponse.class);
                intent.putExtra("qintent", "common");
                startActivity(intent);
            }
        });

        sciencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivityMain.this, QuizActivityResponse.class);
                intent.putExtra("qintent", "science");
                startActivity(intent);
            }
        });

        drugbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivityMain.this, QuizActivityResponse.class);
                intent.putExtra("qintent", "drugs");
                startActivity(intent);
            }
        });

        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivityMain.this, QuizActivityResponse.class);
                intent.putExtra("qintent", "image");
                startActivity(intent);
            }
        });


    }
}