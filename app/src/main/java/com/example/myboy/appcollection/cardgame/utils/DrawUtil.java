package com.example.myboy.appcollection.cardgame.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;

/**
 * Created by Home on 2017/7/18.
 */

public class DrawUtil {

    /**
     * 图片上画字
     */
    public static Bitmap drawTextAtBitmap(int size, String text) {

        // 创建一个和原图同样大小的位图
        Bitmap newbit = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newbit);
        Paint paint = new Paint();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        paint.setAntiAlias(true);
        // 在原始位置0，0插入原图
//        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setColor(Color.RED);

        canvas.drawCircle(size / 2, size / 2, size / 2, paint);

        paint.setTextSize(size / 2);

        // 在原图指定位置写上字
        // 将文字画到中间
        Rect bounds = new Rect();

        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (size - bounds.width()) / 2;
        int y = (size + bounds.height()) / 2;

        paint.setColor(Color.WHITE);

        canvas.drawText(text, x, y, paint);

        canvas.save();

        // 存储
        canvas.restore();

        return newbit;
    }

    /**
     * 图片上画字
     */
    public static Bitmap drawTextAtBitmap(int size, String text,int color) {

        // 创建一个和原图同样大小的位图
        Bitmap newbit = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newbit);

        Paint paint = new Paint();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        paint.setAntiAlias(true);
        // 在原始位置0，0插入原图
//        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setColor(color);

        canvas.drawCircle(size / 2, size / 2, size / 2, paint);

        paint.setTextSize(size / 2);

        // 在原图指定位置写上字
        // 将文字画到中间
        Rect bounds = new Rect();

        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (size - bounds.width()) / 2;
        int y = (size + bounds.height()) / 2;

        paint.setColor(Color.WHITE);

        canvas.drawText(text, x, y, paint);

        canvas.save();

        // 存储
        canvas.restore();

        return newbit;
    }

    /**
     * 图片上画字
     */
    public static Bitmap drawTextAtBitmap(Bitmap bitmap, String text) {

        // 创建一个和原图同样大小的位图
        Bitmap newbit = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newbit);

        Paint paint = new Paint();
        // 在原始位置0，0插入原图
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, 0, 0, paint);

//        canvas.drawCircle(size / 2, size / 2, size, paint);

        paint.setTextSize(bitmap.getWidth() / 2);

        // 在原图指定位置写上字
        // 将文字画到中间
        Rect bounds = new Rect();

        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        paint.setColor(Color.WHITE);

        canvas.drawText(text, x, y, paint);

        canvas.save();
//        canvas.save(Canvas.ALL_SAVE_FLAG);

        // 存储
        canvas.restore();

        return newbit;
    }
}
