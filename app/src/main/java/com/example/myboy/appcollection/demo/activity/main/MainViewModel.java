package com.example.myboy.appcollection.demo.activity.main;

import android.util.Log;

import com.example.myboy.appcollection.demo.base.BaseViewModel;
import com.example.myboy.appcollection.demo.bean.User;

import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends BaseViewModel {

    private String imageHead[] = new String[]{"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1377960405,1383813695&fm=26&gp=0.jpg",
    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554268540804&di=40930eb526c6b96fae1da491b2730495&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1664334804%2C2497708651%26fm%3D214%26gp%3D0.jpg"};

    private String userName[] = new String[]{"你好，陌生人！","你好，过路人！"};
    private MutableLiveData<User> liveData = new MutableLiveData<>();

    public MutableLiveData<User> getLiveData() {
        return liveData;
    }

    public void setLiveData(MutableLiveData<User> liveData) {
        this.liveData = liveData;
    }

    public void RefreshUser(){
        new Thread(new Runnable() {
            int count = 0;
            @Override
            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e("让我睡一秒", "睡觉");
                    liveData.postValue(new User(userName[count%2], "test_me", "15280593883", imageHead[count++ % 2], "我在打代码,别问我代码是谁！"));
//                }
            }
        }).start();
    }
}
