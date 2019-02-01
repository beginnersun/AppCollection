package com.example.myboy.appcollection.cardgame.bean;

import java.util.ArrayList;

/**
 * Created by This on 2018/6/5.
 */

public class CardTreeTwo {

    private ArrayList<CardBean> two = new ArrayList<>(2);
    private ArrayList<CardBean> three = new ArrayList<CardBean>(3);

    public ArrayList<CardBean> getTwo() {
        return two;
    }

    public void setTwo(ArrayList<CardBean> two) {
        this.two = two;
    }

    public ArrayList<CardBean> getThree() {
        return three;
    }

    public void setThree(ArrayList<CardBean> three) {
        this.three = three;
    }
}
