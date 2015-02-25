package com.example.quima.pipes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Benedikt Sævarss on 28.1.2015.
 */
public class Data extends Activity {
    static String data;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            data = extras.getString("data");
            TextView display = (TextView)findViewById(R.id.dataAct);
            if(data.equals("V")){
                display.setText(R.string.findValveText);
            }
            else if(data.equals("A")){
                display.setText(R.string.findAddressText);
            }

        }
    }
}
