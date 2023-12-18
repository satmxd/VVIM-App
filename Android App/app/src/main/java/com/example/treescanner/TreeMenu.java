package com.example.treescanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;


public class TreeMenu extends AppCompatActivity {

    ListView listView;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> qrid = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_menu);
        listView = findViewById(R.id.treeList);
        DBHelper db = new DBHelper(this);
        Cursor data = db.get_data();
        int c = 1;
        while(data.moveToNext()){
            names.add(String.valueOf(c)+". "+data.getString(data.getColumnIndex("name")));
            qrid.add(data.getString(data.getColumnIndex("id")));
            c++;
        }
        data.close();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.white_text, names);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TreeMenu.this, QRInfoActivity.class);
                String e = qrid.get(i);
                intent.putExtra("qrdata", e);
                intent.putExtra("origin", "menu");
                startActivity(intent);
            }
        });

        ImageView backbtn = findViewById(R.id.backbutton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TreeMenu.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        Log.d("e", "inflated");
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("search tree");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu);
        return true;
    }
}