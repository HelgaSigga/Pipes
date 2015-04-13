package com.example.quima.pipes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class DatabaseChoice extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_choice);
        Button button = (Button) findViewById(R.id.hot);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.cold);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.drain);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent infoChoice = new Intent(this, InfoChoice.class);
        switch(v.getId()){
            case R.id.hot:
                infoChoice.putExtra(DatabaseActivity.KEY_CATEGORY, ValveTypeTable.HOT_ID);
                break;
            case R.id.cold:
                infoChoice.putExtra(DatabaseActivity.KEY_CATEGORY, ValveTypeTable.COLD_ID);
                break;
            case R.id.drain:
                infoChoice.putExtra(DatabaseActivity.KEY_CATEGORY, ValveTypeTable.DRAINAGE_ID);
                break;
        }
        startActivity(infoChoice);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database_choice, menu);
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
}
