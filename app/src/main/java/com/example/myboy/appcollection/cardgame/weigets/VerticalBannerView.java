package com.example.myboy.appcollection.cardgame.weigets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.transition.Fade;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 垂直滚动轮播的View
 */
public class VerticalBannerView<T> extends ViewSwitcher {

    /**
     * 当前播放到那个item
     */
    private int position;

    /**
     * 所有数据
     */
    private List<T> mDatas = new ArrayList<>();

    /**
     * 用来刷新自定义View的值
     */
    private VerticalViewFactory<T> factory;

    private long duration = 2*1000;

    private long spaceTime;

    private boolean useFade = true,useTranslate = true,useRotate,useScale;

    private BannerHandler handler = new BannerHandler(new SoftReference<>(this));

    private AnimationSet inAnimationSet;

    private AnimationSet outAnimationSet;

    private static class BannerHandler extends Handler{
        private long delay = 2*1000;
        private SoftReference<VerticalBannerView> weakReference;

        public BannerHandler(SoftReference<VerticalBannerView> weakReference) {
            this.weakReference = weakReference;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weakReference.get()!=null){
                weakReference.get().setNext();
                sendMessageDelayed(new Message(),delay);
                Log.e("执行一次","执行");
            }
        }

        public void setDelay(long delay) {
            this.delay = delay;
        }

        public long getDelay() {
            return delay;
        }
    }
    public VerticalBannerView(Context context) {
        super(context);
    }

    public VerticalBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置进入动画
     * @param inAnimation
     */
    @Override
    public void setInAnimation(Animation inAnimation) {
        if (inAnimationSet == null){
            inAnimationSet = new AnimationSet(true);
            inAnimationSet.addAnimation(inAnimation);
        }
        super.setInAnimation(inAnimationSet);
    }

    /**
     * 设置出场动画
     * @param outAnimation
     */
    @Override
    public void setOutAnimation(Animation outAnimation) {
        if (outAnimationSet == null){
            outAnimationSet = new AnimationSet(true);
            outAnimationSet.addAnimation(outAnimation);
        }
        super.setOutAnimation(outAnimationSet);
    }

    /**
     * 设置轮播的间隔时间  和轮播中停留在此item的时间
     * @param spaceTime 停留时间
     * @param delay 间隔时间
     */
    public void setTime(long spaceTime,long delay){
        if (spaceTime < delay) {
            this.spaceTime = spaceTime;
            handler.setDelay(delay);
            setDuration(delay - spaceTime);
        }
    }

    /**
     * @param duration 动画执行时间 单位是毫秒
     */
    private void setDuration(long duration){
        this.duration = duration;
    }


    public void setDatas(@NonNull List<T> mDatas) {
        this.mDatas = mDatas;
    }
    /**
     * 根据数据选择不同的setFactory方法
     * @param factory
     */
    public void setViewFactory(VerticalViewFactory<T> factory) {
        this.factory = factory;
        setFactory(factory);
    }

    @Override
    public void setFactory(ViewFactory factory) {
        super.setFactory(factory);
        this.post(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
    }

    /**
     * 只有轮播的是自定义View才需要用到这个
     */
    public void setNext(){
        final View view = getNextView();
        if (factory != null){
            factory.refreshView(view,getNextItem());
        }else if (view instanceof ImageView){
            Glide.with(this).load(getNextItem()).into((ImageView) view);
        }else if (view instanceof TextView){
            ((TextView) view).setText(getNextItem().toString());
        }
        showNext();
    }

    private T getNextItem(){
        return mDatas.get(++position%mDatas.size());
    }

    private T getCurrentItem(){
        return mDatas.get(position%mDatas.size());
    }

    /**
     * 开启轮播并初始化动画效果
     */
    private void start(){
        initAnimation();
        setCurrentData();
        handler.sendMessageDelayed(new Message(),spaceTime);
    }

    private void setCurrentData(){
        if (factory != null){
            factory.refreshView(getCurrentView(),getCurrentItem());
        }
    }

    private void initAnimation(){
        int h = getHeight();
        if (inAnimationSet == null) {
            inAnimationSet = new AnimationSet(true);
            if (this.useFade) {
                AlphaAnimation alpha_in = new AlphaAnimation(0, 1);
                inAnimationSet.addAnimation(alpha_in);
            }
            if (this.useTranslate) {
                TranslateAnimation translate_in = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, h, Animation.ABSOLUTE, 0);
                translate_in.setInterpolator(new LinearInterpolator());
                inAnimationSet.addAnimation(translate_in);
            }
        }
        if (outAnimationSet == null){
            outAnimationSet = new AnimationSet(true);
            if (this.useFade) {
                AlphaAnimation alpha_out = new AlphaAnimation(1, 0);
                outAnimationSet.addAnimation(alpha_out);
            }
            if (this.useTranslate) {
                TranslateAnimation translate_out = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -h);
                translate_out.setInterpolator(new LinearInterpolator());
                outAnimationSet.addAnimation(translate_out);
            }
        }
        inAnimationSet.setDuration(getDuration());
        outAnimationSet.setDuration(getDuration());
        setInAnimation(inAnimationSet);
        setOutAnimation(outAnimationSet);
    }

    /**
     * 是否开启淡入淡出动画（默认开启）
     * @param fade
     */
    public void enableFade(boolean fade){
        this.useFade = fade;
    }

    /**
     * 是否开启平移动画（默认开启）
     * @param translate
     */
    public void enableTranslate(boolean translate){
        this.useTranslate = translate;
    }

    /**
     * 是否开启缩放动画 （默认不开）
     * @param scale
     */
    public void enableScale(boolean scale){
        this.useScale = scale;
    }

    /**
     * 是否开启旋转动画 （默认不开）
     * @param rotate
     */
    public void enableRotate(boolean rotate){
        this.useRotate = rotate;
    }

    public static abstract class VerticalViewFactory<T> implements ViewFactory{

        protected abstract void refreshView(View view,@NonNull T t);

    }

    public long getDuration() {
        return duration;
    }
}
