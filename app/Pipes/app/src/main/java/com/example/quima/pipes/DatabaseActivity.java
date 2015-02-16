package com.example.quima.pipes;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

/**
 * Created by Quima on 07/02/2015.
 */
public class DatabaseActivity extends ListActivity {

   private Database database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databaseactivity);


        database = new Database(this);
        try{
            database.open();
        } catch(SQLException e){
            Log.e(DatabaseActivity.class.getName(), "Failed to open SQLite database: " + e.toString());
        }

        List<ValveModel> valves = database.getAllValves();

        ArrayAdapter<ValveModel> adapter = new ArrayAdapter<ValveModel>(this, android.R.layout.simple_expandable_list_item_1, valves);
        setListAdapter(adapter);
    }

    public void onClick(View view){
        ArrayAdapter<ValveModel> adapter = (ArrayAdapter<ValveModel>) getListAdapter();
        ValveModel valveModel = null;
        switch (view.getId()){
            case R.id.add:
                String[] valves = new String[] {"HF", "HB", "HS"};  //Need to fix this
                int nextInt = new Random().nextInt(3); //Need to fix this
                valveModel = database.createValve("", "", "", "", "", "" ); //Need to fix this
                adapter.add(valveModel);
                break;
            case R.id.delete:
                if(getListAdapter().getCount() > 0){
                    valveModel = (ValveModel) getListAdapter().getItem(0);
                    database.deleteValveTable(valveModel);
                    adapter.remove(valveModel);
                }
                break;
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume(){
        database.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        database.close();
        super.onPause();
    }

}