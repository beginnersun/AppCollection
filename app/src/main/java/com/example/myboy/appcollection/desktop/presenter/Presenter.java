package com.example.myboy.appcollection.desktop.presenter;

import android.animation.Animator;

import com.example.myboy.appcollection.desktop.bean.BasePresenter;
import com.example.myboy.appcollection.desktop.bean.MenuItem;

import java.util.ArrayList;

public interface Presenter extends BasePresenter {

    void queryMenuItems();

    void setButtonState(boolean state);

    boolean getButtonState();

    void addMenuItem(MenuItem item);

    interface View{

        void setMenuItems(ArrayList<MenuItem> menuItems);

        void initViews();

        void startAnimator();

        void endAnimator(Animator.AnimatorListener listener);

        void setItemVisibility(int state);

    }
}

