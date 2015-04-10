package com.example.quima.pipes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by Benedikt Sævarss on 28.1.2015.
 */
public class Data extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String data;
    private EditText addressLine;
    private String currentArea;
    private Spinner spinner;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        spinner = (Spinner) findViewById(R.id.spinner2);
        Database database = new Database(this);
        database.open();
        ArrayList<String> list = database.getAreas();
        currentArea = list.isEmpty() ? "" : list.get(0);
        TextViewSpinnerAdapter<String> adapter = new TextViewSpinnerAdapter<String>(this, list);
        database.close();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button search = (Button)findViewById(R.id.searchButton);
        search.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            data = extras.getString("data");
            TextView display = (TextView)findViewById(R.id.dataAct);
            TextView address = (TextView)findViewById(R.id.address);
            if(data.equals("S")){
                display.setText(R.string.findValveText);
                spinner.setVisibility(View.VISIBLE);
                address.setVisibility(View.GONE);
            }
            if(data.equals("A")){
                display.setText(R.string.findAddressText);
                spinner.setVisibility(View.GONE);
                address.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent databaseActivity;
        switch(v.getId()){
            case R.id.searchButton:
                databaseActivity = new Intent(this, DatabaseActivity.class);
                if(data.equals("A")){
                    addressLine = (EditText)findViewById(R.id.address);
                    String s = addressLine.getText().toString();
                    databaseActivity.putExtra(DatabaseActivity.KEY_ACTION, DatabaseActivity.VAL_SEARCH);
                    databaseActivity.putExtra(DatabaseActivity.KEY_SEARCH, s);
                }
                if(data.equals("S")){
                    databaseActivity.putExtra(DatabaseActivity.KEY_ACTION, DatabaseActivity.VAL_AREA);
                    databaseActivity.putExtra(DatabaseActivity.KEY_SEARCH, currentArea);
                }
                startActivity(databaseActivity);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextViewSpinnerAdapter<String> adapter = (TextViewSpinnerAdapter<String>)spinner.getAdapter();
        if(i < adapter.getCount()) {
            currentArea = (String)adapter.getItem(i);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
