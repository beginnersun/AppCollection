package com.example.myboy.appcollection.cardgame.weigets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.myboy.appcollection.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * Created by Home on 2019/3/12.
 */

public class BitmapView extends View {
    /**
     * 宽和高的大小模式
     */
    private int width_mode, height_mode;
    /**
     * 与边界间距（默认为5）
     */
    private int padding = 5;  //单位px
    /**
     * 关键字
     */
    private String key = "";
    /**
     * 全部内容
     */
    private String text = "";
    /**
     * text的大小
     */
    private int text_size = 12;
    /**
     * key的大小
     */
    private int key_size = 12;
    /**
     * 关键字背景（默认为灰色背景）
     */
    private Drawable drawable = new ColorDrawable(Color.GRAY);

    /**
     * 关键字的背景大小
     */
    private int backround_size = 50; //单位px

    /**
     * 关键字颜色  默认为红色
     */
    private int key_color = Color.RED;
    /**
     * 文本颜色  默认亮灰色
     */
    private int text_color = Color.BLACK;

    /**
     * 行间距
     */
    private int leading = 0;
    /**
     * 字符间隙
     */
    private int space = 0;
    /**
     * 对齐方式
     */
    private Paint.Align align = Paint.Align.LEFT;

    /**
     * 关键字的下标列表
     */
    private ArrayList<Integer> indexs = new ArrayList<>();

    private TextPaint textPaint;
    private Paint paint;

    private TextPaint keyTextPaint;
    private Paint keyPaint;

    public BitmapView(Context context) {
        super(context);
    }


    public BitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray arrays = context.obtainStyledAttributes(attrs, R.styleable.BitmapView);

