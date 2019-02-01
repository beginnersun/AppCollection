package com.example.myboy.appcollection.cardgame.activity.home;

import android.os.Bundle;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.activity.start.presenter.StartPresenter;
import com.example.myboy.appcollection.cardgame.base.BaseActivity;
import com.example.myboy.appcollection.cardgame.base.BasePresenter;

import androidx.annotation.Nullable;

public class HomeActivity extends BaseActivity {

        @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void requestPermissionFailed(String permission) {

    }

    @Override
    protected void requestPermissionSuccess(String permission) {

    }

    @Override
    protected void setPresenter(BasePresenter basePresenter) {

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }

    @Override
    protected void queryInfo() {

    }
}
