package com.example.quima.pipes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Benedikt Sævarss on 7.4.2015.
 */
public class Drawing {

    public Paint mPaint;
    public Path mPath;

    public Drawing(CanvasView canvasView){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
    }

    public void Draw(Canvas canvas){
        canvas.drawPath(mPath, mPaint);
    }
}
