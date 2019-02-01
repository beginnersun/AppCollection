package com.example.myboy.appcollection.desktop.base;

import android.os.Bundle;

import com.example.myboy.appcollection.desktop.bean.BasePresenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    BasePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourcesId());
        initView();
        initData();
    }

    /**
     * 获取子类传入的layoutid
     * @return
     */
    protected abstract int getLayoutResourcesId();

    protected abstract void initData();

    protected abstract void initView();

    public void setPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }
}
