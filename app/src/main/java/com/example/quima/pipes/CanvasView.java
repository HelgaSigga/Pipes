package com.example.quima.pipes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
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
        bmp = new Bitmap[11][20];
        for (int i = 0 ; i < 3 ; i++ ){     //row
            for (int j = 0 ; j < 3 ; j++ ){ //col
                loadImage(i+3,j+8,mapType);
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
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_04);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_05);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_06);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_07);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_08);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_09);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_10);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 1:
                    switch(col){
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_24);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_25);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_26);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_27);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_28);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_29);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_30);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_31);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_32);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 2:
                    switch(col){
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_43);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_44);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_45);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_46);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_47);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_48);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_49);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_50);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_51);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_52);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_53);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_54);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_55);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 3:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_61);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_62);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_63);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_64);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_65);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_66);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_67);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_68);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_69);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_70);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_71);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_72);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_73);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_74);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_75);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_76);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 4:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_81);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_82);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_83);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_84);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_85);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_86);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_87);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_88);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_89);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_90);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_91);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_92);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_93);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_94);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_95);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_96);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 5:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_101);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_102);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_107);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_108);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_109);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_110);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_111);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_112);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_113);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_114);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_115);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_116);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_117);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_118);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 6:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_121);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_128);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_129);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_130);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_131);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_132);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_133);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_134);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_135);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_136);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_137);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_138);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_139);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 7:
                    switch(col){
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_150);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_151);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_152);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_153);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_154);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_155);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_156);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_157);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_158);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_159);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 8:
                    switch(col){
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_174);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_175);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_176);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_177);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_178);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.hitaveita_179);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                default:
                    bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
            }
        }
// Images for cold water

        if(type.equals("C")){
            switch(row){
                case 0:
                    switch(col){
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_04);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_05);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_06);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_07);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_08);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_09);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 1:
                    switch(col){
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_25);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_26);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_27);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_28);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_29);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_30);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_31);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 2:
                    switch(col){
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_46);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_47);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_48);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_49);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_50);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_51);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_52);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_53);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_54);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 3:
                    switch(col){
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_64);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_65);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_66);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_67);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_68);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_69);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_70);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_71);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_72);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_73);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_74);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_75);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_76);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 4:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_81);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_82);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_83);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_84);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_85);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_86);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_87);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_88);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_89);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_90);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_91);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_92);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_93);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_94);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_95);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_96);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_97);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 5:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_101);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_102);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_103);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_104);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_105);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_109);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_110);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_111);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_112);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_113);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_114);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_115);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_116);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_117);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 6:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_121);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_122);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_123);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_124);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_129);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_130);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_131);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_132);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_133);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_134);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_135);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_136);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_137);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_138);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_139);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 7:
                    switch(col){
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_151);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_152);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_153);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_154);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_155);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_156);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_157);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_158);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_159);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 8:
                    switch(col){
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_175);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_176);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_177);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_178);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.vatnsveita_179);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                default:
                    bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
            }
        }
// Images for sewage system
        if(type.equals("S")){
            switch(row){
                case 0:
                    switch(col){
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_04);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_05);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_06);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_07);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_08);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_09);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_10);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 1:
                    switch(col){
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_23);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_24);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_25);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_26);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_27);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_28);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_29);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_30);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_31);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_32);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 2:
                    switch(col){
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_40);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_41);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_42);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_43);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_44);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_45);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_46);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_47);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_48);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_49);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_50);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_51);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_52);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_53);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_54);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 3:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_58);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_59);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_60);break;
                        case 3 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_61);break;
                        case 4 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_62);break;
                        case 5 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_63);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_64);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_65);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_66);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_67);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_68);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_69);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_70);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_71);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_72);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_73);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 4:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_77);break;
                        case 1 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_78);break;
                        case 2 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_79);break;
                        case 6 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_83);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_84);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_85);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_86);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_87);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_88);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_89);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_90);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_91);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_92);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_93);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 5:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_96);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_103);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_104);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_105);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_106);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_107);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_108);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_109);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_110);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_111);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_112);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_113);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_114);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 6:
                    switch(col){
                        case 0 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_115);break;
                        case 7 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_122);break;
                        case 8 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_123);break;
                        case 9 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_124);break;
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_125);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_126);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_127);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_128);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_129);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_130);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_131);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_132);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_133);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 7:
                    switch(col){
                        case 10 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_144);break;
                        case 11 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_145);break;
                        case 12 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_146);break;
                        case 13 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_147);break;
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_148);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_149);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_150);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_151);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_152);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                case 8:
                    switch(col){
                        case 14 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_167);break;
                        case 15 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_168);break;
                        case 16 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_169);break;
                        case 17 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_170);break;
                        case 18 :bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.fraveita_171);break;
                        default:bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
                    }break;
                default:
                    bmp[row][col] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_map);
            }
        }
    }
}
