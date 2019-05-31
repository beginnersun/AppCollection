package com.example.myboy.appcollection.cardgame.weigets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.R.attr.mode;

/**
 * Created by MyBoy on 2019/3/7.
 */

public class FlowLayout extends ViewGroup {

    private Context mContext;
    private FlowAdapter flowAdapter;

    public void setFlowAdapter(FlowAdapter flowAdapter) {
        this.flowAdapter = flowAdapter;
    }

    public FlowLayout(Context context) {
        super(context);
        this.mContext = context;
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
            int childWidth = marginLayoutParams.leftMargin + child.getMeasuredWidth() + marginLayoutParams.rightMargin;
            int childHeight = marginLayoutParams.topMargin + child.getMeasuredHeight() + marginLayoutParams.bottomMargin;
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

    private void onAdapterChanger(){
        /**
         * 先清空之前的所有状态与元素
         */
        this.removeAllViews();
        clearStatus();
        Set<Integer> selectors = this.selectIndexs;
        int size = flowAdapter.getChildCount();
        for (int i = 0 ; i < size ; ++i){

            View view = flowAdapter.getView(i,this,flowAdapter.getItem(i));
            TagView tagView = new TagView(view,i);
            if (selectors.contains(i)){              //初始化时 如果已经有默认选中 则调用onSelected 方法
                flowAdapter.onSelected(view,i,flowAdapter.getItem(i));
                tagView.setSelected(true);
            }
            this.addView(view);

            tagView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSelect(tagView);
                }
            });
        }
        selectors = null;
    }

    /**
     * 清空所有状态
     */
    private void clearStatus(){

    }

    private void doSelect(TagView view){
        if (view.isSelected()){ //已经是选中状态
            view.setSelected(false);
            int position = view.getPosition();
            flowAdapter.unSelected(view.getView(),position,flowAdapter.getItem(position));
            selectIndexs.remove(position);
        }else {
            view.setSelected(true);
            int position = view.getPosition();
            flowAdapter.onSelected(view.getView(),position,flowAdapter.getItem(position));
            selectIndexs.add(position);
        }
    }

    private String getSelectInfo(){
        StringBuffer stringBuffer = new StringBuffer("[");
        return stringBuffer.toString();
    }

    private Set<Integer> selectIndexs;

    public void setSelectIndexs(Set<Integer> selectIndexs) {
        this.selectIndexs = selectIndexs;
    }

    public Set<Integer> getSelectIndexs() {
        return selectIndexs;
    }

    public abstract class FlowAdapter<T extends Object>{

        private List<T> mDatas;

        public abstract View getView(int position,FlowLayout parent,T info);

        public abstract void onSelected(View view,int position,T info);

        public abstract void unSelected(View view,int position,T info);

        public int getChildCount(){
            return mDatas!=null?mDatas.size():0;
        }

        public T getItem(int position){
            if (null != mDatas && mDatas.size() > position){
                return mDatas.get(position);
            }
            return null;
        }

    }

    /**
     * Item中View的包装类
     */
    private class TagView{
        private View view;
        private int position;
        private boolean selected;
        private OnClickListener onClickListener;

        public TagView(View view,int position) {
            this(view,position,false);
        }

        public TagView(View view,int position, boolean selected) {
            this.view = view;
            this.selected = selected;
        }

        public View getView() {
            return view;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            this.view.setOnClickListener(onClickListener);
        }
    }

}
