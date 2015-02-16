package com.example.quima.pipes;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

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

    public ValveTypeModel createValveType(String type, String comment){
        return ValveTypeTable.createValveType(database, type, comment);
    }

    public List<ValveTypeModel> getAllValveTypes(){
        return ValveTypeTable.getAllValveTypes(database);
    }

    public void deleteValveTypeTable(ValveTypeModel type){
        ValveTypeTable.deleteType(database, type);
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

    public void prepareTables(){
        Log.e(Database.class.getName(), "Preparing tables.");
       // ValveTable.prepareTable(database, context, R.raw.test_lokar);
        ValveTable.prepareTable(database, context, R.raw.hitaveita_lokar);
        ValveTypeTable.prepareTable(database, context, R.raw.hitaveita_typur);
    }

/*
    public static String setupValveTypes(){
        String hf = "HF";
        String hb = "HB";
        String hs = "HS";
        ValveTypeTable.createValveType(database, hf);
        ValveTypeTable.createValveType(database, hb);
        ValveTypeTable.createValveType(database, hs);
        return "0";
    }

Reynum að finna betri leið...
    public static String setupValves(){
        ValveTable.createValve(database, "HF", "1", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "1", "2", "Stofn frá Kirkjubraut/Valhúsabraut", "B-1-1", "");
        ValveTable.createValve(database, "HF", "1", "3", "Stofn frá Austurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "1", "4", "Heimæð að kirkju", "B-1-1", "");
        ValveTable.createValve(database, "HF", "1", "5", "Heimæð að Kirkjubraut 4-18", "", "");
        ValveTable.createValve(database, "HF", "1", "6", "Stofn Kirkjubraut að brunni B-1-1", "B-1-2", "");
        ValveTable.createValve(database, "HF", "1", "7", "Heimæð Kirkjubraut 1-13", "B-1-2", "");
        ValveTable.createValve(database, "HF", "1", "8", "Heimæð Skólabraut - Mýrarhúsaskóli , Mýró eldri", "B-1-3", "");
        ValveTable.createValve(database, "HF", "1", "9", "Heimæð Skólabraut 7 - Valhúsaskóli", "B-1-3", "");
        ValveTable.createValve(database, "HF", "1", "10", "Stofn að Suðurströnd", "B-1-3", "");
        ValveTable.createValve(database, "HF", "1", "11", "Heimæð að heilsugæslu", "B-1-4", "");
        ValveTable.createValve(database, "HF", "1", "12", "", "B-1-4", "Við völlinn");
        ValveTable.createValve(database, "HB", "1", "13", "Bakrennsli frá skólunum og íþróttahúsi", "", "Í plani heilsugæslu");
        ValveTable.createValve(database, "HF", "1", "14", "Heimæð íþróttavöllur", "", "Í plani heilsugæslu");
        ValveTable.createValve(database, "HF", "1", "15", "Stofn að suðurströnd", "", "ofar við hús");
        ValveTable.createValve(database, "HF", "1", "16", "Heimæð World Class", "", "neðar við hús");
        ValveTable.createValve(database, "HB", "1", "17", "Bakrennsli World Class", "", "");
        ValveTable.createValve(database, "HF", "1", "18", "Stofn að Suðurströnd við sundlaug", "", "");
        ValveTable.createValve(database, "", "1", "", "Stofn 10m/m að Selbraut", "B-1-5", "Brunnur - enginn krani í brunninum");
        ValveTable.createValve(database, "HF", "1", "19", "Heimæð íþróttahúss við félagsheimili", "", "");
        ValveTable.createValve(database, "HF", "1", "20", "Stofn í Selbraut", "", "");
        ValveTable.createValve(database, "HF", "1", "21", "Heimæð Hrólfsskálavör 7-15", "B-1-6", "");
        ValveTable.createValve(database, "HF", "1", "22", "Heimæð Hrólfsskálamel 2-8", "B-1-7", "");
        ValveTable.createValve(database, "HF", "1", "23", "Stofn að Nesvegi í brunn B-7-3", "B-1-7", "");
        ValveTable.createValve(database, "HF", "1", "24", "Heimæð Hrólfsskálamel 10-18", "B-1-7", "");
        ValveTable.createValve(database, "HF", "1", "25", "Heimæð Mýrarhúsaskóla, nýjasti hluti", "", "");
        ValveTable.createValve(database, "HS", "1", "26", "Sandgildra við enda Kirkjubrautar", "", "");
        ValveTable.createValve(database, "", "1", "", "Snjóbræðsla við tónlistarskóla", "B-1-8", "Ath. loka sem gætu nýst");
        ValveTable.createValve(database, "HF", "1", "27", "Snjóbræðsla Mýróvöllur við Hrólfsskálamel 2", "", "");

        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");
        ValveTable.createValve(database, "HF", "2", "1", "Stofn frá Víkurströnd", "B-1-1", "");




        return "0";
    }
    */
    //type
    //area
    //number
    //location
    //source
    //comment

}
