package com.example.myboy.appcollection.cardgame.activity.start.fragment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myboy.appcollection.GlideApp;
import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.bean.ContactBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<ContactBean> datas;
    private Context context;
    private OnItemInviteClickListener clickListener;

    public ContactAdapter(List<ContactBean> beans, Context context) {
        this.datas = beans;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        holder.refresh(datas.get(position));
        holder.tvInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener!=null){
                    clickListener.onClick(position);
                }
            }
        });
    }

    public void setDatas(List<ContactBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder.contactImg!=null) {
            Glide.with(context).clear(holder.contactImg);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_contact_img)
        ImageView contactImg;
        @BindView(R.id.item_tv_name)
        TextView tvName;
        @BindView(R.id.item_tv_game)
        TextView tvGame;
        @BindView(R.id.item_tv_invite)
        TextView tvInvite;
        @BindView(R.id.item_tv_add)
        TextView tvAdd;

        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void refresh(ContactBean bean) {
            if(TextUtils.isEmpty(bean.getName())){
                tvName.setText("");
            }else{
                tvName.setText(bean.getName());
            }
            if(bean.isRegistered()){
                tvAdd.setVisibility(View.VISIBLE);
                tvInvite.setVisibility(View.GONE);
            }else{
                tvAdd.setVisibility(View.GONE);
                tvInvite.setVisibility(View.VISIBLE);
                tvGame.setText("游戏暂未开通");
            }
            GlideApp.with(itemView).asDrawable().load(bean.getImg()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).into(contactImg);
        }
    }

    public void setClickListener(OnItemInviteClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnItemInviteClickListener {
        void onClick(int position);
    }
}