        for (int i = 0; i < arrays.getIndexCount(); i++) {
            int attr = arrays.getIndex(i);
            switch (attr) {
                case R.styleable.BitmapView_key_word:
                    key = TextUtils.isEmpty(arrays.getString(attr)) ? key : arrays.getString(attr);
                    break;
                case R.styleable.BitmapView_key_background:
                    drawable = arrays.getDrawable(attr) == null ? drawable : arrays.getDrawable(attr);
                    break;
                case R.styleable.BitmapView_key_color:
                    key_color = arrays.getColor(attr, key_color);
                    break;
                case R.styleable.BitmapView_text:
                    text = TextUtils.isEmpty(arrays.getString(attr)) ? text : arrays.getString(attr);
                    break;
                case R.styleable.BitmapView_key_size:
                    key_size = arrays.getDimensionPixelSize(attr, key_size);
                    break;
                case R.styleable.BitmapView_text_size:
                    text_size = arrays.getDimensionPixelSize(attr, text_size);
                    break;
                case R.styleable.BitmapView_text_color:
                    text_color = arrays.getColor(attr, text_color);
                    break;
            }
        }
        arrays.recycle();
        initTextPaint();
        initKeyPaint();
        initKeyIndexs();
    }

    /**
     * 定位关键字在总文中的下标
     */
    private void initKeyIndexs() {
        String info = new String(text);
        while (info.contains(key) && !TextUtils.isEmpty(key)) {
            indexs.add(info.indexOf(key) + (indexs.size() * key.length()));
            info = info.replaceFirst(key, "");
        }
    }

    /**
     * 初始化文本Paint
     */
    private void initTextPaint() {
        paint = new Paint();
        paint.setColor(text_color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setTextSize(text_size);
        paint.setTextAlign(align);
        textPaint = new TextPaint();
        textPaint.set(textPaint);
        paint = new Paint(paint);
    }

    /**
     * 初始化关键字Paint
     */
    private void initKeyPaint() {
        keyPaint = new Paint();
        keyPaint.setColor(key_color);
        keyPaint.setAntiAlias(true);
        keyPaint.setDither(true);
        keyPaint.setFilterBitmap(true);
        keyPaint.setTextSize(key_size);
        keyPaint.setTextAlign(align);
        keyTextPaint = new TextPaint(keyPaint);
        keyPaint = new Paint(keyPaint);
    }

    /**
     * 初始化文本内容,并获取绘制文本信息 ，默认垂直方向居中
     *
     * @param content 要获取文字对应位置的信息
     * @param full    文字是否已经充满当前行
     */
    private float initText(String content, boolean full) {
        Rect rect = new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float x = 0, y = 0;  //x 代表文本开始绘制的位置
        switch (align) {
            case LEFT:
                x = 0;
                break;
            case RIGHT:
                x = rect.left - rect.right; //这边是取负值  （如果是自适应，不存在右对齐  如果不是则用宽度+x就可以)
                break;
            case CENTER:
                x = ((rect.right - rect.left) * 1.0f / 2); //自适应的话文本中间值就是居中对齐，不是自适应，宽/2
                break;
        }
        y = ((rect.bottom - rect.top) - (metrics.descent)/2);  //bottom - decent 为基线距离推荐文字绘制位置的距离
        /**
         * 进过上述处理之后 的x y 为当前垂直（左右中对齐的最佳位置）
         */
        Log.e("BitView处理后的位置",x + "," + y);
        return y + getPaddingTop();
    }

    private void measureText(){
        int total_l = 0;
        for (int i =0 ;i < lines ;i ++){
            String info = text.substring(lin_size*i,lin_size*(i+1)>text.length()?text.length():lin_size*(i+1));

            float baseline = initText(info,false);

            total_l = total_l + info.length();

            float left = getPaddingLeft();
            int start = 0;
            int index = 0;
            a:if (indexs.size() > 0 && indexs.get(0) < total_l){ //已经截取的字符串包含关键字或关键字的一部分
                index = indexs.get(0);
                while(index + key.length() <= total_l){
                    index = index - lin_size * i;
                    String info1 = info.substring(start,index);
                    contents.add(info1);
                    baselines.add(baseline + (text_height + leading) * i);
                    baseleft.add(left);

                    /**
                     * left 是下一组字符串的开头
                     */
                    left = left + paint.measureText(info1,0,info1.length());
                    String info2 = info.substring(index,index+key.length());
                    contents.add(info2);
                    baselines.add(baseline + (text_height + leading) * i);
                    baseleft.add(left);

                    left = left + paint.measureText(key,0,key.length());
                    start = index + key.length();
                    indexs.remove(0);
                    if (indexs.size()>0) {
                        index = indexs.get(0);
                    }else{
                        break a; //直接跳出整个if
                    }
                }
                if (index <=total_l){ //包含部分关键字
                    String info1 = info.substring(start);
                    contents.add(info1);
                    baselines.add(baseline + (text_height + leading) * i);
                    baseleft.add(left);
                }else { // 不包含关键字
                    /**
                     * 先按不标记处理
                     */
                    String info1 = info.substring(start);
                    contents.add(info1);
                    baselines.add(baseline + (text_height + leading) * i);
                    baseleft.add(left);
                }
            }else { //不包含的话
                contents.add(info);
                baselines.add(baseline + i * ( text_height + leading ));
                baseleft.add(0f);
            }
            if (index < total_l ){
                String info1 = info.substring(start);
                contents.add(info1);
                baselines.add(baseline + (text_height + leading) * i);
                baseleft.add(left);
            }
        }
    }

    private ArrayList<String> contents = new ArrayList<>();

    private ArrayList<Float> baselines = new ArrayList<>();

    private ArrayList<Float> baseleft = new ArrayList<>();

    private int lin_size;

    private int lines;

    private int text_height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width_mode = MeasureSpec.getMode(widthMeasureSpec);
        height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        Rect rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        int text_width = rect.right - rect.left; // 文本总长
        text_height = rect.bottom - rect.top;
        int line_width = 0;
        int line_height = 0; //文本高度
        lines = 0; //文本行数
        lines = (int) Math.ceil(text_width*1.0 / width); //向上取整
        lin_size = text.length() / lines;
        int i = 1;
        while((lin_size+ i)< text.length() && paint.measureText(text.substring(0,lin_size+i)) + getPaddingLeft() + getPaddingRight() < width){  //寻找单行能绘制的最长字符数
            i++;
            if ( (lin_size + i) < text.length() && paint.measureText(text.substring(0,lin_size+i)) + getPaddingLeft() + getPaddingRight() >= width){
                i--;
                break;
            }
        }
        if (lin_size + i < text.length()) { //如果不止有一行 那么说明上述循环的破除条件是找到了当前最长字符 而不是因为字符数不满足一行
            lin_size = lin_size + i;
        }
        line_width = (int) paint.measureText(text.substring(0,lin_size)) + getPaddingLeft() + getPaddingRight();  //最终将 margin 。。。等加入测量的宽度和高度
        line_height = text_height * lines + (lines-1)*leading + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width_mode == MeasureSpec.EXACTLY?width:line_width,height_mode == MeasureSpec.EXACTLY?height:line_height); //根据 布局中是固定值还是自适应来赋值
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        measureText();String message = "paint,draw paint指用颜色画,如油画颜料、水彩或者水墨画,而draw 通常指用铅笔、钢笔或者粉笔画,后者一般并不涂上颜料。两动词的相应名词分别为p";
//         StaticLayout myStaticLayout = new StaticLayout(message, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
//         myStaticLayout.draw(canvas);

        for (int i = 0 ;i < contents.size() ; i++){
            String content = contents.get(i);
            float x = baseleft.get(i);
            float y = baselines.get(i);
            if (content.length() == key.length() && TextUtils.equals(content,key)) {
                canvas.drawText(content, x, y, keyPaint);
            }else{
                canvas.drawText(content,x,y,paint);
            }
        }
    }

    private void getBitMapForSize(BitmapDrawable drawable) {

//        Canvas canvas = new Canvas(drawable);
        Bitmap bitmap = drawable.getBitmap();

    }

    private void drawItem_background(Drawable drawable) {
        if (width_mode == height_mode && width_mode == MeasureSpec.AT_MOST) {

        }
    }

    private void drawItem_word(String text) {

    }

}
