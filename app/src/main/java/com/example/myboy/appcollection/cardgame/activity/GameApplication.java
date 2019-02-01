package com.example.myboy.appcollection.cardgame.activity;

import android.app.Application;

import com.mob.MobSDK;

public class GameApplication extends Application {

    public static GameApplication application;

    public static GameApplication getInstance(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initSDK();
    }

    private void initSDK(){
        MobSDK.init(this);
    }


}
