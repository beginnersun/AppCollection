package com.example.myboy.appcollection.databinding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myboy.appcollection.databinding.ItemWeatherBinding;
import com.example.myboy.appcollection.databinding.bean.CityBean;
import com.example.myboy.appcollection.databinding.bean.WeatherBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<CityBean> beans;
    private OnItemClickListener listener;

    public WeatherAdapter(Context context,List<CityBean> beans) {
        this.inflater = LayoutInflater.from(context);
        this.beans = beans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(inflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindToData(beans.get(position));
        if (listener!=null) {
            holder.bindListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemWeatherBinding binding;

        public static ViewHolder create(LayoutInflater inflater, ViewGroup viewGroup){
            ItemWeatherBinding binding = ItemWeatherBinding.inflate(inflater,viewGroup,false);
            return new ViewHolder(binding);
        }

        private ViewHolder(@NonNull ItemWeatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindToData(CityBean bean){
            binding.setItem(bean);
            binding.executePendingBindings();//这个必须有当你的数据还无效的时候，数据绑定是等到下一个动画
            // 帧之前才设置布局。这就让我们不可以一次性批量绑定完所有的数据内容，因为 RecyclerView 的机制并
            // 不是这样。当要绑定一个数据的时候，RecyclerView 会调用 BindView，让你去准备测量这个布局。这就
            // 是为什么我们叫这个方法为 executePendingBindings，它使数据绑定刷新所有挂起的更改。否则，它将
            // 视为另一个布局失效了。
        }

        public void bindListener(OnItemClickListener onItemClickListener){
            binding.setListener(onItemClickListener);
        }

    }

    public interface OnItemClickListener{
        void onItemClick(CityBean bean);
    }
}
