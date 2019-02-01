package com.example.myboy.appcollection.desktop.bean;

public class Point {
    /**
     * x,y为组件中心所在的位置
     */
    float x,y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
