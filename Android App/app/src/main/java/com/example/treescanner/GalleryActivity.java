package com.example.treescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    DBHelper db;
    Cursor res;
    List<ImageView> imgviewlist;
    Bitmap bitmap;

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
        ArrayList<String> imglist = new ArrayList<>();
        while(res.moveToNext()){
            imglist.add(res.getString(0));
        }
        Collections.shuffle(imglist);
        Collections.shuffle(imgviewlist);
        int[] pos = {1,2,3};
            //TODO fix db entry updation
        //TODO fix threading issues
            new Thread(new Runnable() {
                @Override
                public void run() {
                        for(int i=0;i<6;i++){
                            String img = imglist.get(i);
                            Collections.shuffle(Collections.singletonList(pos));
                            String link = "https://raw.githubusercontent.com/satmxd/VVIM-App/main/data/picdb/"+img+"-"+pos[0]+".png";

                            URL url;
                            try {
                                url = new URL(link);
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            }
                            int loopi = i;
                            try {
                                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            imgviewlist.get(loopi).setImageBitmap(bitmap);
                }
            }}).start();

//            URL url = null;
//            try {
//                url = new URL(link);
//            } catch (MalformedURLException e) {
//                Toast.makeText(GalleryActivity.this, "URL Error, please try again later", Toast.LENGTH_SHORT).show();
//                throw new RuntimeException(e);
//            }
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                imgviewlist.get(i).setImageBitmap(bitmap);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

//            Glide.with(GalleryActivity.this).load(link).into(imgviewlist.get(i));
        }




//    private static class GetImage extends AsyncTask<URL, Integer, Bitmap> {
//        Bitmap bitmap;
//        @Override
//        protected Bitmap doInBackground(URL... urls) {
//            try {
//                bitmap = BitmapFactory.decodeStream(urls[0].openConnection().getInputStream());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return bitmap;
//
//
//            }
//    }


}
