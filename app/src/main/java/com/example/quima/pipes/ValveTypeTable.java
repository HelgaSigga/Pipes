package com.example.quima.pipes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.Comment;

/**
 * Created by Quima on 07/02/2015.
 */
public class ValveTypeTable {

    public static final String tableName = "ValveTypeTable";
    public static final String Id = "Id";
    public static final String Type = "Type";
    public static final String Comment = "Comment";


    private static String[] allColumns = {Id, Type, Comment};


    public static String createStatement(){

        String create = "create table " + tableName + "(" + Id
                + " integer primary key autoincrement, " + Type + " String, " + Comment + " String);";

        return create;
    }

    public static String getTableName(){

        return tableName;

    }
/*
    public static ValveTypeModel createLines(SQLiteDatabase database, String line){
        ContentValues values = new ContentValues();
        values.put(Type, line);
        long insertId = database.insert(tableName, null, values);
        Cursor cursor = database.query(tableName, allColumns, Id + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ValveTypeModel newLine = cursorToValveType(cursor);
        cursor.close();
        return newLine;
    }
*/
    public static List<ValveTypeModel> getAllValveTypes(SQLiteDatabase database){

        List<ValveTypeModel> lines = new ArrayList<ValveTypeModel>();

        Cursor cursor = database.query(tableName, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            ValveTypeModel line = cursorToValveType(cursor);
            lines.add(line);
            cursor.moveToNext();
        }

        cursor.close();
        return lines;

    }
/*
    private static ValveTypeModel cursorToLine(Cursor cursor){
        ValveTypeModel line = new ValveTypeModel();
        line.setId(cursor.getLong(0));
        line.setType(cursor.getString(1));
        return line;
    }
*/
    public static void deleteType(SQLiteDatabase database, ValveTypeModel type){
        long id = type.getId();
        System.out.println("Deleted line with ID: " + id);
        database.delete(tableName, Id + " = " + id, null);
    }



    public static ValveTypeModel createValveType(SQLiteDatabase database, String type, String comment){
        ContentValues values = new ContentValues();
        values.put(Type, type);
        values.put(Comment, comment);
        long insertId = database.insert(tableName, null, values);
        Cursor cursor = database.query(tableName, allColumns, Id + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ValveTypeModel newValveTypeModel = cursorToValveType(cursor);
        cursor.close();
        return newValveTypeModel;
    }

    public static ValveTypeModel createValveTypeFromString(SQLiteDatabase database, String valve){
        String[] valveArray = valve.split(",", 2);
        if(valveArray.length == 2) {
            return createValveType(database, valveArray[0], valveArray[1]);
        } else {
            Log.e(ValveTable.class.getName(), "Could not create valve from line:\n" + valve);
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
                    createValveTypeFromString(database, line);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(ValveTable.class.getName(), "File not found: " + e.toString());
        }
        catch (IOException e){
            Log.e(ValveTable.class.getName(), "Can not read file: " + e.toString());
        }
    }

    private static ValveTypeModel cursorToValveType(Cursor cursor){

        ValveTypeModel valveType = new ValveTypeModel();
        valveType.setId(cursor.getLong(0));
        valveType.setType(cursor.getString(1));
        return valveType;
    }


}
