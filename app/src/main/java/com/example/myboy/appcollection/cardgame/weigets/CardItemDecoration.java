package com.example.myboy.appcollection.cardgame.weigets;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by This on 2018/6/5.
 */

public class CardItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Log.e("数量为",parent.getChildCount()+"  "+ "当前项   "+parent.getChildAdapterPosition(view));
        if(parent.getChildAdapterPosition(view)!=0) {
            outRect.left = -200;
        }
    }
}
