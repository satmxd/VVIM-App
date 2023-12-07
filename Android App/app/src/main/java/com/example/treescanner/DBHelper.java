package com.example.treescanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBHelper extends SQLiteOpenHelper {
    public Context context;
    private static final String TAG = DBHelper.class.getName();

    public DBHelper(Context context_) {
        super(context_, "plant.db", null, 1);
        //db.execSQL("INSERT INTO PlantDetails (id, name, kingdom, division, class, order_, family, genus, species, details) VALUES ('9d786af4-20d4-11ee-be56-0242ac120002', 'Cannonball tree', 'Plantae', 'Angiosperms', 'Dicots', 'Ericales', 'Lecythidaceae', 'Couroupita', 'C. guianesis', 'This tree is planted as an ornamental for its showy, scented flowers, and as a botanical specimen for its interesting fruit. The fruit is fed to livestock such as pigs and domestic fowl. The fruit is edible, but not usually eaten by people because it can have an unpleasant smell. In India the tree is sacred to Hindus, who believe its hooded flowers look like the n?ga, and it is grown at Shiva temples. There are many medicinal uses for the plant. Native Amazonians use extracts of several parts of the tree to treat hypertension, tumors, pain, and inflammation. It has been used to treat the common cold, stomachache, skin conditions and wounds, malaria, and toothache. The fruit pulp is rubbed on sick dogs to cure them of manage.')");
        context = context_;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table PlantDetails (id TEXT primary key, name TEXT, title TEXT, kingdom TEXT, division TEXT, class TEXT, order_ TEXT, family TEXT, genus TEXT, species TEXT, details TEXT, url TEXT)");
        try {
            this.insertFromFile(context, R.raw.data, DB);
        } catch (IOException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {

    }

    //returns a cursor of the data in database
    public Cursor get_data(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from PlantDetails", null);
        return cursor;
    }

    public Cursor get_from_id(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM PlantDetails WHERE id = ?", new String[]{id});
    }
    public void insertFromFile(Context context, int resourceId, SQLiteDatabase db) throws IOException {

        // Open the resource
        InputStream insertsStream = context.getResources().openRawResource(resourceId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        // Iterate through lines (assuming each insert has its own line and theres no other stuff)
        while (insertReader.ready()) {
            String insertStmt = insertReader.readLine();
            try {
                db.execSQL(insertStmt);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
