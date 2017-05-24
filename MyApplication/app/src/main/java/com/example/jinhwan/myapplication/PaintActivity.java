package com.example.jinhwan.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PaintActivity extends AppCompatActivity {
    MyPainter myPainter ;
    CheckBox stamp ;
    boolean flag_move=true, flag_rotate=true,flag_scale=true,flag_skew=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        String path = getExternalPath();
        File file = new File(path+"canvas");
        System.out.println(file.toString());
        System.out.println(file.getAbsolutePath());
        file.mkdir();
        myPainter = (MyPainter)findViewById(R.id.painter);

        stamp = (CheckBox)findViewById(R.id.checkBox);
        stamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                        myPainter.checkbox=true;
                else
                    myPainter.checkbox=false;
            }
        });
    }

    public void onClick(View v){
        if(v.getId() == R.id.eraser){
            myPainter.erase();
        }
        else if(v.getId()==R.id.open){
            String path = getExternalPath()+"canvas/sample.jpeg";
            myPainter.erase();
            try{
                FileInputStream input = new FileInputStream(path);
                Bitmap img = BitmapFactory.decodeStream(input);
                myPainter.mCanvas.drawBitmap(img,myPainter.getWidth(), myPainter.getHeight(),myPainter.mPaint);
                input.close();
                myPainter.invalidate();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(v.getId()==R.id.save){
            String path = getExternalPath()+"canvas/sample.jpeg";
            try{
                FileOutputStream output = new FileOutputStream(path);
                myPainter.mBitmap.compress(Bitmap.CompressFormat.JPEG,100,output);
                output.close();

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(v.getId()==R.id.move){

            if(flag_move == true){
                stamp.setChecked(true);
                myPainter.setMove(flag_move);
                flag_move=false;
            }else{
                stamp.setChecked(false);
                myPainter.setMove(flag_move);
                flag_move=true;
            }

        }
        else if(v.getId()==R.id.rotate){
            if(flag_rotate == true){
                stamp.setChecked(true);
                myPainter.setRotate(flag_rotate);
                flag_rotate=false;
            }else{
                stamp.setChecked(false);
                myPainter.setRotate(flag_rotate);
                flag_rotate=true;
            }
        }
        else if(v.getId()==R.id.scale){
            if(flag_scale == true){
                stamp.setChecked(true);
                myPainter.setScale(flag_scale);
                flag_scale=false;
            }else{
                stamp.setChecked(false);
                myPainter.setScale(flag_scale);
                flag_scale=true;
            }
        }
        else if(v.getId()==R.id.skew){
            if(flag_skew == true){
                stamp.setChecked(true);
                myPainter.setSkew(flag_skew);
                flag_skew=false;
            }else{
                stamp.setChecked(false);
                myPainter.setScale(flag_skew);
                flag_skew=true;
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"Bluring");
        menu.add(0,2,0,"Coloring");
        menu.add(0,3,0,"Pen Width Big");
        menu.add(1,4,0,"Pen Color Red");
        menu.add(1,5,0,"Pen Color Blue");
        menu.setGroupCheckable(0,true,false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==1) {
            if (item.isChecked()) {
                item.setChecked(false);
                myPainter.setBlurring(false);
            } else {
                item.setChecked(true);
                myPainter.setBlurring(true);
            }
        } else if (item.getItemId()==2) {
            if (item.isChecked()) {
                item.setChecked(false);
                myPainter.setColoring(false);
            } else {
                item.setChecked(true);
                myPainter.setColoring(true);
            }
        } else if (item.getItemId()==3) {
            if (item.isChecked()) {
                myPainter.setPenWidthBig(3);
                item.setChecked(false);
            } else {
                myPainter.setPenWidthBig(5);
                item.setChecked(true);
            }
        } else if (item.getItemId() == 4) {
            myPainter.setPenColorRed();
        } else if (item.getItemId() == 5) {
            myPainter.setPenColorBlue();
        }

        return super.onOptionsItemSelected(item);
    }

    public String getExternalPath() {
        String sdPath ;
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        } else
            sdPath = ""+getFilesDir();
        return sdPath;
    }
}
