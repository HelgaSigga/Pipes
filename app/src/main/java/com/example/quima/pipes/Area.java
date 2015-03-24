package com.example.quima.pipes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Area extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.area1:
                //do stuff
                break;
            case R.id.area2:
                //do stuff
                break;
            case R.id.area3:
                // do stuff
                break;
            case R.id.area4:
                //do stuff
                break;
            case R.id.area5:
                //do stuff
                break;
            case R.id.area6:
                //do stuff
                break;
            case R.id.area7:
                //do stuff
                break;
            case R.id.area8:
                //do stuff
                break;
            case R.id.area9:
                //do stuff
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_area, menu);
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
