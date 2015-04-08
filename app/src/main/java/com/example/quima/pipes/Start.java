package com.example.quima.pipes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.view.View.OnClickListener;


public class Start extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn2 = (Button)findViewById(R.id.database);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.database:
                //Intent Database = new Intent(this, DatabaseActivity.class);
                Intent infoChoicActivity = new Intent(this, InfoChoice.class);
                startActivity(infoChoicActivity);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.hotWater:
                Intent Hot = new Intent(this, Map.class);
                Hot.putExtra("map" , "H");
                startActivity(Hot);
            return true;
            case R.id.coldWater:
                Intent Cold = new Intent(this, Map.class);
                Cold.putExtra("map", "C");
                startActivity(Cold);
                return true;
            case R.id.sewage:
                Intent Sewage = new Intent(this, Map.class);
                Sewage.putExtra("map", "S");
                startActivity(Sewage);
                return true;
            case R.id.findValve:
                Intent Valve = new Intent(this, Data.class);
                Valve.putExtra("data", "S");
                startActivity(Valve);
                return true;
            case R.id.findAddress:
                Intent Address = new Intent(this, Data.class);
                Address.putExtra("data", "A");
                startActivity(Address);
                return true;
            case R.id.showData:
                Intent databaseActivity;
                databaseActivity = new Intent(this, DatabaseActivity.class);
                databaseActivity.putExtra(DatabaseActivity.KEY_ACTION, DatabaseActivity.VAL_ALL);
                databaseActivity.putExtra(DatabaseActivity.KEY_SEARCH, "");
                startActivity(databaseActivity);
                return true;
            case R.id.about_icon:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
