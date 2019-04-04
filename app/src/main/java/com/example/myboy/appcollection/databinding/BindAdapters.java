package com.example.myboy.appcollection.databinding;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myboy.appcollection.cardgame.activity.SampleApplicationLike;

import androidx.databinding.BindingAdapter;

public class BindAdapters {

    @BindingAdapter("loadBingPic")
    public static void loadPicture(ImageView imageView, String url){
        Glide.with(SampleApplicationLike.getInstance()).load(url).into(imageView);
    }

}
