package com.example.myboy.appcollection.cardgame.weigets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.mode;

/**
 * Created by MyBoy on 2019/3/7.
 */

public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizewidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeheight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int linwidth = 0;

        int height = 0;
        int lineHeight = 0; //保存行高

        int childCount = getChildCount();
        for (int i = 0;i < childCount; i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = marginLayoutParams.leftMargin + child.getWidth() + marginLayoutParams.rightMargin;
            int childHeight = marginLayoutParams.topMargin + child.getHeight() + marginLayoutParams.bottomMargin;
            if (linwidth + childWidth > sizewidth){
                height = height + lineHeight;
                width = Math.max(width,linwidth);
                //换行后当前行高和行宽重新计算
                lineHeight =  childHeight;
                linwidth = childWidth;
            }else {
                linwidth = linwidth + childWidth;
                height = Math.max(height,lineHeight);
            }
            if (i == childCount -1){
                height = height + lineHeight;
                width = Math.max(width,linwidth);
            }
        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY)?sizewidth:width,(mode == MeasureSpec.EXACTLY)?sizeheight:height);

    }

    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        mAllViews.clear();
        mLineHeight.clear();
        int width = getWidth();
        int linwidth = 0;

        int lineHeight = 0; //保存行高

        List<View> lineViews = new ArrayList<View>();
        int childCount = getChildCount();
        for (int j = 0;j < childCount; j++){
            View child = getChildAt(j);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + params.leftMargin + params.rightMargin + linwidth > width){
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                lineViews = new ArrayList<View>();
                linwidth = 0;
            }
            linwidth = linwidth + childWidth + params.leftMargin + params.rightMargin;
            lineHeight = Math.max(lineHeight,childHeight + params.topMargin + params.bottomMargin);
            lineViews.add(child);
        }
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        int left =0, top =0;
        int count = 0;
        for (List<View> views:mAllViews){
            lineHeight = mLineHeight.get(count++);
            for (View view:views){
                if(view.getVisibility() == View.GONE){
                    continue;
                }
                MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                int l = left + params.leftMargin;
                int t = top + params.topMargin;
                int r = l + view.getMeasuredWidth();
                int bo = t + view.getMeasuredHeight();
                view.layout(l,t,r,bo);
                left = left + view.getMeasuredWidth() + params.rightMargin + params.leftMargin;
            }
            left = 0;
            top = top + lineHeight;
        }

    }

}
