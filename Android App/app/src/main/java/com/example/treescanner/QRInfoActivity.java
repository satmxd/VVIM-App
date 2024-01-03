package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
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
    private String origin ;
    private static final String TAG = QRInfoActivity.class.getName();



    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrinfo);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            Toast.makeText(this, "Connect to a Wifi network to view Images", Toast.LENGTH_LONG).show();
        }

        origin = getIntent().getStringExtra("origin");
        String qrd = getIntent().getStringExtra("qrdata");
        Log.d("qrd", qrd);
        Integer id = Integer.valueOf(qrd);
        ImageView backbtn = findViewById(R.id.backbutton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(origin.equals("qrscan")){
                    startActivity(new Intent(QRInfoActivity.this, QRScanActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(QRInfoActivity.this, TreeMenu.class));
                    finish();
                }
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
        ImageView treeimg1 = findViewById(R.id.dispimg1);
        ImageView treeimg2 = findViewById(R.id.dispimg2);
        ImageView treeimg3 = findViewById(R.id.dispimg3);




        if (!res.toString().equals(" ")) {
            Toast.makeText(QRInfoActivity.this, "Entry exists", Toast.LENGTH_SHORT).show();
            String idd = res.getString(0);
            name = res.getString(1);
            title = res.getString(2);
            kingdom = res.getString(3);
            SpannableString skingdom = new SpannableString("Kingdom: " +kingdom);
            division = res.getString(4);
            SpannableString sdivison = new SpannableString("Division: "+division);
            class_ = res.getString(5);
            SpannableString sclass_ = new SpannableString("Class: "+class_);
            order = res.getString(6);
            SpannableString sorder = new SpannableString("Order: "+order);
            family = res.getString(7);
            SpannableString sfamily = new SpannableString("Family: "+family);
            genus = res.getString(8);
            SpannableString sgenus = new SpannableString("Genus: "+genus);
            species = res.getString(9);
            SpannableString sspecies = new SpannableString("Species: "+species);
            details = res.getString(10);

            nameCol.setText(name);
            titlecol.setText(title);
            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

            skingdom.setSpan(boldSpan, 0, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            kingdomCol.setText(skingdom);

            sdivison.setSpan(boldSpan, 0, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            divisionCol.setText(sdivison);

            sclass_.setSpan(boldSpan, 0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            classCol.setText(sclass_);

            sorder.setSpan(boldSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            orderCol.setText(sorder);

            sfamily.setSpan(boldSpan, 0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            familyCol.setText(sfamily);

            sgenus.setSpan(boldSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            genusCol.setText(sgenus);

            sspecies.setSpan(boldSpan, 0, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            speciesCol.setText(sspecies);

            detailsCol.setText(details);


            Glide.with(QRInfoActivity.this).load("https://raw.githubusercontent.com/satmxd/VVIM-App/main/data/picdb/"+idd+"-1.png").into(treeimg1);
            Glide.with(QRInfoActivity.this).load("https://raw.githubusercontent.com/satmxd/VVIM-App/main/data/picdb/"+idd+"-2.png").into(treeimg2);
            Glide.with(QRInfoActivity.this).load("https://raw.githubusercontent.com/satmxd/VVIM-App/main/data/picdb/"+idd+"-3.png").into(treeimg3);


        } else {
            Toast.makeText(QRInfoActivity.this, "Entry does not exist", Toast.LENGTH_SHORT).show();
        }
    }


}