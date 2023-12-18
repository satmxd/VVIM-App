package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

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
    Integer nlines;
    ArrayList<String> factlist;
    TextView factbox;
    TextView factnamebox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);

        factbox = findViewById(R.id.factbox);
        TextView nextbtn = findViewById(R.id.nextbtn);
        factnamebox = findViewById(R.id.factnamebox);
        treeimg = findViewById(R.id.treeimg);
        db = new DBHelper(this);
        res = db.get_data();

        InputStream input_txt = getBaseContext().getResources().openRawResource(R.raw.facts);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(input_txt));
        factlist = new ArrayList<>();

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            Toast.makeText(this, "Connect to a Wifi network to view Images", Toast.LENGTH_LONG).show();
        }


        String line;
        try {
            line = bfr.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(line != null){
            factlist.add(line);
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
        Collections.shuffle(factlist);
        nlines = factlist.size();
        update();

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }

        });
    }

    private void update(){
        if (count>=nlines){
            count = 0;
        }
        String fact = factlist.get(count);
        String treeno = fact.substring(fact.length()-2, fact.length());
        Integer id = Integer.valueOf(treeno);
        res.moveToPosition(id);
        String qrd = res.getString(0);
        factnamebox.setText(res.getString(1));
        factbox.setText(fact.substring(0, fact.length()-2));


        Glide.with(FactsActivity.this).load("https://raw.githubusercontent.com/satmxd/VVIM-App/main/data/picdb/"+qrd+"-1.png").into(treeimg);
        count+=1;
    }
}