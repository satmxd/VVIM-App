package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuizActivityResponse extends AppCompatActivity {

    DBHelper db;
    Cursor res;
    Integer count = 0;
    TextView questionBox;
    TextView optBox[];
    String qintent;
    List<Integer> commonNameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_response);
        db = new DBHelper(this);
        questionBox = findViewById(R.id.question);
        Button nextq = findViewById(R.id.quiznext);
        optBox = new TextView[]{findViewById(R.id.option1), findViewById(R.id.option2), findViewById(R.id.option3), findViewById(R.id.option4)};
        res = db.get_data();
        qintent = getIntent().getStringExtra("qintent");
        commonNameList = CommonNames();
        Collections.shuffle(commonNameList);
        this.update(qintent);

    }
    public void onclick(View view){
        count += 1;
        this.update(qintent);
    }


    private List<Integer> CommonNames(){
        res.moveToFirst();
        String common;
        List<Integer> names = new ArrayList<>();
        while(res.moveToNext()){
            common = res.getString(3);
            if (!common.equals("")){
                names.add(res.getPosition());
            }
        }
        Collections.shuffle(names);
        return names;
    }

    private List<Integer> ScientificAndImage(){
        res.moveToFirst();
        List<Integer> toreturn =IntStream.range(0, res.getCount()).boxed().collect(Collectors.toList());
        Collections.shuffle(toreturn);
        return toreturn;
    }

    private Map<Integer, String> MedicalQuestions(){
        res.moveToFirst();
        InputStream input_txt = getBaseContext().getResources().openRawResource(R.raw.drugs);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(input_txt));
        Map<Integer, String> toreturn = Collections.emptyMap();

        String line;
        try {
            line = bfr.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(line != null){
            String qrd_sliced = line.substring(line.length()-2, line.length());
            Integer id = Integer.valueOf(qrd_sliced);
            toreturn.put(id, line);
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
        while(res.moveToNext()){
             tree.add(res.getString(2));
        }
        return tree;
    }


    private void update(String type){
        String answer;
        List<String> options = new ArrayList<>();
        if(type.equals("common")){
            Integer questionCounter = commonNameList.get(count);
            res.moveToPosition(questionCounter);
            String question = res.getString(1);
            questionBox.setText(question);
            answer = res.getString(2);
            List<Integer> cache = commonNameList;
            Collections.shuffle(cache);
            String commonNameCache;
            for(int counter = 0; counter<3; counter++){
                res.moveToPosition(cache.get(counter));
                commonNameCache = res.getString(2);
                if (!commonNameCache.equals(answer)){
                    options.add(commonNameCache);
                }else{
                    counter--;
                }
            }
            options.add(answer);
            Collections.shuffle(options);
            for(int i = 0; i<4; i++){
                optBox[i].setText(options.get(i));
            }

        }

    }
}