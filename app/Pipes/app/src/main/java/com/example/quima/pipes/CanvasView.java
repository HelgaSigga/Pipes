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

    float oldPosX = 0,oldPosY = 0;
    float posX = 0,posY = 0;
    float distOld = 1, distCurrent = 1;

    boolean mapSelected = false;
    boolean touchOneStatus = false;
    boolean touchTwoStatus = false;

    private Bitmap bmp,bmp2;
    private Image image;
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
    }

    private void selectMap(){
        String type = Map.getMap();
        if(type != null) {
            if (type.equals("H")) {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pipes1_01);
                //bmp.prepareToDraw();
                //bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                //bmp2.prepareToDraw();
            } else if (type.equals("C")) {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.max1);
                //bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            } else if (type.equals("S")) {
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                //bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            }
            image = new Image(this, bmp/*,bmp2*/);
            mapSelected = true;
        }
    }

    private float getDistance(float x1, float y1, float x2, float y2){
        float distx, disty;
        distx = x1 - x2;
        disty = y1 - y2;
        return FloatMath.sqrt(distx * distx + disty * disty);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!mapSelected){selectMap();}
        canvas.drawColor(Color.GRAY);
        image.Draw(canvas);
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
                    mPath.moveTo(oldPosX-image.getPosX(), oldPosY-image.getPosY());
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
                        image.zoomInn();
                    }else {
                        image.zoomOut();
                    }
                }else {
                    posX = event.getX();
                    posY = event.getY();
                    if(draw){
                        mPath.lineTo(posX-image.getPosX(),posY-image.getPosY());
                    }else {
                        image.moveImage(oldPosX - posX,oldPosY - posY);
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
