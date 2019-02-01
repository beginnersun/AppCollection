package com.example.myboy.appcollection.cardgame.weigets;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by This on 2018/6/5.
 */

public class MyLinearLayoutManager extends LinearLayoutManager {
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public int pointToPosition(int x,int y){
        Rect rect = new Rect();
        for(int i = 0 ;i <= getChildCount();i++) {
            View view = getChildAt(i);
            if(view!=null) {
                view.getHitRect(rect);
                if (rect.contains(x, y)) {
                    return i;
                }
            }
        }
        return -1;
    }

}
