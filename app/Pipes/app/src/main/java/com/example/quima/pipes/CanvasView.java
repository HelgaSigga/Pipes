package com.example.quima.pipes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.util.FloatMath;
/**
 * Created by Benedikt Sævarss on 7.2.2015.
 */
public class CanvasView extends View {

    float imagePosX = 200,imagePosY = 300;
    float oldPosX = 0,oldPosY = 0;
    float posX = 0,posY = 0;
    float scaleX = 1, scaleY = 1;
    float distOld = 1, distCurrent = 1;

    boolean drawEnable = false;
    boolean touchOneStatus = false;
    boolean touchTwoStatus = false;

    private Bitmap bmp,bmp1,bmp2;
    private Paint mPaint;
    private Path mPath;

    public CanvasView(Context context) {
        super(context);
        init(context);
    }
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.max1);
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }

    private void scaleImage(boolean zoom){
        if ((scaleX <= 0.6f && scaleY <= 0.6f)||(scaleX >= 3f && scaleY >= 3f)){
            return;
        }
        if (zoom){
            scaleX = scaleX + 0.1f;
            scaleY = scaleY + 0.1f;
        }
        else{
            scaleX = scaleX - 0.1f;
            scaleY = scaleY - 0.1f;
        }
    }

    private void moveImage(float cx, float cy){
        Log.d("pipes",imagePosX + " " + imagePosY);
        imagePosX = imagePosX - cx;
        imagePosY = imagePosY - cy;
    }

    private void drawAt(Canvas canvas, Bitmap img, float cx, float cy){
        float w = img.getWidth();
        float h = img.getHeight();

        canvas.translate(cx, cy);
        canvas.scale(scaleX , scaleY);
        canvas.drawBitmap(img, -w/2, -h/2, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String x = Map.getMap();
        canvas.drawColor(Color.GRAY);
        if(x != null) {
            if (x.equals("H")) drawAt(canvas, bmp, imagePosX, imagePosY);
            else if (x.equals("C")) drawAt(canvas, bmp2, imagePosX, imagePosY);
            else if (x.equals("S")) drawAt(canvas, bmp2, imagePosX, imagePosY);
        }
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float distx, disty;
        boolean draw = Map.getDraw();
        boolean clear = Map.getClear();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("pipes", "ACTION_DOWN");
                touchOneStatus = true;
                oldPosX = event.getX();
                oldPosY = event.getY();
                if(clear){
                    mPath.reset();
                }
                if(draw) {
                    mPath.moveTo(oldPosX-imagePosX, oldPosY-imagePosY);
                }
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
                    if(draw){
                        mPath.lineTo(posX-imagePosX,posY-imagePosY);
                    }else {
                        moveImage(distx,disty);
                    }
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
}
