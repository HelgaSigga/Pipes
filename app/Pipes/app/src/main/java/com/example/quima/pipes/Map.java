package com.example.quima.pipes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by Benedikt Sævarss on 28.1.2015.
 */
public class Map extends Activity{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Button draw = (Button)findViewById(R.id.button_draw);
        draw.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Log.d("pipes", "draw");
            }
        });

        Button save = (Button)findViewById(R.id.button_save);
        save.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Log.d("pipes","save");
            }
        });
    }
}
