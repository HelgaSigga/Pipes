package com.example.quima.pipes;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Quima on 12/02/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "valves.db";
    public static final int DATABASE_VERSION = 1;
    private boolean databaseInitialized;

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        databaseInitialized = true;
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(ValveTable.createStatement());
        database.execSQL(ValveTypeTable.createStatement());
        databaseInitialized = false;
    }

    public boolean isInitialized(){
        return databaseInitialized;
    }

    public void databaseHasBeenInitialized(){
        databaseInitialized = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + ValveTable.getTableName());
        database.execSQL("DROP TABLE IF EXISTS " + ValveTypeTable.getTableName());
        onCreate(database);
    }
}
