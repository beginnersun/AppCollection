package com.example.myboy.appcollection.desktop.presenter;

import android.animation.Animator;
import android.view.View;

import com.example.myboy.appcollection.desktop.bean.MenuItem;

import java.util.ArrayList;

public class DeskTopPresenter implements Presenter {

    View view;
    ArrayList<MenuItem> menuItems;
    boolean state;


    public DeskTopPresenter(View view) {
        this.view = view;
        menuItems = new ArrayList<>();
        setButtonState(state);
    }

    @Override
    public void queryMenuItems() {
        menuItems.add(new MenuItem(0,"QQ"));
        menuItems.add(new MenuItem(1,"微信"));
        menuItems.add(new MenuItem(2,"三国杀"));
        view.setMenuItems(menuItems);
        view.initViews();
        view.setItemVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void setButtonState(boolean state) {
        this.state = state;
        if(this.state){ //打开菜单  进行开始动画
            view.setItemVisibility(android.view.View.VISIBLE); //动画开始前 让子View显示出来
            view.startAnimator();
        }else{  //动画完成监听事件
            view.endAnimator(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    view.setItemVisibility(android.view.View.INVISIBLE); //动画完成隐藏View
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    @Override
    public boolean getButtonState() {
        return this.state;
    }

    @Override
    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

}
