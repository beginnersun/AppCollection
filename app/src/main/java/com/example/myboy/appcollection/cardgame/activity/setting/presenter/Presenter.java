package com.example.myboy.appcollection.cardgame.activity.setting.presenter;

import com.example.myboy.appcollection.cardgame.base.BasePresenter;

public interface Presenter extends BasePresenter {

    void querySetting();

    void setVolume(int size);

    void setMusicBg(boolean open);

    void setSound(boolean open);

    interface View{

        void setVolume(int progress);

        void setMusicBg(boolean open);

        void setSound(boolean open);

    }

}
