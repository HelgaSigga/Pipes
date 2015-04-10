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
    public static final String Category = "Category";

    private static final String TAG = ValveTable.class.getName();

    public static final int HOT_ID = 0;
    public static final int COLD_ID = 1;
    public static final int DRAINAGE_ID = 2;

    private static String[] allColumns = {Id, Type, Comment, Category};


    public static String createStatement(){

        String create = "create table " + tableName + "(" + Id
                + " integer primary key autoincrement, " + Type + " String, " + Comment + " String, " + Category + " integer);";

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

    public static List<String> getAllTypesByCategory(SQLiteDatabase database, int category){
        List<String> types = new ArrayList<String>();
        String[] columns = {Type};
        String select = Type + " IS NOT NULL AND " + Category + "=" + category;
        Cursor cursor = database.query(tableName, columns, select, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            types.add(cursor.getString(0));
            cursor.moveToNext();
        }
        return types;
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



    public static ValveTypeModel createValveType(SQLiteDatabase database, String type, String comment, int category){
        if(type==null){
            Log.e(TAG, "Encountered null value for " + Type + " while creating ValveType");
            type = "";
        }
        if(comment==null) {
            Log.e(TAG, "Encountered null value for " + Comment + " while creating ValveType");
            comment = "";
        }
        ContentValues values = new ContentValues();
        values.put(Type, type);
        values.put(Comment, comment);
        values.put(Category, category);
        long insertId = database.insert(tableName, null, values);
        Cursor cursor = database.query(tableName, allColumns, Id + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ValveTypeModel newValveTypeModel = null;
        if(cursor.getCount()>0) {
            newValveTypeModel = cursorToValveType(cursor);
        } else {
            Log.e(TAG, "Could not find ValveType " + insertId + " just after inserting it.");
        }
        cursor.close();
        return newValveTypeModel;
    }

    public static ValveTypeModel createValveTypeFromString(SQLiteDatabase database, String valve, int category){
        String[] valveArray = valve.split(",", 2);
        if(valveArray.length == 2) {
            return createValveType(database, valveArray[0], valveArray[1], category);
        } else {
            Log.e(ValveTable.class.getName(), "Could not create valve from line:\n" + valve);
            return null;
        }
    }

    public static void prepareTable(SQLiteDatabase database, Context context, int resource, int category){
        Log.e(TAG, "Opening resource file");
        try{
            InputStream inputStream = context.getResources().openRawResource(resource);

            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputStreamReader);
                String line = "";

                while((line = br.readLine()) != null){
                    createValveTypeFromString(database, line, category);
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

    private static ValveTypeModel cursorToValveType(Cursor cursor){

        Log.e(TAG, cursor.toString());
        ValveTypeModel valveType = new ValveTypeModel();
        valveType.setId(cursor.getLong(0));
        valveType.setType(cursor.getString(1));
        valveType.setComment(cursor.getString(2));
        valveType.setCategory(cursor.getInt(3));
        return valveType;
    }


}
