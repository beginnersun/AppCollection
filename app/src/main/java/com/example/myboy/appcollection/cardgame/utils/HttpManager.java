package com.example.myboy.appcollection.cardgame.utils;

import com.example.myboy.appcollection.cardgame.api.UserApI;
import com.example.myboy.appcollection.cardgame.constant.Constant;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {

    public static HttpManager instance;
    private Retrofit mRetrofit;
    //请求超时时间
    private static final int REQUEST_TIME = 10;

    private HttpManager(){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(REQUEST_TIME, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIME, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIME, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create()) //只需要在这边添加就好
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.base_url)
                .build();
        mRetrofit.create(UserApI.class);
    }


    public static HttpManager getInstance(){
        if(instance == null) {
            synchronized (HttpManager.class) {
                if(instance == null){
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public Retrofit getmRetrofit() {
        return mRetrofit;
    }
}
