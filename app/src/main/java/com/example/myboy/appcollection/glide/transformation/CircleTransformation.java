package com.example.myboy.appcollection.glide.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CircleTransformation extends BitmapTransformation {

    private static final String id = "com.example.myboy.appcollection.glide.transformation.CircleTransformation";

    private float corner;

    public CircleTransformation(float corner) {
        this.corner = corner;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(outWidth,outHeight,toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        RectF rectF = new RectF(0,0,result.getWidth(),result.getHeight());
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF,corner,corner,paint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(id.getBytes());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof CircleTransformation;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
