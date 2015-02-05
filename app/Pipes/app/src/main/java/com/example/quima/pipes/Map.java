package com.example.quima.pipes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Benedikt Sævarss on 28.1.2015.
 */
public class Map extends Activity implements View.OnClickListener {
    ImageView myImageView;
    Bitmap bitmap;
    int bmpWidth, bmpHeight;

    Boolean touchOneStatus = false;
    Boolean touchTwoStatus = false;
    float dist0 = 1;
    float distCurrent = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        myImageView = (ImageView)findViewById(R.id.imageview);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pipes);
        bmpWidth = bitmap.getWidth();
        bmpHeight = bitmap.getHeight();
        drawImage();

        myImageView.setOnTouchListener(OnTouchListener);

        Button btn1 = (Button)findViewById(R.id.plusBut);
        Button btn2 = (Button)findViewById(R.id.minusBut);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.plusBut:
                Log.d("pipes","Plus " + dist0);
                dist0 = dist0 + 0.2f;
                drawImage();
                break;
            case R.id.minusBut:
                Log.d("pipes","minus "+dist0);
                dist0 = dist0 - 0.2f;
                drawImage();
                break;
        }
    }

    private void drawImage(){
        float curScale = distCurrent/dist0;
        if (curScale < 0.1){
            curScale = 0.1f;
        }

        Bitmap resizedBitmap;
        int newHeight = (int) (bmpHeight * curScale);
        int newWidth = (int) (bmpWidth * curScale);
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
        myImageView.setImageBitmap(resizedBitmap);
    }

    OnTouchListener OnTouchListener = new OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            float distx, disty;

            switch(event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    Log.d("pipes","ACTION_DOWN");
                    touchOneStatus = true;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.d("pipes","ACTION_POINTER_DOWN");
                    touchTwoStatus = true;
                //Get the distance
                    distx = event.getX(0) - event.getX(1);
                    disty = event.getY(0) - event.getY(1);
                    dist0 = FloatMath.sqrt(distx * distx + disty * disty);
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("pipes","ACTION_MOVE");

                    if(touchOneStatus && touchTwoStatus){
                        //Get the current distance
                        distx = event.getX(0) - event.getX(1);
                        disty = event.getY(0) - event.getY(1);
                        distCurrent = FloatMath.sqrt(distx * distx + disty * disty);
                        drawImage();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d("pipes","ACTION_UP");
                    touchOneStatus = false;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    Log.d("pipes","ACTION_POINTER_UP");
                    touchTwoStatus = false;
                    break;
            }
            return true;
        }
    };
}
