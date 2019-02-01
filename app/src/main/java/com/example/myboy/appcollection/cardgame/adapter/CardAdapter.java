package com.example.myboy.appcollection.cardgame.adapter;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.bean.CardBean;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * Created by This on 2018/6/8.
 */

public class CardAdapter extends MyAdapter {
    public CardAdapter(ArrayList<CardBean> data) {
        super(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void isItemUp(ViewHolder holder, boolean flag) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.textView.getLayoutParams();
        layoutParams.bottomMargin = flag?50:0;
    }

    @Override
    public void isItemSelected(ViewHolder holder, boolean flag) {
        holder.textView.setBackgroundResource(flag? R.color.colorPrimary:R.color.white);
    }

}
