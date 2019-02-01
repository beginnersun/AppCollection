package com.example.myboy.appcollection.cardgame.activity.video.presenter;

import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import com.example.myboy.appcollection.cardgame.bean.VideoBean;

import java.util.List;

public interface Presenter extends BasePresenter {

    void queryVideo();

    void openShare();

    interface View{

        void setData(List<VideoBean> videos);

        void openShare();

        void closeShare();
    }

}
