package com.example.myboy.appcollection.cardgame.bean;

import java.util.ArrayList;

/**
 * Created by This on 2018/6/5.
 */

public class CardTreeOne {

    private CardBean one;
    private ArrayList<CardBean> three = new ArrayList<CardBean>(3);

    public CardBean getOne() {
        return one;
    }

    public void setOne(CardBean one) {
        this.one = one;
    }

    public ArrayList<CardBean> getThree() {
        return three;
    }

    public void setThree(ArrayList<CardBean> three) {
        this.three = three;
    }
}
