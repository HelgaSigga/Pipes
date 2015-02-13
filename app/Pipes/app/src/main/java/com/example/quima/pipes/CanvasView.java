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
    float imagePosX = 300,imagePosY = 150;
    float oldPosX = 0,oldPosY = 0;
    float posX = 0,posY = 0;
    float scaleX = 1, scaleY = 1;
    Paint paint = new Paint();
    private Bitmap bmp;
    Boolean touchOneStatus = false;
    Boolean touchTwoStatus = false;
    float distOld = 1;
    float distCurrent = 1;

    public CanvasView(Context context) {
        super(context);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.max1);
    }

    private void scaleImage(boolean zoom){
        if ((scaleX <= 0.6f && scaleY <= 0.6f)||(scaleX >= 3f && scaleY >= 3f)){
            return;
        }
        if (zoom){
            scaleX = scaleX + 0.2f;
            scaleY = scaleY + 0.2f;
        }
        else{
            scaleX = scaleX - 0.2f;
            scaleY = scaleY - 0.2f;
        }
    }

    private void moveImage(float cx, float cy){
        Log.d("pipes",imagePosX + " " + imagePosY);
        imagePosX = imagePosX - cx;
        imagePosY = imagePosY - cy;
    }

    private void drawAt(Canvas canvas, float cx, float cy){
        float w = bmp.getWidth();
        float h = bmp.getHeight();

        canvas.translate(cx, cy);
        canvas.scale(scaleX , scaleY);
        canvas.drawBitmap(bmp, -w/2, -h/2, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        paint.setColor(Color.WHITE);
        drawAt(canvas,imagePosX,imagePosY);
        canvas.drawLine(0, 0, 200, 200, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float distx, disty;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("pipes", "ACTION_DOWN");
                touchOneStatus = true;
                oldPosX = event.getX();
                oldPosY = event.getY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d("pipes","ACTION_POINTER_DOWN");
                touchTwoStatus = true;
                //Get the distance
                distx = event.getX(0) - event.getX(1);
                disty = event.getY(0) - event.getY(1);
                distOld = FloatMath.sqrt(distx * distx + disty * disty);
                break;
            case MotionEvent.ACTION_MOVE:
                if(touchOneStatus && touchTwoStatus){
                    //Get the current distance
                    distx = event.getX(0) - event.getX(1);
                    disty = event.getY(0) - event.getY(1);
                    distCurrent = FloatMath.sqrt(distx * distx + disty * disty);
                    if (distOld <= distCurrent){
                        scaleImage(true);
                    }
                    else {
                        scaleImage(false);
                    }
                }else {
                    posX = event.getX();
                    posY = event.getY();
                    distx = oldPosX - posX;
                    disty = oldPosY - posY;
                    moveImage(distx,disty);
                    oldPosX = posX;
                    oldPosY = posY;
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
