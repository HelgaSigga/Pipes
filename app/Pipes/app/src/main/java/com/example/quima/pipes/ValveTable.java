package com.example.quima.pipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.app.Activity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quima on 07/02/2015.
 */
public class ValveTable {

    public static final String TAG = ValveTable.class.getName();
    public static final String tableName = "ValveTable";
    public static final String Id = "Id";
    public static final String Type = "Type";
    public static final String Area = "Area";
    public static final String Number = "Number";
    public static final String Location = "Location";
    public static final String Source = "Source";
    public static final String Comment = "Comment";


    private static String[] allColumns = {Id, Type, Area, Number, Location, Source, Comment};



    public static String createStatement(){
        String create = "create table " + tableName + "(" + Id + " integer primary key autoincrement, "
                + Type + " String, " + Area + " Integer, " + Number + " Integer, "
                + Location + " String, " + Source + " String, "
                + Comment + " String);";

        return create;
    }

    public static String getTableName(){

        return tableName;

    }

    public static ValveModel createValve(SQLiteDatabase database, String type, String area, String number, String location, String source, String comment){
        ContentValues values = new ContentValues();
        values.put(Type, type);
        values.put(Area, area);
        values.put(Number, number);
        values.put(Location, location);
        values.put(Source, source);
        values.put(Comment, comment);
        long insertId = database.insert(tableName, null, values);
        Log.e(TAG, "Content value as string:\n\t" + values.toString() + "\nID was " + insertId + ".");
        Cursor cursor = database.query(tableName, allColumns, Id + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ValveModel newValveModel = cursorToValve(cursor);
        cursor.close();

        Log.e(TAG, "Inserted as:\n\t" + newValveModel.toString());

        return newValveModel;
    }

    public static ValveModel createValveFromString(SQLiteDatabase database, String valve){

        Log.e(TAG, "Read from file:\n\t" + valve);

        String[] valveArray = valve.split(",", 6);
        if(valveArray.length == 6) {
            Log.e(TAG, "Creating valve:\n\t" + valveArray[0] + "  " + valveArray[1] + "-" + valveArray[2] + "\n\tLocation: " +
                    valveArray[3] + "\n\tSource: " + valveArray[4] + "\n\tComment:" + valveArray[5]);
            return createValve(database, valveArray[0], valveArray[1], valveArray[2],
                    valveArray[3], valveArray[4], valveArray[5]);
        } else {
            Log.e(TAG, "Could not create valve from line:\n" + valve);
            return null;
        }
    }

    public static void prepareTable(SQLiteDatabase database, Context context, int resource){

        try{
            InputStream inputStream = context.getResources().openRawResource(resource);

            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputStreamReader);
                String line = "";

                while((line = br.readLine()) != null){
                    createValveFromString(database, line);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        }
        catch (IOException e){
            Log.e(TAG, "Can not read file: " + e.toString());
        }
    }

    public static List<ValveModel> getAllValves (SQLiteDatabase database){

        List<ValveModel> valves = new ArrayList<ValveModel>();

        Cursor cursor = database.query(tableName, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            ValveModel valve = cursorToValve(cursor);
            valves.add(valve);
            cursor.moveToNext();
        }

        cursor.close();
        return valves;

    }

    public static void deleteValve(SQLiteDatabase database, ValveModel type){
        long id = type.getId();
        System.out.println("Deleted line with ID: " + id);
        database.delete(tableName, Id + " = " + id, null);
    }

    private static ValveModel cursorToValve(Cursor cursor) {
        ValveModel valve = new ValveModel();
        valve.setId(cursor.getLong(0));
        valve.setType(cursor.getString(1));
        valve.setArea(cursor.getInt(2));
        valve.setNumber(cursor.getInt(3));
        valve.setLocation(cursor.getString(4));
        valve.setSource(cursor.getString(5));
        valve.setComment(cursor.getString(6));
        return valve;
    }
}
