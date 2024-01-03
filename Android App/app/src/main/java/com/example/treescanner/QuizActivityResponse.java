package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivityResponse extends AppCompatActivity {

    DBHelper db;
    Cursor res;
    Integer count = 0;
    TextView questionBox;
    List<TextView> optBox;

    TextView answer;
    String qintent;

    LinearLayout mainlyt;
    ImageView imgbox;
    static List<TextView> optBox_Numbered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_response);
        db = new DBHelper(this);
        questionBox = findViewById(R.id.question);
        optBox = new ArrayList<>();
        optBox.add(0, findViewById(R.id.option1));
        optBox.add(1, findViewById(R.id.option2));
        optBox.add(2, findViewById(R.id.option3));
        optBox.add(3, findViewById(R.id.option4));




        ImageView backbtn = findViewById(R.id.backbutton);
        optBox_Numbered = new ArrayList<>(optBox);
        res = db.get_data();
        qintent = getIntent().getStringExtra("qintent");
        if (qintent.equals("image")){
            mainlyt = findViewById(R.id.quizlinearlayout);
            imgbox = new ImageView(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(700,700);
            layoutParams.gravity = Gravity.CENTER;
            imgbox.setLayoutParams(layoutParams);
            imgbox.setPadding(20, 20, 20, 5);
            mainlyt.addView(imgbox, 1);
        }
        this.update(qintent);
        Toast.makeText(this, qintent, Toast.LENGTH_SHORT).show();

        optBox_Numbered.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer == optBox_Numbered.get(0)){
                    Toast.makeText(QuizActivityResponse.this, "correct answer", Toast.LENGTH_SHORT).show();
                    answer.setBackgroundColor(Color.parseColor("#00FF00"));

                    update(qintent);
                }else{
                    optBox_Numbered.get(0).setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        });
        optBox_Numbered.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer == optBox_Numbered.get(1)){
                    Toast.makeText(QuizActivityResponse.this, "correct answer", Toast.LENGTH_SHORT).show();
                    answer.setBackgroundColor(Color.parseColor("#00FF00"));

                    update(qintent);

                }else{
                    optBox_Numbered.get(1).setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        });
        optBox_Numbered.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer == optBox_Numbered.get(2)){
                    Toast.makeText(QuizActivityResponse.this, "correct answer", Toast.LENGTH_SHORT).show();
                    answer.setBackgroundColor(Color.parseColor("#00FF00"));

                    update(qintent);

                }else{
                    optBox_Numbered.get(2).setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        });
        optBox_Numbered.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer == optBox_Numbered.get(3)){
                    Toast.makeText(QuizActivityResponse.this, "correct answer", Toast.LENGTH_SHORT).show();
                    answer.setBackgroundColor(Color.parseColor("#00FF00"));

                    update(qintent);
                }else{
                    optBox_Numbered.get(3).setBackgroundColor(Color.parseColor("#FF0000"));

                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivityResponse.this, QuizActivityMain.class);
                startActivity(intent);
            }
        });
    }
    public void onclick(View view){
        count += 1;
        this.update(qintent);
    }


    private Pair<String[], String[]> CommonNames(){
        res.moveToFirst();
        String common;
        List<String[]> NameMap = new ArrayList<>();
        while(res.moveToNext()){
            common = res.getString(2);
            if (!common.equals("")){
                NameMap.add(new String[]{common, res.getString(1)});
            }
        }
        Collections.shuffle(NameMap);
        String[] correct = NameMap.get(0);
        String[] options = new String[]{NameMap.get(1)[0], NameMap.get(2)[0], NameMap.get(3)[0]};

        return new Pair<>(correct, options);
    }

    private Pair<String[], String[]> ScientificNames(){
        res.moveToFirst();
        String science;
        List<String[]> NameMap = new ArrayList<>();
        while(res.moveToNext()){
            science = res.getString(9);
            if (!science.equals("")){
                NameMap.add(new String[]{science, res.getString(1)});
            }
        }
        Collections.shuffle(NameMap);
        String[] correct = NameMap.get(0);
        String[] options = new String[]{NameMap.get(1)[0], NameMap.get(2)[0], NameMap.get(3)[0]};

        return new Pair<>(correct, options);
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


    private List<Pair<Integer, String>> MedicalQuestions(){
        res.moveToFirst();
        InputStream input_txt = getBaseContext().getResources().openRawResource(R.raw.medical);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(input_txt));
        List<Pair<Integer, String>> toreturn = new ArrayList<>();

        String line;
        try {
            line = bfr.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(line != null){
            String qrd_sliced = line.substring(line.length()-2);
            Integer id = Integer.valueOf(qrd_sliced);
            toreturn.add(new Pair<>(id, line));
            try {
                line = bfr.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return toreturn;
    }

    private List<String> getTreeNames(){
        List<String> tree = new ArrayList<>();
        res.moveToFirst();
        tree.add(res.getString(1));
        while(res.moveToNext()){
            tree.add(res.getString(1));
        }
        return tree;
    }


    private void update(String type){
        optBox.get(0).setBackgroundColor(Color.parseColor("#1E1E1E"));
        optBox.get(1).setBackgroundColor(Color.parseColor("#1E1E1E"));
        optBox.get(2).setBackgroundColor(Color.parseColor("#1E1E1E"));
        optBox.get(3).setBackgroundColor(Color.parseColor("#1E1E1E"));

        if(type.equals("common")){
            Pair<String [], String[]> getVal = CommonNames();
            String[] ans_list = getVal.first;
            String[] options_list = getVal.second;
            questionBox.setText("What is the common name of " + ans_list[1]+ " ?");
            Collections.shuffle(optBox);
            optBox.get(0).setText(options_list[0]);
            optBox.get(1).setText(options_list[1]);
            optBox.get(2).setText(options_list[2]);
            optBox.get(3).setText(ans_list[0]);
            answer = optBox.get(3);
        }
        else if(type.equals("science")){
            Pair<String [], String[]> getVal = ScientificNames();
            String[] ans_list = getVal.first;
            String[] options_list = getVal.second;
            questionBox.setText("What is the scientific name of " + ans_list[1]+ " ?");
            Collections.shuffle(optBox);
            optBox.get(0).setText(options_list[0]);
            optBox.get(1).setText(options_list[1]);
            optBox.get(2).setText(options_list[2]);
            optBox.get(3).setText(ans_list[0]);
            answer = optBox.get(3);
        }
        else if(type.equals("drugs")){
            List<Pair<Integer, String>> questions = MedicalQuestions();
            List<String> treenames = getTreeNames();
            Collections.shuffle(questions);
            Pair<Integer, String> ques = questions.get(0);
            questionBox.setText(ques.second.substring(0, ques.second.length()-2));
            Collections.shuffle(optBox);
            optBox.get(0).setText(treenames.remove(ques.first.intValue()));
            Collections.shuffle(treenames);
            optBox.get(1).setText(treenames.get(0));
            optBox.get(2).setText(treenames.get(1));
            optBox.get(3).setText(treenames.get(2));
            answer = optBox.get(3);
        }
        else if(type.equals("image")){
            List<Pair<String, String>> questions =  ImageQuestion();
            Collections.shuffle(questions);
            questionBox.setText("Can you guess the name of this tree?");




            Collections.shuffle(optBox);
            Random rand = new Random();
            String url = "https://raw.githubusercontent.com/satmxd/VVIM-App/main/data/picdb/"+questions.get(0).second+"-"+ (rand.nextInt(3)+1) +".png";
            Glide.with(QuizActivityResponse.this).load(url).into(imgbox);
            Log.d("url", url);
            optBox.get(0).setText(questions.get(0).first);
            optBox.get(1).setText(questions.get(1).first);
            optBox.get(2).setText(questions.get(2).first);
            optBox.get(3).setText(questions.get(3).first);
            answer = optBox.get(0);
        }
    }




}
