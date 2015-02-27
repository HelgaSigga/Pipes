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
<<<<<<< HEAD
=======
<<<<<<< HEAD
        btn1.setOnClickListener(this);
=======
>>>>>>> ecfccee58bd6f52fe24f6816de7e12617fbcc252
>>>>>>> Fixing database
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
<<<<<<< HEAD
=======
<<<<<<< HEAD
            case R.id.map:
                Intent Map = new Intent(this, Map.class);
                startActivity(Map);
                break;

=======
>>>>>>> ecfccee58bd6f52fe24f6816de7e12617fbcc252
>>>>>>> Fixing database
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
                startActivity(Sewage);
                Sewage.putExtra("map", "S");
                return true;
            case R.id.findValve:
                Intent Valve = new Intent(this, Data.class);
                Valve.putExtra("data", "V");
                startActivity(Valve);
                return true;
            case R.id.findAddress:
                Intent Address = new Intent(this, Data.class);
                Address.putExtra("data", "A");
                startActivity(Address);
                return true;
            case R.id.showData:
                Intent showData = new Intent(this, Data.class);
                startActivity(showData);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View v){
        startActivity(new Intent(getApplicationContext(), DatabaseActivity.class));

    }


}
