package com.example.quima.pipes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Benedikt Sævarss on 27.2.2015.
 */
public class Image {
    private float imagePosX = 300,imagePosY = 300;
    private float scale = 1;
    private CanvasView canvasView;
    private Bitmap bmp,bmp2;
    private int currentFrame = 0;
    private int width;
    private int height;

    public Image(CanvasView canvasView, Bitmap bmp/*,Bitmap bmp2*/){
        this.canvasView = canvasView;
        this.bmp = bmp;
        //this.bmp2 = bmp2;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    public float getPosX(){
        return imagePosX;
    }

    public float getPosY(){
        return imagePosY;
    }

    public void zoomInn(){
        if (scale >= 0.6f || scale <= 3f){
            scale = scale + 0.1f;
        }
    }

    public void zoomOut(){
        if (scale >= 0.6f || scale <= 3f){
            scale = scale - 0.1f;
        }
    }

    public void moveImage(float cx, float cy){
        imagePosX = imagePosX - cx;
        imagePosY = imagePosY - cy;
    }

    private void drawAt(Canvas canvas, Bitmap img, float cx, float cy, float scale){
        float w = img.getWidth();
        float h = img.getHeight();

        canvas.translate(cx, cy);
        canvas.scale(scale , scale);
        canvas.drawBitmap(img, -w/2, -h/2, null);
    }

    public void update(){
        //TODO
        Log.d("pipes", imagePosX+" "+imagePosY);
    }

    public void Draw(Canvas canvas){
        update();
        //TODO
        drawAt(canvas, bmp, imagePosX, imagePosY, scale);
        //if(imagePosX < -100){
          //  drawAt(canvas, bmp2, 1143, 0, scale);
        //}
    }
}
