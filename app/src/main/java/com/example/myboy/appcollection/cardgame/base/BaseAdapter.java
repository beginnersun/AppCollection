package com.example.myboy.appcollection.cardgame.base;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private int resId;
    private List<BaseBean> datas;
    private Context context;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
