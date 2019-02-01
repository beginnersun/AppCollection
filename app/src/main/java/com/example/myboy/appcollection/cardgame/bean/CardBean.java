package com.example.myboy.appcollection.cardgame.bean;


import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * 对应的卡牌Bean
 * Created by This on 2018/6/5.
 */

public class CardBean implements Comparable<CardBean>{

    /**
     * 大王和小王
     */
    public static final int BIG_CARD_S = 16; //black Joker 小王
    public static final int BIG_CARD_B = 17; //red Joker 大王


    private ArrayList<CardBean> parent; //卡牌对应所在的卡牌组

    /**
     * category 代表类型
     * num代表卡牌显示的具体字符串 比如:A:2:3:4:......
     * 按照number（扑克牌大小顺序排序） 2 对应 15 | A 对应 14
     */
    private int category;
    private String num;
    private int number;

    private boolean isSelected; // 被选中(背景变蓝 一般为全选才会)
    private boolean up;//准备出牌(牌会上移)  选中不一定是上移 所以不能用一个boolean

    public int getNumber() {
        return number;
    }

    public CardBean(ArrayList<CardBean> parent) {
        this.parent = parent;
    }

    public CardBean(int category, int number, boolean isSelected, boolean up,ArrayList<CardBean> parent) {
        this.category = category;
        this.number = number;
        this.isSelected = isSelected;
        this.up = up;
        this.parent = parent;
        switch (number){
            case 10:{
                this.num = "10";
                break;
            }
            case 11:{
                this.num = "J";
                break;
            }
            case 12:{
                this.num = "Q";
                break;
            }
            case 13:{
                this.num = "K";
                break;
            }
            case 14:{
                this.num = "A";
                break;
            }
            case 15:{
                this.num = "2";
                break;
            }
            case 16:{
                this.num = "READJOKER";
                break;
            }
            case 17:{
                this.num = "BLACKJOKER";
                break;
            }
            default:{
                this.num = number+"";
                break;
            }
        }
    }

    /**
     * 打出去牌后 删除listView中的此牌后 此牌对应的parent也要删除
     * 在List中删除自己
     * @return
     */
    public CardBean removeInParent(){
        this.parent.remove(this);
        return this;
    }

    public boolean equalNum(@NonNull CardBean bean){
        if(bean.getNumber() == this.number){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        CardBean bean = (CardBean) obj;
        if(bean.category == category && bean.num == num){ //颜色和数字都相同 才说明相同
            return true;
        }else{
            return false;
        }
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NonNull CardBean o) {
        if(o.number < this.number){
            return 1;
        }else if(o.number > this.number){
            return -1;
        }
        return 0;
    }
}
