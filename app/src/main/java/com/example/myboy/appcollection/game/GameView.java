package com.example.myboy.appcollection.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    private float position_x;
    private float position_y;
    private int peopleHeight;
    private int peopleWidth;
    private int direction;
    private Bitmap[][] roles;
    private int roleStatus;
    private float speed;


    private Context mContext;
    private SurfaceHolder holder;
    private DrawThread drawThread;


    private int stup_size_l;
    private int stup_size_s;
    private int stup_size_1;
    private int stup_size_2;
    private int stup_size_3;
    private int stup_size_4;

    private static int DEFAULT_DURATION = 100;  //平均1秒30帧   所以默认移动一下移动30毫秒的时间


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
        this.holder.addCallback(this);

        DisplayMetrics outMetrics = getResources().getDisplayMetrics();
        SCREEN_WIDTH = outMetrics.widthPixels;
        SCREEN_HEIGHT = outMetrics.heightPixels;
        Log.e("GameView大小",SCREEN_WIDTH + "   ,   " + SCREEN_HEIGHT);
    }

    private void init(){
        drawThread = new DrawThread();
        roles = createBitmaps(R.mipmap.sprite,4,4);
        stup_size_1 = (int) (peopleWidth/20 * 2);
        stup_size_2 = (int) (peopleWidth/20 * 3);
        stup_size_3 = (int) (peopleWidth/20 * 2);
        stup_size_4 = (int) (peopleWidth/20 * 2);

//        stup_size_l = (int) (peopleWidth/20 * 2.5);
//        stup_size_s = peopleWidth/20 * 2;
//        speed = (float) ((500 * SCREEN_WIDTH / 480) * 0.001);

        this.direction = 0;
        this.roleStatus = 0;
        this.position_x = SCREEN_WIDTH / 2;
        this.position_y = SCREEN_HEIGHT / 2;

        Log.e("GameView大小",position_x + "   ,   " + position_y);
        Bitmap bitmap = getRoleBitmap();
        Canvas canvas = this.holder.lockCanvas();
        try {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
//        canvas.drawCircle(this.position_x,this.position_y,this.position_x,paint);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap,position_x,position_y,new Paint());
            Log.e("GameView","绘制结束");
        }catch (Exception e){
            Log.e("GameView","出错");
            e.printStackTrace();
        }finally {
            Log.e("GameView","提交");
            this.holder.unlockCanvasAndPost(canvas);
        }

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
                bitmap[i][j] = Bitmap.createBitmap(source,j*itemWidth,i*itemHeight,itemWidth,itemHeight);
            }
        }
        return bitmap;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

//    private int getNextDirection(){
//
//        return -1;
//    }

    public void onTouchLeft(){
        setDirection(ROLE_LEFT);
        if (!drawThread.isRunning()){
            drawThread.run();
        }
    }
    public void onTouchRight(){
        setDirection(ROLE_RIGHT);
        if (!drawThread.isRunning()){
            drawThread.run();
        }
    }
    public void onTouchUp(){
        setDirection(ROLE_UP);
        if (!drawThread.isRunning()){
            drawThread.run();
        }
    }
    public void onTouchDown(){
        setDirection(ROLE_DOWN);
        if (!drawThread.isRunning()){
            drawThread.run();
        }
    }

    public void onStop(){
        if (drawThread.isRunning()){
            drawThread.stopThread();
        }
    }

    private Bitmap getRoleBitmap(){
        Bitmap role = roles[direction][roleStatus];
        return role;
    }

    /**
     *
     * @direction 0 1 2 3 分别代表下  左  右  上
     */
    private void updatePosition(int dealTime,int state){
        int add = 0;
        switch (state){
            case 0:
                add = stup_size_1;
                break;
            case 1:
                add = stup_size_2;
                break;
            case 2:
                add = stup_size_3;
                break;
            case 3:
                add = stup_size_4;
                break;
        }
        if (dealTime == 0){
            add = 0;
        }
        switch (direction){
            case 0:
                this.position_y = (int) (this.position_y + add);
                if (this.position_y + peopleHeight >= SCREEN_HEIGHT){
                    this.position_y = SCREEN_HEIGHT - peopleHeight;
                }
                break;
            case 1:
                this.position_x = (int) (this.position_x - add);
                if (this.position_x < 0){
                    this.position_x = 0;
                }
                break;
            case 2:
                this.position_x = (int) (this.position_x + add);
                if (this.position_x > SCREEN_WIDTH){
                    this.position_x = SCREEN_WIDTH - peopleWidth;
                }
                break;
            case 3:
                this.position_y = (int) (this.position_y - add);
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
        if (this.position_x + peopleWidth >= SCREEN_WIDTH){
            per_Right = false;
        }
        if (this.position_y + peopleHeight >= SCREEN_HEIGHT){
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

        private boolean running;
        private int dealTime;
        private long previousTime;

        public void stopThread(){
            synchronized (this) {
                running = false;
            }
        }

        public boolean isRunning() {
            return running;
        }

        @Override
        public void run() {
//            running = true;
            previousTime = System.currentTimeMillis();
//            while (running){
//                canvas.save();
                Bitmap bitmap = getRoleBitmap();
                updatePosition((int) dealTime,roleStatus++);
                roleStatus = roleStatus % 4;  //状态切换为下一个
                Canvas canvas = holder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(bitmap,position_x,position_y,new Paint());
                holder.unlockCanvasAndPost(canvas);
                dealTime = (int) (System.currentTimeMillis() - previousTime);
                if (dealTime < DEFAULT_DURATION){
                    try {
                        Thread.sleep(DEFAULT_DURATION - dealTime);
                        dealTime = DEFAULT_DURATION;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                previousTime = System.currentTimeMillis();
//                canvas.restore();
//            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        init();
        Log.e(TAG, "surfaceCreated: 创建");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged: 改变");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "surfaceDestroyed: 销毁");
        if (drawThread!=null){
            drawThread.stopThread();
        }
    }
}
