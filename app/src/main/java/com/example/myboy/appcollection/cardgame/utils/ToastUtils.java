package com.example.myboy.appcollection.cardgame.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myboy.appcollection.R;

public class ToastUtils {

    public static void toastCenter(){

    }

    public static void toastError(Context context,String content,int duration){
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_error,null);
        TextView tvContent = view.findViewById(R.id.toast_content);
        tvContent.setText(content);
        toast.setView(view);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * 吐司提示  成功
     * @param context 上下文
     * @param content 内容
     * @param duration 持续时间 Toast.Long / Toast.short ...
     */
    public static void toastSuccess(Context context,String content,int duration){
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_success,null);
        TextView tvContent = view.findViewById(R.id.toast_content);
        tvContent.setText(content);
        toast.setView(view);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
