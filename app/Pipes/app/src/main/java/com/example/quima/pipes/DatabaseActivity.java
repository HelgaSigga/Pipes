package com.example.quima.pipes;

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
    private Context context;


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

        List<ValveModel> valves = database.getAllValves();

       // ArrayAdapter<ValveModel> adapter = new ArrayAdapter<ValveModel>(this, android.R.layout.simple_expandable_list_item_1, valves);
        DatabaseAdapter adapter = new DatabaseAdapter(this, valves, database);
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
                Activity activity = (Activity) context;
                FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
                Fragment prev = activity.getFragmentManager().findFragmentByTag("awwa");
                if(prev != null){
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                addDialog.show(ft,"awwa");
                //break;

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