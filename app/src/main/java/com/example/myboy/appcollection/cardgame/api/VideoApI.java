package com.example.myboy.appcollection.cardgame.api;

import com.example.myboy.appcollection.cardgame.bean.VideoBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VideoApI {

    @GET("video/queryAll")
    Observable<ArrayList<VideoBean>> queryVideo();

}
