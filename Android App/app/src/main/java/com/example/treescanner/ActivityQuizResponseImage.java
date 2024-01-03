package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ActivityQuizResponseImage extends AppCompatActivity {

    DBHelper db;
    Cursor res;
    Integer count = 0;
    TextView questionBox;
    List<TextView> optBox;

    TextView answer;
    ImageView imageBox;
    static List<TextView> optBox_Numbered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_response_image);
        db = new DBHelper(this);
        questionBox = findViewById(R.id.question);
        Button nextq = findViewById(R.id.quiznext);
        imageBox = findViewById(R.id.imageQuestion);
        optBox = new ArrayList<>();
        optBox.add(0, findViewById(R.id.option1));
        optBox.add(1, findViewById(R.id.option2));
        optBox.add(2, findViewById(R.id.option3));
        optBox.add(3, findViewById(R.id.option4));
        ImageView backbtn = findViewById(R.id.backbutton);
        res = db.get_data();
        optBox_Numbered = new ArrayList<>(optBox);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityQuizResponseImage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        optBox_Numbered.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer == optBox_Numbered.get(0)){
                    Toast.makeText(ActivityQuizResponseImage.this, "correct answer", Toast.LENGTH_SHORT).show();
                    answer.setBackgroundColor(Color.parseColor("#00FF00"));

                    update();
                }else{
                    optBox_Numbered.get(0).setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        });
        optBox_Numbered.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer == optBox_Numbered.get(1)){
                    Toast.makeText(ActivityQuizResponseImage.this, "correct answer", Toast.LENGTH_SHORT).show();
                    answer.setBackgroundColor(Color.parseColor("#00FF00"));

                    update();

                }else{
                    optBox_Numbered.get(1).setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        });
        optBox_Numbered.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer == optBox_Numbered.get(2)){
                    Toast.makeText(ActivityQuizResponseImage.this, "correct answer", Toast.LENGTH_SHORT).show();
                    answer.setBackgroundColor(Color.parseColor("#00FF00"));

                    update();

                }else{
                    optBox_Numbered.get(2).setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        });
        optBox_Numbered.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer == optBox_Numbered.get(3)){
                    Toast.makeText(ActivityQuizResponseImage.this, "correct answer", Toast.LENGTH_SHORT).show();
                    answer.setBackgroundColor(Color.parseColor("#00FF00"));

                    update();
                }else{
                    optBox_Numbered.get(3).setBackgroundColor(Color.parseColor("#FF0000"));

                }
            }
        });
        this.update();

    }

    public void onclick(View view){
        count += 1;
        this.update();
    }

    private List<Pair<String, String>> ImageQuestion(){
        res.moveToFirst();
        List<Pair<String, String>> toreturn = new ArrayList<>();
        toreturn.add(new Pair<>(res.getString(1), res.getString(0)));
        while(res.moveToNext()){
            toreturn.add(new Pair<>(res.getString(1), res.getString(0)));
        }
        return toreturn;
    }

    public void update(){
        optBox.get(0).setBackgroundColor(Color.parseColor("#1E1E1E"));
        optBox.get(1).setBackgroundColor(Color.parseColor("#1E1E1E"));
        optBox.get(2).setBackgroundColor(Color.parseColor("#1E1E1E"));
        optBox.get(3).setBackgroundColor(Color.parseColor("#1E1E1E"));
        List<Pair<String, String>> questions =  ImageQuestion();
        Collections.shuffle(questions);
        questionBox.setText("Guess which tree this is");
        Collections.shuffle(optBox);
        Random rand = new Random();
        Glide.with(ActivityQuizResponseImage.this).load("https://raw.githubusercontent.com/satmxd/VVIM-App/main/data/picdb/"+questions.get(0).second+"-"+ (1+(int)(Math.random()*3)) +".png").into(imageBox);
        optBox.get(0).setText(questions.get(0).first);
        optBox.get(1).setText(questions.get(1).first);
        optBox.get(2).setText(questions.get(2).first);
        optBox.get(3).setText(questions.get(3).first);
        answer = optBox.get(0);
    }
}