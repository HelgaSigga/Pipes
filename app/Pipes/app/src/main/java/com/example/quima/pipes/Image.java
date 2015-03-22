package com.example.quima.pipes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Benedikt Sævarss on 27.2.2015.
 */
public class Image {
    private float imagePosX = 0,imagePosY = 0;
    private float scale = 1;
    private float imagePartPosX[][];
    private float imagePartPosY[][];
    private int oldRow, oldCol;
    private int validRow, validCol;
    private CanvasView canvasView;
    private Bitmap bmp[][];
    private String type;
    private int width;
    private int height;
    private int gridSize = 13;

    public Image(CanvasView canvasView, Bitmap[][] bmp, String type){
        this.canvasView = canvasView;
        this.bmp = bmp;
        this.type = type;
        this.width = bmp[0][0].getWidth();
        this.height = bmp[0][0].getHeight();

        imagePartPosX = new float[gridSize][gridSize];
        imagePartPosY = new float[gridSize][gridSize];

        for (int i = 0 ; i < gridSize ; i++ ){
            for (int j = 0 ; j < gridSize ; j++ ){
                imagePartPosX[j][i] = this.width*i;
                imagePartPosY[j][i] = this.height*j;
            }
        }
    }

    public float getPosX(){
        return imagePosX;
    }

    public float getPosY(){
        return imagePosY;
    }

    public float getScale(){
        return scale;
    }

    public void zoomInn(){
        if (0.6f > scale){
            scale = 0.6f;
        }else if (scale > 2.0f){
            scale = 2.0f;
        }else {
            scale = scale + 0.01f;

        }
    }

    public void zoomOut(){
        if (0.6f > scale){
            scale = 0.6f;
        }else if (scale > 2.0f){
            scale = 2.0f;
        }else {
            scale = scale - 0.01f;

        }
    }

    public void moveImage(float cx, float cy){
        if(imagePosX <= 0){
            imagePosX = imagePosX - cx;
        }else {
            imagePosX = 0;
        }
        if(imagePosY <= 0){
            imagePosY = imagePosY - cy;
        }else {
            imagePosY = 0;
        }
    }

    public void update(){
        float row;
        float col;
        col = imagePosX/(-this.width*scale);
        row = imagePosY/(-this.height*scale);
        validRow = (int)row;
        validCol = (int)col;
        //Log.d("pipes", imagePosX+" "+imagePosY);
        if(validRow != oldRow) {
            if(bmp[validRow][validCol] == null){
                bmp[validRow+2][validCol] = null;
                canvasView.loadImage(validRow,validCol);
            }
            if(bmp[validRow][validCol+1] == null){
                bmp[validRow+2][validCol+1] = null;
                canvasView.loadImage(validRow,validCol+1);
            }
            if(bmp[validRow+1][validCol] == null){
                bmp[validRow-1][validCol] = null;
                canvasView.loadImage(validRow+1,validCol);
            }
            if(bmp[validRow+1][validCol+1] == null){
                bmp[validRow-1][validCol+1] = null;
                canvasView.loadImage(validRow+1,validCol+1);
            }
            oldRow = validRow;
        }
        if(validCol != oldCol) {
            if(bmp[validRow][validCol] == null){
                bmp[validRow][validCol+2] = null;
                canvasView.loadImage(validRow,validCol);
            }
            if(bmp[validRow+1][validCol] == null){
                bmp[validRow+1][validCol+2] = null;
                canvasView.loadImage(validRow+1,validCol);
            }
            if(bmp[validRow][validCol+1] == null){
                bmp[validRow][validCol-1] = null;
                canvasView.loadImage(validRow,validCol+1);
            }
            if(bmp[validRow+1][validCol+1] == null){
                bmp[validRow+1][validCol-1] = null;
                canvasView.loadImage(validRow+1,validCol+1);
            }
            oldCol = validCol;
        }
    }

    public void Draw(Canvas canvas){
        update();
        canvas.translate(imagePosX, imagePosY);
        canvas.scale(scale , scale);

        if(this.width < 1000 && this.height < 1000) {
            canvas.drawBitmap(bmp[validRow][validCol], imagePartPosX[validRow][validCol], imagePartPosY[validRow][validCol], null);
            canvas.drawBitmap(bmp[validRow][validCol+1], imagePartPosX[validRow][validCol+1], imagePartPosY[validRow][validCol+1], null);
            canvas.drawBitmap(bmp[validRow+1][validCol], imagePartPosX[validRow+1][validCol], imagePartPosY[validRow+1][validCol], null);
            canvas.drawBitmap(bmp[validRow+1][validCol+1], imagePartPosX[validRow+1][validCol+1], imagePartPosY[validRow+1][validCol+1], null);
        }
    }
}
