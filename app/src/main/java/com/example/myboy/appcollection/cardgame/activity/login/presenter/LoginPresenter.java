package com.example.myboy.appcollection.cardgame.activity.login.presenter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;

import com.example.myboy.appcollection.cardgame.bean.UserBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import com.example.myboy.appcollection.cardgame.dao.UserDao;

public class LoginPresenter implements Presenter {

    View view;
    UserDao userDao;

    public LoginPresenter(View view) {
        this.view = view;
        userDao = new UserDao();
    }


    @Override
    public void login(Editable username, Editable password) {
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            view.loginFailed("用户名或者密码不能为空！！！");
        }
        else {
            userDao.login(username.toString(), password.toString(), new Observer<UserBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(UserBean userBean) {
                    if (userBean != null) {
                        Activity activity = (Activity) view;
                        if (!activity.isFinishing()) {
                            view.loginSuccess();
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    @Override
    public void register(UserBean userBean) {

    }
}