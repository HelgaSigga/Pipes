package com.example.quima.pipes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
    private Drawing drawing;


    public CanvasView(Context context) {
        super(context);
    }
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

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
            drawing = new Drawing(this);
            mapSelected = true;
        }
    }

    public void initializeMapParts(String type){
        String mapType = type;
        bmp = new Bitmap[9][13];
        for (int i = 0 ; i < 3 ; i++ ){     //row
            for (int j = 0 ; j < 3 ; j++ ){ //col
                loadImage(i,j,mapType);
            }
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
        //canvas.drawColor(Color.BLUE);
        image.Draw(canvas);
        drawing.Draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        boolean draw = Map.getDraw();
        boolean clear = Map.getClear();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("pipes", image.getPosX()+" "+image.getPosY());
                touchOneStatus = true;
                oldPosX = event.getX();
                oldPosY = event.getY();
                if(clear){
                    drawing.mPath.reset();
                }
                if(draw) {
                    drawing.mPath.moveTo((oldPosX-image.getPosX())/image.getScale()-(image.getCanCentX()/image.getScale()),
                            (oldPosY-image.getPosY())/image.getScale()-(image.getCanCentY()/image.getScale()));
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                touchTwoStatus = true;
                distOld = getDistance(event.getX(0),event.getY(0),event.getX(1),event.getY(1));
                break;

            case MotionEvent.ACTION_POINTER_2_DOWN:
                touchTwoStatus = true;
                distOld = getDistance(event.getX(0),event.getY(0),event.getX(1),event.getY(1));
                break;

            case MotionEvent.ACTION_MOVE:
                if(touchOneStatus && touchTwoStatus){
                    distCurrent = getDistance(event.getX(0),event.getY(0),event.getX(1),event.getY(1));
                    if (distOld <= distCurrent){
                        image.zoomInn();
                    }else {
                        image.zoomOut();
                    }
                    distOld = distCurrent;
                }else if (touchOneStatus) {
                    posX = event.getX();
                    posY = event.getY();
                    if(draw){
                        drawing.mPath.lineTo((posX-image.getPosX())/image.getScale()-(image.getCanCentX()/image.getScale()),
                                (posY-image.getPosY())/image.getScale()-(image.getCanCentY())/image.getScale());
                    }else {
                        image.moveImage(oldPosX - posX,oldPosY - posY);
                    }
                    oldPosX = posX;
                    oldPosY = posY;
                }else{
                    break;
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                touchOneStatus = false;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                touchTwoStatus = false;
                touchOneStatus = false;
                break;

            case MotionEvent.ACTION_POINTER_2_UP:
                touchTwoStatus = false;
                touchOneStatus = false;
                break;
        }
        return true;
    }

    public void loadImage(int row,int col,String type){
// Images for hot water
        if(type.equals("H")){
            switch(row){
                case 0:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_01);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_02);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_03);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_04);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_05);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_06);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_white);
                    }break;
                case 1:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_13);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_14);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_15);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_16);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_17);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_18);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_19);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_20);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_21);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_white);
                    }break;
                case 2:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_25);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_26);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_27);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_28);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_29);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_30);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_31);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_32);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_33);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_34);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_white);
                    }break;
                case 3:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_37);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_38);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_39);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_40);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_41);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_42);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_43);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_44);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_45);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_46);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_white);
                    }break;
                case 4:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_49);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_50);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_51);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_52);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_53);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_54);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_55);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_56);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_57);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_58);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_59);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_60);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_white);
                    }break;
                case 5:
                    switch(col){
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_62);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_63);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_64);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_65);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_66);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_67);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_68);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_69);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_70);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_71);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_72);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_white);
                    }break;
                case 6:
                    switch(col){
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_76);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_77);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_78);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_79);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_80);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_81);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_82);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_83);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_84);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_white);
                    }break;
                case 7:
                    switch(col){
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_91);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_92);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_93);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_94);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_95);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_96);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_white);
                    }break;
                default:
                    bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_white);
            }
        }
// Images for cold water
        if(type.equals("C")){
            switch(row){
                case 0:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_01);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_03);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_05);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                case 1:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_13);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_15);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_17);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                case 2:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_25);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_26);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_27);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_28);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_29);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                case 3:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_37);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_38);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_39);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_40);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_41);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                case 4:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_49);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_50);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_51);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_52);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_53);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                default:
                    bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            }
        }
// Images for sewage system
        if(type.equals("S")){
            switch(row){
                case 0:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_01);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_02);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_03);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_05);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                case 1:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_13);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_14);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_15);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_16);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                case 2:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_25);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_26);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_27);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_28);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                case 3:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_38);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_39);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_40);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_41);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                case 4:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_50);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_51);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_52);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.pipes_53);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                    }break;
                default:
                    bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            }
        }
    }
}
