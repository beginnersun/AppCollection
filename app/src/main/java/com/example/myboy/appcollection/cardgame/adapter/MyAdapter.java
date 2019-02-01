package com.example.myboy.appcollection.cardgame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.bean.CardBean;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by This on 2018/6/5.
 */

public abstract class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<CardBean> cards;
    private SelectItemCard callBack;

    public ArrayList<CardBean> getCards(){
        return this.cards;
    }

    public SelectItemCard getCallBack() {
        return callBack;
    }

    public void setCallBack(SelectItemCard callBack) {
        this.callBack = callBack;
    }

    public MyAdapter(ArrayList<CardBean> data) {
        this.cards = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (holder != null) {
            holder.refresh(cards.get(position));
            isItemUp(holder,cards.get(position).isUp());
            isItemSelected(holder,cards.get(position).isSelected());
            holder.itemView.requestLayout();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cards.get(position).setUp(!cards.get(position).isUp());
                    isItemUp(holder,cards.get(position).isUp());
                    v.requestLayout();
                }
            });
        }
    }

    /**
     * 设置被up和没被up状态
     * @param holder 当前Holder
     * @param flag true 被 up
     */
    public abstract void isItemUp(ViewHolder holder,boolean flag);

    /**
     * 设置被选中和没被选中状态
     * @param holder 当前Holder
     * @param flag true 被 选中
     */
    public abstract void isItemSelected(ViewHolder holder,boolean flag);

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public interface SelectItemCard {
        void selectItem(int position);

        void removeItem(int position);
    }
}
