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

        Button btn1 = (Button)findViewById(R.id.map);
        Button btn2 = (Button)findViewById(R.id.database);
        Button btn3 = (Button)findViewById(R.id.data);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.map:
                Intent Map = new Intent(this, Map.class);
                startActivity(Map);
                break;
            case R.id.data:
                Intent Data = new Intent(this, Data.class);
                startActivity(Data);
                break;
            case R.id.database:
                Intent Database = new Intent(this, DatabaseActivity.class);
                startActivity(Database);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View v){
        startActivity(new Intent(getApplicationContext(), DatabaseActivity.class));

    }


}
