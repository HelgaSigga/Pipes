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

    private Bitmap[][] bmp;
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
                initializeMapParts(type);
            } else if (type.equals("C")) {
                initializeMapParts(type);
            } else if (type.equals("S")) {
                initializeMapParts(type);
            }
            image = new Image(this, bmp, type);
            mapSelected = true;
        }
    }

    public void initializeMapParts(String type){
        String x1 = type;
        bmp = new Bitmap[13][10];
        for (int i = 0 ; i < 2 ; i++ ){     //row
            for (int j = 0 ; j < 2 ; j++ ){ //col
                loadImage(i,j);
            }
        }
    }

    public void loadImage(int row,int col){
        switch(row){
            case 0:
                switch(col){
                    case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_01);break;
                    case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_02);break;
                    case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_03);break;
                    case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_04);break;
                    default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                }break;
            case 1:
                switch(col){
                    case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_13);break;
                    case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_14);break;
                    case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_15);break;
                    case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_16);break;
                    default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                }break;
            case 2:
                switch(col){
                    case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_25);break;
                    case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_26);break;
                    case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_27);break;
                    case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_28);break;
                    default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                }break;
            case 3:
                switch(col){
                    case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_37);break;
                    case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_38);break;
                    case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_39);break;
                    case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_40);break;
                    default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                }break;
            default:
                bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
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
