package com.example.quima.pipes;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;


/**
 * Created by Quima on 07/02/2015.
 */
public class DatabaseActivity extends ListActivity {

    private Database database;
    private Activity activity;

    public static final String KEY_ACTION = "query type";
    public static final String KEY_SEARCH = "search string";
    public static final int VAL_ALL = 0;
    public static final int VAL_AREA = 1;
    public static final int VAL_SEARCH = 2;
    public static final int VAL_ADDRESS = 3;
    public static final String KEY_CATEGORY = "query category";

    private int action;
    private String raw;
    private int category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databaseactivity);

        final ListView listView = getListView();

        database = new Database(this);
        try{
            database.open();
        } catch(SQLException e){
            Log.e(DatabaseActivity.class.getName(), "Failed to open SQLite database: " + e.toString());
        }
        activity = this;
        action = this.getIntent().getExtras().getInt(KEY_ACTION);
        raw = this.getIntent().getExtras().getString(KEY_SEARCH);
        category = this.getIntent().getExtras().getInt(KEY_CATEGORY);
        Log.e("DatabaseActivity: ", "The category is " + category);
        // ArrayAdapter<ValveModel> adapter = new ArrayAdapter<ValveModel>(this, android.R.layout.simple_expandable_list_item_1, valves);
        DatabaseAdapter adapter = new DatabaseAdapter(this, getData(), database);
        setListAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView right = (TextView) view.findViewById(R.id.rowDatabaseLocation);
                DatabaseAdapter adapter = (DatabaseAdapter) getListAdapter();
                adapter.inflateItem(position, view);
            }
        });
    }

    private List<ValveModel> getData(){
        List<ValveModel> valves;
        switch(action){
            case VAL_ALL:
                valves = database.getValvesByCategory(category);
                break;
            case VAL_AREA:
                valves = database.getValvesByAreaAndCategory(raw, category);
                break;
            case VAL_SEARCH:
                Log.e("DatabaseActivity","String raw before search is:\n\t" + raw);
                valves = database.getValvesByStringAndCategory(raw, category);
                Log.e("DatabaseActivity","String raw after search is:\n\t" + raw);
                break;
            case VAL_ADDRESS:
                /* TODO: Currently acts the same as case VAL_SEARCH
                   Should use nonexistant table Address(csv data is prepared, res/raw/heimilisfong.csv)
                   to match a hot valve to the given string. Also, the button should be removed when
                   cold and drainage action is selected in the previous activity.
                */
                Log.e("DatabaseActivity","String raw before search is:\n\t" + raw);
                valves = database.getValvesByStringAndCategory(raw, category);
                Log.e("DatabaseActivity","String raw after search is:\n\t" + raw);
                break;
            default:
                valves = new ArrayList<ValveModel>();
                break;
        }
        return valves;
    }

    public void onClick(View view){
        DatabaseAdapter adapter = (DatabaseAdapter) getListAdapter();
        ValveModel valveModel = null;
        if(view.getId() == R.id.add){ //line added
       /* switch (view.getId()){
            case R.id.add:
                /*
                String[] valves = new String[] {"HF", "HB", "HS"};  //Need to fix this
                int nextInt = new Random().nextInt(3); //Need to fix this
                valveModel = database.createValve("", "", "", "", "", "" ); //Need to fix this
                adapter.add(valveModel);
                */

                DialogFragment addDialog = new AddInfoDialog();
                FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
                Fragment prev = activity.getFragmentManager().findFragmentByTag("addInfo");
                if(prev != null){
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                addDialog.show(ft,"addInfo");
                //break;

        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume(){
        database.open();
        DatabaseAdapter adapter = (DatabaseAdapter)getListAdapter();
        adapter.clear();
        adapter.addAll(getData());
        super.onResume();
    }

    @Override
    protected void onPause(){
        database.close();
        super.onPause();
    }

}