package com.example.quima.pipes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Benedikt Sævarss on 28.1.2015.
 */
public class Map extends Activity{

    static String map;
    static boolean draw = false;
    static boolean clear = false;

    public Bitmap bitmap;

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
                bitmap = captureScreen();
                saveBmp();
                draw = false;
                return true;
            case R.id.clear:
                clear = true;
                draw = false;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Bitmap captureScreen()
    {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBmp(){
        String sdPath = Environment.getExternalStorageDirectory().toString();
        String Day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        String Year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String Hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
        String Minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));

        File mapDir = new File(sdPath + "/Map_images");
        mapDir.mkdirs();
        String imageName = Day+"-"+Year+"--"+Hour+"-"+Minute+".jpg";
        File file = new File(mapDir, imageName);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                Uri.parse("file://" + Environment.getExternalStorageDirectory())));
    }

}
