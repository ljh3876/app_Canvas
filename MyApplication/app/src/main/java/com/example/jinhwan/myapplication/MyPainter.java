package com.example.jinhwan.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jinhwan on 2017-05-18.
 */

public class MyPainter extends View {
    String operationType="";
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint = new Paint();
    Matrix matrix = new Matrix();
    boolean checkbox, rotate, move, scale, skew;

    public MyPainter(Context context) {
        super(context);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.BLACK);
    }

    public MyPainter(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);
        super.onSizeChanged(w, h, oldw, oldh);

    }
    public void setOperationType(String operationType){
        this.operationType = operationType;
    }
    public void setOperationType(String operationType, boolean flag){
        this.operationType = operationType;
    }
    private void drawStamp(int x, int y){

        Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        if(rotate){
            matrix.postRotate(30);
            img = Bitmap.createBitmap(img,0,0,img.getWidth(),img.getHeight(),matrix,true);
            matrix.postRotate(-30);
        }
        else if(move){
            x +=50;
            y +=50;
        }
        else if(scale){
            matrix.postScale(1.5f, 1.5f);
            img = Bitmap.createBitmap(img, 0,0,img.getWidth(), img.getHeight(),matrix,true);
            matrix.postScale(1f,1f);
        }
        else if(skew){
            matrix.postSkew(0.2f, 0);
            img = Bitmap.createBitmap(img, 0,0,img.getWidth(), img.getHeight(),matrix,true);
            matrix.postScale(0,0);
        }

        mCanvas.drawBitmap(img, x, y, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
        super.onDraw(canvas);
    }

    int oldX = -1, oldY = -1;
    Bitmap img;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldX = X;
            oldY = Y;
        }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (oldX != -1) {
                mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                invalidate();
                oldX = X;
                oldY = Y;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (oldX != -1) {
                mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                invalidate();
            }
            if(checkbox){
                drawStamp(X,Y);
            }
                oldX = -1;
                oldY = -1;
            }


        return true;
    }
    public void erase(){
        mBitmap.eraseColor(Color.WHITE);
        invalidate();
    }
    public void setRotate(boolean rotate){
        this.rotate = rotate;
    }

    public void setMove(boolean move){
        this.move= move;
    }

    public void setScale(boolean scale){
        this.scale = scale;
    }
    public void setSkew(boolean skew){
        this.skew = skew;
    }
    public void setBlurring(boolean blur){
        if(blur){
            BlurMaskFilter blurring = new BlurMaskFilter(100, BlurMaskFilter.Blur.NORMAL);
            mPaint.setMaskFilter(blurring);
        }else{
            mPaint.setMaskFilter(null);
        }
    }
    public void setColoring(boolean color){
        if(color){
            float[] matrixarray = {
                    2f, 0f, 0f, 0f, -25f,
                    0f, 2f, 0f, 0f, -25f,
                    0f, 0f, 2f, 0f, -25f,
                    0f, 0f, 0f, 2f, -25f
            };
            ColorMatrix matrix = new ColorMatrix(matrixarray);
            mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
        }else {
            mPaint.setColorFilter(null);
        }
    }
    public void setPenWidthBig(int size){
        mPaint.setStrokeWidth(size);
    }
    public void setPenColorRed(){
        mPaint.setColor(Color.RED);
    }
    public void setPenColorBlue(){
        mPaint.setColor(Color.BLUE);
    }

}
