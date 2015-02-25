package com.example.quima.pipes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by Benedikt Sævarss on 28.1.2015.
 */
public class Map extends Activity{

    static String map;
    static boolean draw = false;
    static boolean clear = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            map = extras.getString("map");
        }
    }

    public static String getMap(){
        return map;
    }
    public static boolean getDraw(){
        return draw;
    }
    public static boolean getClear(){
        return clear;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.paint:
                draw = true;
                clear = false;
                return true;
            case R.id.mapMotion:
                draw = false;
                return true;
            case R.id.save:

                return true;
            case R.id.clear:
                clear = true;
                draw = false;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
