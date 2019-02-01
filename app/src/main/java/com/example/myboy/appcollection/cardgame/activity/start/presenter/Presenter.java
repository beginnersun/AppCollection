package com.example.myboy.appcollection.cardgame.activity.start.presenter;

import com.example.myboy.appcollection.cardgame.base.BasePresenter;

public interface Presenter extends BasePresenter {

    void queryPkCount();

    interface View{

        void setNoviciateCount(int size);

        void setJuniorCount(int size);

        void setIntermediateCount(int size);

        void setSeniorCount(int size);

    }
}
