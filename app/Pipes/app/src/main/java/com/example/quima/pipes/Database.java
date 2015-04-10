package com.example.quima.pipes;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Quima on 07/02/2015.
 */
public class Database{

    private static SQLiteDatabase database;
    private SQLiteHelper helpMe;
    private Context context;

    public Database(Context context){
        this.context = context;
        helpMe = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = helpMe.getWritableDatabase();
        //helpMe.onUpgrade(database, 1, 1); // For debug purposes only, remove line
        if(!helpMe.isInitialized()){
            prepareTables();
            helpMe.databaseHasBeenInitialized();
        }
    }

    public void close(){

        helpMe.close();

    }

    public ValveTypeModel createValveType(String type, String comment, int category){
        return ValveTypeTable.createValveType(database, type, comment, category);
    }

    public List<ValveTypeModel> getAllValveTypes(){
        return ValveTypeTable.getAllValveTypes(database);
    }

    public List<ValveModel> getValvesByCategory(int category){
        return ValveTable.getValvesByCategory(database, category);
    }

    public void deleteValveTypeTable(ValveTypeModel type){
        ValveTypeTable.deleteType(database, type);
    }

    public List<ValveModel> getValvesByArea(String area){
        return ValveTable.getValvesByArea(database, area);
    }

    public List<ValveModel> getValvesByAreaAndCategory(String area, int category){
        return ValveTable.getValvesByAreaAndCategory(database, area, category);
    }

    public List<ValveModel> getValvesByString(String raw){
        return ValveTable.getValvesByString(database, raw);
    }

    public List<ValveModel> getValvesByStringAndCategory(String raw, int category){
        return ValveTable.getValvesByStringAndCategory(database, raw, category);
    }

    public ArrayList<String> getAreas(){
        return ValveTable.getAreas(database);
    }

    public ValveModel createValve(String type, String area, String number, String location, String source, String comment){
        return ValveTable.createValve(database, type, area, number, location, source, comment);
    }

    public List<ValveModel> getAllValves(){
        return ValveTable.getAllValves(database);
    }

    public void deleteValveTable(ValveModel valve){
        ValveTable.deleteValve(database, valve);
    }

    public ValveModel editValve(ValveModel valve){
        return ValveTable.updateValve(database, valve);
    }

    /*
    public List<ColdValveModel> getValvesByArea(String area){
        return ColdValveTable.getValvesByArea(database, area);
    }

    public List<ColdValveModel> getValvesByString(String raw) {
        return ColdValveTable.getValvesByString(database, raw);
    }

    public ArrayList<String> getAreas(){
            return ColdValveTable.getAreas(database);
    }

    public ColdValveModel createValve(String type, String area, String number, String location, String comment){
        return ColdValveTable.createValve(database, type, area, number, location, comment);
    }

    public List<ColdValveModel> getAllValves(){
        return ColdValveTable.getAllValves(database);
    }

    public void deleteColdValveTable(ColdValveModel valve){
        ColdValveTable.deleteValve(database, valve);
    }

    public ColdValveModel editValve(ColdValveModel valve){
        return ColdValveTable.updateValve(database, valve);
    }

*/

    public void prepareTables(){
        Log.e(Database.class.getName(), "Preparing tables.");
       // ValveTable.prepareTable(database, context, R.raw.test_lokar);
        ValveTable.prepareTable(database, context, R.raw.hitaveita_lokar);
        ValveTypeTable.prepareTable(database, context, R.raw.hitaveita_typur, ValveTypeTable.HOT_ID);
        ValveTable.prepareTable(database, context, R.raw.kalt_lokar);
        ValveTypeTable.prepareTable(database, context, R.raw.kalt_typur, ValveTypeTable.COLD_ID);
        ValveTable.prepareTable(database, context, R.raw.frarennsli_lokar);
        ValveTypeTable.prepareTable(database, context, R.raw.frarennsli_typur, ValveTypeTable.DRAINAGE_ID);
        List<ValveTypeModel> types = ValveTypeTable.getAllValveTypes(database);
        Log.e("Database", "Listing types, count " + types.size());
        for(ValveTypeModel type: types){
            Log.e("Database", ValveTypeModel.id + " -- " + ValveTypeModel.type + "::" + ValveTypeModel.category);
        }
    }


}
