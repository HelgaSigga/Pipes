package com.example.quima.pipes;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class InfoChoice extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener{

    EditText addressLine;
    private String currentArea;
    private int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_choice);
        category = this.getIntent().getExtras().getInt(DatabaseActivity.KEY_CATEGORY);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Database database = new Database(this);
        database.open();
        ArrayList<String> list = database.getAreas();
        currentArea = list.isEmpty() ? "" : list.get(0);
        TextViewSpinnerAdapter<String> adapter = new TextViewSpinnerAdapter<String>(this, list);
        database.close();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Button b = (Button) findViewById(R.id.database);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.search);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.searchByAreaButton);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.addressBtn);
        b.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent databaseActivity = new Intent(this, DatabaseActivity.class);
        databaseActivity.putExtra(DatabaseActivity.KEY_CATEGORY, category);
        switch(v.getId()){
            case R.id.database:
                databaseActivity.putExtra(DatabaseActivity.KEY_ACTION, DatabaseActivity.VAL_ALL);
                databaseActivity.putExtra(DatabaseActivity.KEY_SEARCH, "");
                break;
            case R.id.search:
                addressLine = (EditText)findViewById(R.id.address);
                String s = addressLine.getText().toString();
                databaseActivity.putExtra(DatabaseActivity.KEY_ACTION, DatabaseActivity.VAL_SEARCH);
                databaseActivity.putExtra(DatabaseActivity.KEY_SEARCH, s);
                break;
            case R.id.searchByAreaButton:
                databaseActivity.putExtra(DatabaseActivity.KEY_ACTION, DatabaseActivity.VAL_AREA);
                databaseActivity.putExtra(DatabaseActivity.KEY_SEARCH, currentArea);
                break;
            case R.id.addressBtn:
                addressLine = (EditText) findViewById(R.id.address);
                String st = addressLine.getText().toString();
                databaseActivity.putExtra(DatabaseActivity.KEY_ACTION, DatabaseActivity.VAL_ADDRESS);
                databaseActivity.putExtra(DatabaseActivity.KEY_SEARCH, st);
                break;
           }
        startActivity(databaseActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_choice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        TextViewSpinnerAdapter<String> adapter = (TextViewSpinnerAdapter<String>)spinner.getAdapter();
        if(i < adapter.getCount()) {
            currentArea = (String)adapter.getItem(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
