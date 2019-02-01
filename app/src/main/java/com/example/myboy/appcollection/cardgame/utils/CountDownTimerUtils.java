package com.example.myboy.appcollection.cardgame.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class CountDownTimerUtils extends CountDownTimer {

    private SoftReference<TextView> mTextView;

    public CountDownTimerUtils(long millisInFuture, long countDownInterval, SoftReference<TextView> mTextView) {
        super(millisInFuture, countDownInterval);
        this.mTextView = mTextView;
    }

    @Override
    public void onTick(long l) {
        if(mTextView.get()==null){
            cancel();
            return;
        }
        this.mTextView.get().setClickable(false);
        this.mTextView.get().setText("剩余"+"("+(l/1000)+")"+"秒");
        SpannableString spannableString = new SpannableString(this.mTextView.get().getText().toString());
        ForegroundColorSpan span = new ForegroundColorSpan(Color.GRAY);
        spannableString.setSpan(span,4,8,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        this.mTextView.get().setText(spannableString);
    }

    @Override
    public void onFinish() {
        if(mTextView.get()!=null){
            this.mTextView.get().setClickable(true);
            this.mTextView.get().setText("获取验证码");
        }
    }


}
