package com.example.myboy.appcollection.cardgame.adapter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.bean.CardBean;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by This on 2018/6/5.
 */

public class ViewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    View itemView;
    public ViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        textView = itemView.findViewById(R.id.card_text);
    }

    public void refresh(CardBean cardBean){
        Log.e("VIEWHOLDER","写入数据"+cardBean.getNum());
        this.textView.setText(cardBean.getNum());
    }

}