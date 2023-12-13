package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;

public class QRInfoActivity extends AppCompatActivity {
    TextView data;

    private String name;

    private String title;
    private String kingdom;
    private String division;
    private String class_;
    private String order;
    private String family;
    private String genus;
    private String species;
    private String details;
    private String url;
    private static final String TAG = QRInfoActivity.class.getName();



    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrinfo);
        //TODO: backbutton for both origins teleporting to same activity, change.

        String qrd = getIntent().getStringExtra("qrdata");
        String qrd_sliced = qrd.substring(qrd.length()-2, qrd.length());
        Integer id = Integer.valueOf(qrd_sliced);
        ImageView backbtn = findViewById(R.id.backbutton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QRInfoActivity.this, MainActivity.class));
                finish();
            }
        });

        DBHelper db = new DBHelper(this);
        Cursor res = db.get_data();
        res.moveToPosition(id);

        TextView nameCol = findViewById(R.id.treename);
        TextView titlecol = findViewById(R.id.treetitle);
        TextView kingdomCol = findViewById(R.id.treekingdom);
        TextView divisionCol = findViewById(R.id.treedivision);
        TextView classCol = findViewById(R.id.treeclass);
        TextView orderCol = findViewById(R.id.treeorder);
        TextView familyCol = findViewById(R.id.treefamily);
        TextView genusCol = findViewById(R.id.treegenus);
        TextView speciesCol = findViewById(R.id.treespecies);
        TextView detailsCol = findViewById(R.id.treedetails);
        ImageView treeimg = findViewById(R.id.dispimg);



        if (!res.toString().equals(" ")) {
            Toast.makeText(QRInfoActivity.this, "Entry exists", Toast.LENGTH_SHORT).show();
            name = res.getString(1);
            title = res.getString(2);
            kingdom = res.getString(3);
            division = res.getString(4);
            class_ = res.getString(5);
            order = res.getString(6);
            family = res.getString(7);
            genus = res.getString(8);
            species = res.getString(9);
            details = res.getString(10);
            url = res.getString(11);

            nameCol.setText(name);
            titlecol.setText(title);

            kingdomCol.setText("Kingdom: " + kingdom);

            divisionCol.setText("Division: " + division);

            classCol.setText("Class: " + class_);

            orderCol.setText("Order: " + order);

            familyCol.setText("Family: " + family);

            genusCol.setText("Genus: " + genus);

            speciesCol.setText("Species: " + species);

            detailsCol.setText(details);

            Glide.with(QRInfoActivity.this).load(url).centerCrop().into(treeimg);


        } else {
            Toast.makeText(QRInfoActivity.this, "Entry does not exist", Toast.LENGTH_SHORT).show();
        }
    }


}