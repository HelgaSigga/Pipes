package com.example.quima.pipes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by Benedikt Sævarss on 27.2.2015.
 */
public class Image {
    private float imagePosX = -500,imagePosY = -500;
    private float scale = 1;
    private float imagePartPosX[][];
    private float imagePartPosY[][];
    private int oldRow, oldCol;
    private int validRow, validCol;
    private CanvasView canvasView;
    private Bitmap bmp[][];
    private String type;
    private int imgWidth,imgHeight;
    private int canWidth,canHeight;
    private int gridSize = 14;

    public Image(CanvasView canvasView, Bitmap[][] bmp, String type){
        this.canvasView = canvasView;
        this.bmp = bmp;
        this.type = type;
        this.imgWidth = bmp[0][0].getWidth();
        this.imgHeight = bmp[0][0].getHeight();
        this.canWidth = canvasView.getWidth();
        this.canHeight = canvasView.getHeight();

        imagePartPosX = new float[gridSize][gridSize];
        imagePartPosY = new float[gridSize][gridSize];

        for (int i = 0 ; i < gridSize ; i++ ){
            for (int j = 0 ; j < gridSize ; j++ ){
                imagePartPosX[j][i] = this.imgWidth*i-imgWidth/2;
                imagePartPosY[j][i] = this.imgHeight*j-imgHeight/2;
            }
        }
    }

    public float getPosX(){
        return imagePosX;
    }

    public float getPosY(){
        return imagePosY;
    }

    public float getCanCentX(){
        return canWidth/2;
    }

    public float getCanCentY(){
        return canHeight/2;
    }

    public float getScale(){
        return scale;
    }

    public void zoomInn(){
        scale = scale / 0.95f;
        if (0.5f > scale){
            scale = 0.5f;
        }else if (scale > 2.0f){
            scale = 2.0f;
        }else {
            imagePosX = imagePosX / 0.95f;
            imagePosY = imagePosY / 0.95f;
            moveImage(0,0);
        }
    }

    public void zoomOut(){
        scale = scale * 0.95f;
        if (0.5f > scale){
            scale = 0.5f;
        }else if (scale > 2.0f){
            scale = 2.0f;
        }else {
            imagePosX = imagePosX * 0.95f;
            imagePosY = imagePosY * 0.95f;
            moveImage(0,0);
        }
    }

    public void moveImage(float cx, float cy){
        imagePosX = imagePosX - cx;
        imagePosY = imagePosY - cy;

        if(imagePosX > (scale-1)*this.canWidth/2){
            imagePosX = (scale-1)*this.canWidth/2;
        }
        if(imagePosY > (scale-1)*this.canHeight/2){
            imagePosY = (scale-1)*this.canHeight/2;
        }
        if(imagePosX < (this.imgWidth*-scale*12)-(scale-1)*this.canWidth/2){
            imagePosX = (this.imgWidth*-scale*12)-(scale-1)*this.canWidth/2;
        }
        if(imagePosY < (this.imgHeight*-scale*6.15f)-(scale-1)*this.canHeight/2){
            imagePosY = (this.imgHeight*-scale*6.15f)-(scale-1)*this.canHeight/2;
        }
    }

    public void update(){
        float row;
        float col;
        col = (imagePosX+(canWidth/2)*scale)/(-this.imgWidth*scale);
        row = (imagePosY+(canHeight/2)*scale)/(-this.imgHeight*scale);
        validRow = (int)row;
        validCol = (int)col;
        Log.d("pipes", imagePosX+" "+this.imgWidth*scale+"-"+col+"-"+row+"-"+scale);
        // Moving upp or down
        if(validRow != oldRow) {
            if(validRow < oldRow){
                bmp[validRow+3][validCol] = null;
                bmp[validRow+3][validCol+1] = null;
                bmp[validRow+3][validCol+2] = null;
                canvasView.loadImage(validRow,validCol,this.type);
                canvasView.loadImage(validRow,validCol+1,this.type);
                canvasView.loadImage(validRow,validCol+2,this.type);
            }
            else if(validRow > oldRow){
                bmp[validRow-1][validCol] = null;
                bmp[validRow-1][validCol+1] = null;
                bmp[validRow-1][validCol+2] = null;
                canvasView.loadImage(validRow+2,validCol,this.type);
                canvasView.loadImage(validRow+2,validCol+1,this.type);
                canvasView.loadImage(validRow+2, validCol+2,this.type);
            }
            oldRow = validRow;
        }
        // Moving left or right
        if(validCol != oldCol) {
            if(validCol < oldCol){
                bmp[validRow][validCol+3] = null;
                bmp[validRow+1][validCol+3] = null;
                bmp[validRow+2][validCol+3] = null;
                canvasView.loadImage(validRow,validCol,this.type);
                canvasView.loadImage(validRow+1,validCol,this.type);
                canvasView.loadImage(validRow+2,validCol,this.type);
            }
            else if(validCol > oldCol){
                bmp[validRow][validCol-1] = null;
                bmp[validRow+1][validCol-1] = null;
                bmp[validRow+2][validCol-1] = null;
                canvasView.loadImage(validRow,validCol+2,this.type);
                canvasView.loadImage(validRow+1,validCol+2,this.type);
                canvasView.loadImage(validRow+2,validCol+2,this.type);
            }
            oldCol = validCol;
        }
    }

    public void Draw(Canvas canvas){
        update();
        canvas.translate(imagePosX + canWidth/2, imagePosY + canHeight/2);
        canvas.scale(scale , scale);

        canvas.drawBitmap(bmp[validRow][validCol], imagePartPosX[validRow][validCol], imagePartPosY[validRow][validCol], null);
        canvas.drawBitmap(bmp[validRow][validCol+1], imagePartPosX[validRow][validCol+1], imagePartPosY[validRow][validCol+1], null);
        canvas.drawBitmap(bmp[validRow][validCol+2], imagePartPosX[validRow][validCol+2], imagePartPosY[validRow][validCol+2], null);

        canvas.drawBitmap(bmp[validRow+1][validCol], imagePartPosX[validRow+1][validCol], imagePartPosY[validRow+1][validCol], null);
        canvas.drawBitmap(bmp[validRow+1][validCol+1], imagePartPosX[validRow+1][validCol+1], imagePartPosY[validRow+1][validCol+1], null);
        canvas.drawBitmap(bmp[validRow+1][validCol+2], imagePartPosX[validRow+1][validCol+2], imagePartPosY[validRow+1][validCol+2], null);

        canvas.drawBitmap(bmp[validRow+2][validCol], imagePartPosX[validRow+2][validCol], imagePartPosY[validRow+2][validCol], null);
        canvas.drawBitmap(bmp[validRow+2][validCol+1], imagePartPosX[validRow+2][validCol+1], imagePartPosY[validRow+2][validCol+1], null);
        canvas.drawBitmap(bmp[validRow+2][validCol+2], imagePartPosX[validRow+2][validCol+2], imagePartPosY[validRow+2][validCol+2], null);
    }
}
