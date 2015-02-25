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
    float scale = 1;
    float distOld = 1, distCurrent = 1;

    boolean touchOneStatus = false;
    boolean touchTwoStatus = false;

    private Bitmap bmp,bmp1,bmp2;
    private Paint mPaint;
    private Path mPath;

    public CanvasView(Context context) {
        super(context);
    }
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

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
        if (scale >= 0.6f || scale <= 3f){
            if (zoom){
                scale = scale + 0.1f;
            }
            else{
                scale = scale - 0.1f;
            }
        }
    }

    private void moveImage(float cx, float cy){
        imagePosX = imagePosX - cx;
        imagePosY = imagePosY - cy;
    }

    private float getDistance(float x1, float y1, float x2, float y2){
        float distx, disty;
        distx = x1 - x2;
        disty = y1 - y2;
        return FloatMath.sqrt(distx * distx + disty * disty);
    }

    private void drawAt(Canvas canvas, Bitmap img, float cx, float cy, float scale){
        float w = img.getWidth();
        float h = img.getHeight();

        canvas.translate(cx, cy);
        canvas.scale(scale , scale);
        canvas.drawBitmap(img, -w/2, -h/2, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String x = Map.getMap();
        canvas.drawColor(Color.GRAY);
        if(x != null) {
            if (x.equals("H")) drawAt(canvas, bmp, imagePosX, imagePosY, scale);
            else if (x.equals("C")) drawAt(canvas, bmp2, imagePosX, imagePosY, scale);
            else if (x.equals("S")) drawAt(canvas, bmp2, imagePosX, imagePosY, scale);
        }
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean draw = Map.getDraw();
        boolean clear = Map.getClear();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
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
                distOld = getDistance(event.getX(0),event.getX(1),event.getY(0),event.getY(1));
                break;

            case MotionEvent.ACTION_MOVE:
                if(touchOneStatus && touchTwoStatus){
                    distCurrent = getDistance(event.getX(0),event.getX(1),event.getY(0),event.getY(1));
                    if (distOld <= distCurrent){
                        scaleImage(true);
                    }
                    else {
                        scaleImage(false);
                    }
                }else {
                    posX = event.getX();
                    posY = event.getY();
                    if(draw){
                        mPath.lineTo(posX-imagePosX,posY-imagePosY);
                    }else {
                        moveImage(oldPosX - posX,oldPosY - posY);
                    }
                    oldPosX = posX;
                    oldPosY = posY;
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
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
