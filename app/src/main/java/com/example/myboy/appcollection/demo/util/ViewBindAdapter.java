package com.example.myboy.appcollection.demo.util;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myboy.appcollection.GlideApp;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

public class ViewBindAdapter {

    @BindingAdapter({"type","load_picture"})
    public static void loadPicture(ImageView imageView,String type,String url){
        Log.e("获取信息",type+ "  " +url);
        GlideApp.with(imageView.getContext()).load(url).centerCrop().circleCrop().into(imageView);
    }
    @BindingAdapter({"type","load_picture_res"})
    public static void loadPicture(ImageView imageView,String type,@DrawableRes int id){
    }

    @BindingAdapter({"type","load_background"})
    public static void loadBackground(View view,String type,String url){

    }

    @BindingAdapter({"type","load_background_res"})
    public static void loadBackground(View view, String type, @DrawableRes int id){

    }
}
