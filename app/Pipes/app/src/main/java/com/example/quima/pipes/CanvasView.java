package com.example.quima.pipes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.util.FloatMath;
/**
 * Created by Benedikt Sævarss on 7.2.2015.
 */
public class CanvasView extends View {
    float x = 0,y = 0;
    float scaleX = 1, scaleY = 1;
    Paint paint = new Paint();
    private Bitmap bmp;
    Boolean touchOneStatus = false;
    Boolean touchTwoStatus = false;
    float dist0 = 1;
    float distCurrent = 1;

    public CanvasView(Context context) {
        super(context);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pipes);
    }

    private void scaleImage(){
        scaleX = scaleX + 0.1f;
        scaleY = scaleY + 0.1f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        paint.setColor(Color.WHITE);
        canvas.scale(scaleX , scaleY);
        //canvas.translate(0, 0);
        canvas.drawBitmap(bmp, x, y, null);
        canvas.drawLine(0, 0, 200, 200, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float distx, disty;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("pipes", "ACTION_DOWN");
                touchOneStatus = true;
                scaleImage();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d("pipes","ACTION_POINTER_DOWN");
                touchTwoStatus = true;
                //Get the distance
                distx = event.getX(0) - event.getX(1);
                disty = event.getY(0) - event.getY(1);
                dist0 = FloatMath.sqrt(distx * distx + disty * disty);
                scaleImage();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                Log.d("pipes","ACTION_MOVE");
                if(touchOneStatus && touchTwoStatus){
                    //Get the current distance
                    distx = event.getX(0) - event.getX(1);
                    disty = event.getY(0) - event.getY(1);
                    distCurrent = FloatMath.sqrt(distx * distx + disty * disty);
                    scaleImage();
                }
                invalidate();
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

      /*
      	canvas.save();
      		canvas.translate(50, 50);
      		canvas.scale(0.5f, 0.5f);
      		canvas.drawRect(0.0, 0.0, 5.0, 5.0, paint);
  		canvas.restore();*/

}
