package com.example.quima.pipes;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Quima on 24/02/2015.
 */
public class DatabaseAdapter extends ArrayAdapter<ValveModel>{
    private final Context context;
    private final List<ValveModel> valves;
    private final Database database;

    public DatabaseAdapter(Context context, List<ValveModel> valves, Database database) {
        super(context, R.layout.row_databaseactivity, valves);
        this.context = context;
        this.valves = valves;
        this.database = database;
        ChangeInfoDialog.setDatabaseAdapter(this);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_databaseactivity, parent, false);
        TextView rowId = (TextView) rowView.findViewById(R.id.rowDatabaseID);
        TextView rowLoc = (TextView) rowView.findViewById(R.id.rowDatabaseLocation);
        ValveModel valve = valves.get(position);
        rowId.setText(valve.getArea() + ":" + valve.getNumber() + "\n" + valve.getType());
        rowLoc.setText(valve.getLocation());


        Button edit = (Button) rowView.findViewById(R.id.rowDatabaseEditButton);
        Button delete = (Button) rowView.findViewById(R.id.rowDatabaseDeleteButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment changeDialog = ChangeInfoDialog.newInstance(position);
                Activity activity = (Activity) context;
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Fragment prev = fragmentManager.findFragmentByTag("edit");
                if(prev != null){
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                changeDialog.show(ft,"edit");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteValveTable(valves.get(position));
                remove(valves.get(position));
            }
        });

        return rowView;
    }

    public void editItem(int position){
        ValveModel valve = getItem(position);
        database.editValve(valve);

    }

    public void inflateItem(int position, View view){
        ValveModel valve = valves.get(position);
        TextView right = (TextView) view.findViewById(R.id.rowDatabaseLocation);
        String text = valve.getLocation();
        String add = valve.getSource();
        if(!add.equals("")){
            text = text + "\n" + add;
        }
        add = valve.getComment();
        if(!add.equals("")){
            text = text + "\n" + add;
        }
        right.setText(text);
        //LinearLayout ll = (LinearLayout) view;
        //ll.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        Button edit = (Button) view.findViewById(R.id.rowDatabaseEditButton);
        edit.setVisibility(View.VISIBLE);
        Button delete = (Button) view.findViewById(R.id.rowDatabaseDeleteButton);
        delete.setVisibility(View.VISIBLE);
    }
}
