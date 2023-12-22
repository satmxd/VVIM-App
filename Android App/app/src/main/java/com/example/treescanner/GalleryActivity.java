package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    DBHelper db;
    Cursor res;
    List<ImageView> imgviewlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imgviewlist = new ArrayList<>();
        imgviewlist.add(0, findViewById(R.id.galleryimg1));
        imgviewlist.add(1, findViewById(R.id.galleryimg2));
        imgviewlist.add(2, findViewById(R.id.galleryimg3));
        imgviewlist.add(3, findViewById(R.id.galleryimg4));
        imgviewlist.add(4, findViewById(R.id.galleryimg5));
        imgviewlist.add(5, findViewById(R.id.galleryimg6));


        ImageView backbtn = findViewById(R.id.backbutton);

        db = new DBHelper(this);
        res = db.get_data();


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GalleryActivity.this, MainActivity.class));
                finish();
            }
        });

        update();
    }
    private void update(){
        res.moveToFirst();
        ArrayList<String> imglist = new ArrayList<String>();
        while(res.moveToNext()){
            imglist.add(res.getString(0));
        }
        Collections.shuffle(imglist);
        Collections.shuffle(imgviewlist);
        int[] pos = {1,2,3};
        for(int i=0;i<6;i++){
            String img = imglist.get(i);
            Collections.shuffle(Collections.singletonList(pos));
            Glide.with(GalleryActivity.this).load("https://raw.githubusercontent.com/satmxd/VVIM-App/main/data/picdb/"+img+"-"+pos[0]+".png").into(imgviewlist.get(i));
        }

    }
}