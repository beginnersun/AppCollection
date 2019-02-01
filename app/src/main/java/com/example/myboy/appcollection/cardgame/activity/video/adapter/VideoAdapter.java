package com.example.myboy.appcollection.cardgame.activity.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.bean.VideoBean;
import com.example.myboy.appcollection.cardgame.utils.ImageUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VideoAdapter extends ArrayAdapter<VideoBean> {
    int resourceId;
    public VideoAdapter(@NonNull Context context, int resource, @NonNull List<VideoBean> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VideoBean videoBean = getItem(position);
        ViewHolder viewHolder;
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.thumbnail = view.findViewById(R.id.item_video_thumbnail);
            viewHolder.title = view.findViewById(R.id.item_title);
            viewHolder.user = view.findViewById(R.id.item_issuer);
            viewHolder.time = view.findViewById(R.id.item_time);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        RequestOptions options = new RequestOptions();
        options.circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).load(ImageUtils.getNetVideoBitmap(videoBean.getUrl())).apply(options).into(viewHolder.thumbnail);
        viewHolder.time.setText(videoBean.getTime());
        viewHolder.user.setText(videoBean.getUserBean().getUserName());
        viewHolder.title.setText(videoBean.getTitle());
        return view;
    }

    class ViewHolder{
        ImageView thumbnail;
        TextView title;
        TextView user;
        TextView time;
    }
}
