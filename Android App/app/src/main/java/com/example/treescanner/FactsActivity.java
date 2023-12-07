package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class FactsActivity extends AppCompatActivity {

    private int count = 0;
    ImageView treeimg;
    DBHelper db;
    Cursor res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        TextView factbox = findViewById(R.id.factbox);
        TextView nextbtn = findViewById(R.id.nextbtn);
        treeimg = findViewById(R.id.treeimg);
        db = new DBHelper(this);
        res = db.get_data();

        InputStream input_txt = getBaseContext().getResources().openRawResource(R.raw.facts);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(input_txt));
        ArrayList<String> fatlist = new ArrayList<>();

        String line;
        try {
            line = bfr.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(line != null){
            fatlist.add(line);
            try {
                line = bfr.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            bfr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.shuffle(fatlist);
        int nlines = fatlist.size();

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count>nlines){
                    count = 0;
                }
                String fact = fatlist.get(count);
                String treeno = fact.substring(fact.length()-2, fact.length());
                Integer id = Integer.valueOf(treeno);
                factbox.setText(fact);
                res.moveToPosition(id);
                String url = res.getString(11);
                Glide.with(FactsActivity.this).load(url).centerCrop().into(treeimg);
            }

        });
    }
}