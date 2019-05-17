package com.example.myboy.appcollection.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myboy.appcollection.R;

import java.io.InputStream;

import androidx.annotation.DrawableRes;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {



    private static final String TAG = "GameView";
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

    private int position_x;
    private int position_y;
    private int peopleHeight;
    private int peopleWidth;
    private int direction;
    private Bitmap[][] roles;
    private int roleStatus;
    private double speed;


    private Context mContext;
    private SurfaceHolder holder;


    private static int DEFAULT_DURATION = 30;  //平均1秒30帧   所以默认移动一下移动30毫秒的时间


    public static int ROLE_LEFT = 1;
    public static int ROLE_RIGHT = 2;
    public static int ROLE_UP = 3;
    public static int ROLE_DOWN = 0;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.holder = getHolder();

        DisplayMetrics outMetrics = getResources().getDisplayMetrics();
        SCREEN_WIDTH = outMetrics.widthPixels;
        SCREEN_HEIGHT = outMetrics.heightPixels;

    }

    public Bitmap decodeBitmapFromRes(int resourseId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = mContext.getResources().openRawResource(resourseId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    private Bitmap[][] createBitmaps(@DrawableRes int resId, int rows, int clos){
        Bitmap[][] bitmap = new Bitmap[rows][clos];
        Bitmap source = decodeBitmapFromRes(resId);
        int itemWidth = source.getWidth()/4;
        int itemHeight = source.getHeight()/4;
        this.peopleWidth = itemWidth;
        this.peopleHeight = itemHeight;
        for (int i = 0 ;i < 4 ;i++){
            for (int j = 0 ;j < 4 ;j++){
                bitmap[i][j] = Bitmap.createBitmap(source,i*itemWidth,j*itemHeight,itemWidth,itemHeight);
            }
        }
        return null;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

//    private int getNextDirection(){
//
//        return -1;
//    }

    private void onTouchLeft(){
        setDirection(ROLE_LEFT);
    }

    private Bitmap getRoleBitmap(){
        Bitmap role = roles[direction][roleStatus++];
        roleStatus = roleStatus % 4;
        return role;
    }

    /**
     *
     * @direction 0 1 2 3 分别代表下  左  右  上
     */
    private void updatePosition(int dealTime){
        switch (direction){
            case 0:
                this.position_y = (int) (this.position_y + (this.speed) * dealTime);
                if (this.position_y + peopleHeight >= SCREEN_HEIGHT){
                    this.position_y = SCREEN_HEIGHT - peopleHeight;
                }
                break;
            case 1:
                this.position_x = (int) (this.position_x - this.speed*dealTime);
                if (this.position_x < 0){
                    this.position_x = 0;
                }
                break;
            case 2:
                this.position_x = (int) (this.position_x + this.speed*dealTime);
                if (this.position_x > SCREEN_WIDTH){
                    this.position_x = SCREEN_WIDTH - peopleWidth;
                }
                break;
            case 3:
                this.position_y = (int) (this.position_y + this.speed * dealTime);
                if (this.position_y < 0 ){
                    this.position_y = 0;
                }
                break;
        }
        this.setDirectionAllowed();
        if (this.position_y == 0 ){
            per_Up = false;
        }
        if (this.position_x == 0){
            per_Left = false;
        }
        if (this.position_x + peopleWidth == SCREEN_WIDTH){
            per_Right = false;
        }
        if (this.position_y + peopleHeight == SCREEN_HEIGHT){
            per_Down = false;
        }
    }

    private void setDirectionAllowed(){
        per_Left = true;
        per_Right = true;
        per_Up = true;
        per_Down = true;
    }

    private boolean per_Left = true;
    private boolean per_Right = true;
    private boolean per_Up = true;
    private boolean per_Down = false;

    class DrawThread implements Runnable{

        private boolean isRunning;

        public void stopThread(){
            isRunning = false;
        }

        @Override
        public void run() {
            isRunning = true;
            long previousTime = 0;
            int dealTime = 0;
            while (isRunning){
                Canvas canvas = holder.lockCanvas();
                Bitmap bitmap = getRoleBitmap();
                updatePosition((int) dealTime);
                canvas.drawBitmap(bitmap,position_x,position_y,null);
                dealTime = (int) (System.currentTimeMillis() - previousTime);
                if (dealTime < DEFAULT_DURATION){
                    try {
                        Thread.sleep(DEFAULT_DURATION - dealTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                previousTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreated: 创建");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged: 改变");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "surfaceDestroyed: 销毁");
    }
}
