package com.example.myboy.appcollection.cardgame.activity.login.presenter;

import android.text.Editable;

import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import com.example.myboy.appcollection.cardgame.bean.UserBean;

public interface Presenter extends BasePresenter {

    void login(Editable username, Editable password);

    void register(UserBean userBean);

    interface View{

        void loginSuccess();

        void loginFailed(String error);

        void registerSuccess();

    }
}
