package com.example.myboy.appcollection.cardgame.weigets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.myboy.appcollection.cardgame.utils.CalendarUtil;

import java.util.Calendar;

import androidx.annotation.Nullable;

public class CalendarView extends View {
    /**
     * 顶部栏高度
     */
    private int head_height;
    /**
     * 表头高度 (就是周一 周二。。高度)
     */
    private int title_height;
    /**
     * 行高
     */
    private int item_height;
    /**
     * 行宽
     */
    private int item_width;

    /**
     * 当前日历 数组
     */
    private int[][] days;
    /**
     * 日历item是否能够点击
     */
    private boolean days_enable[][] = new boolean[6][7];

    private Rect[][] dayBound = new Rect[6][7];

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        days = CalendarUtil.currentTime();
        head_height = 100;

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                
                return false;
            }
        });
    }

    private void drawHead(Canvas canvas) {
        Rect textBound = new Rect();
        Paint textPaint = new Paint();
        String text = CalendarUtil.getCurrentTime();
        textPaint.getTextBounds(text, 0, text.length(), textBound);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(45);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textPaint.setTextAlign(Paint.Align.LEFT);
        float font_height = fontMetrics.bottom - fontMetrics.top;
        float font_center = font_height / 2;
        float center_baseLine = font_center - fontMetrics.bottom;   //基线到中间位置的高度

        if (center_baseLine < 0) {
            center_baseLine = -center_baseLine;
        }
        canvas.drawText(text, getPaddingLeft(), (head_height) / 2 + center_baseLine, textPaint);
    }

    private void drawUpButton(Canvas canvas) {
        Paint pathPaint = new Paint();
        int bitmap_width = head_height;
        int bitmap_height = head_height;
        int start_width = getWidth() - head_height * 2;
        Path path = new Path();
        path.moveTo(start_width + bitmap_width / 4, 5 * bitmap_height / 8);
        path.lineTo(start_width + bitmap_width / 2, 3 * (bitmap_height / 4) / 2);
        path.lineTo(start_width + 3 * bitmap_width / 4, 5 * bitmap_height / 8);

        pathPaint.setAntiAlias(true);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.BLACK);
        pathPaint.setStrokeWidth(5);
        canvas.drawPath(path, pathPaint);
    }

    private void drawBottomButton(Canvas canvas) {
        Paint pathPaint = new Paint();
        int bitmap_width = head_height;
        int bitmap_height = head_height;
        int start_width = getWidth() - head_height * 1;
        Path path = new Path();
        path.moveTo(start_width + bitmap_width / 4, 3 * (bitmap_height / 4) / 2);
        path.lineTo(start_width + bitmap_width / 2, 5 * bitmap_height / 8);
        path.lineTo(start_width + 3 * bitmap_width / 4, 3 * (bitmap_height / 4) / 2);

        pathPaint.setAntiAlias(true);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.BLACK);
        pathPaint.setColor(Color.parseColor("#676767"));
        pathPaint.setStrokeWidth(5);
        canvas.drawPath(path, pathPaint);
    }

    private void initDate() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Rect rect = new Rect(i * item_height, j * item_height, (i + 1) * item_height, (j + 1) * item_height);
                dayBound[i][j] = rect;
            }
        }
    }

    private void drawCenterText(Canvas canvas, String text, int left, int height) {
        Rect textBound = new Rect();
        Paint textPaint = new Paint();
        textPaint.getTextBounds(text, 0, text.length(), textBound);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(45);

        textPaint.setTextSize(45);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textPaint.setTextAlign(Paint.Align.LEFT);
        float font_height = fontMetrics.bottom - fontMetrics.top;
        float font_center = font_height / 2;
        float center_baseLine = font_center - fontMetrics.bottom;   //基线到中间位置的高度

        float font_width = textBound.right - textBound.left;
        if (center_baseLine < 0) {
            center_baseLine = -center_baseLine;
        }
        canvas.drawText(text, left + (item_width - font_width) / 2, height + item_height / 2 + center_baseLine, textPaint);
    }

    private void drawDays(Canvas canvas) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int top = head_height + title_height;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (days[i][j] == day){  //处于当前日期
                    drawSelectBg(dayBound[i][j],canvas,j * item_width, top + i * item_height);
                }
                drawCenterText(canvas, days[i][j] + "", j * item_width, top + i * item_height);

            }
        }
    }

    private void drawSelectBg(Rect rect,Canvas canvas,int left,int top){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#cc7832"));
        canvas.drawCircle(left + item_width/2 , top + item_height/2 , item_width/2 ,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();
        int width = getWidth();
        int height = getHeight();
        if (item_height == 0) {
            item_height = (height - head_height - title_height) / 6;
            item_width = (width) / 6;
        }
        Log.e("measure size:", measureWidth + " , " + measureHeight);
        Log.e("size:", width + " , " + height);
        Paint backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        Paint headPaint = new Paint();
        headPaint.setColor(Color.WHITE);
        headPaint.setAntiAlias(true);
        Rect head = new Rect(0, 0, width, head_height);
        canvas.drawRect(head, headPaint);
//        StaticLayout staticLayout = new StaticLayout();
//        TextPaint paint = new TextPaint(headPaint);


        drawHead(canvas);
        drawUpButton(canvas);
        drawBottomButton(canvas);
        for (int i = 0; i < 6; i++) {
            Rect rect = new Rect();
            rect.left = 0;
            rect.top = head_height + title_height + i * item_height;
            rect.right = width;
            rect.bottom = head_height + title_height + (i + 1) * item_height;
            if (i % 2 == 0) {
                backgroundPaint.setColor(Color.RED);
                canvas.drawRect(rect, backgroundPaint);
            } else {
                backgroundPaint.setColor(Color.YELLOW);
                canvas.drawRect(rect, backgroundPaint);
            }
        }
        initDate();
        drawDays(canvas);

        super.onDraw(canvas);
    }


    private void initData() {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
