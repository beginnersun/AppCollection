package com.example.myboy.appcollection.videoplayer;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.myboy.appcollection.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static com.example.myboy.appcollection.cardgame.utils.ImageUtils.getNetVideoBitmap;

public class VideoPlayerActivity extends AppCompatActivity{

    @BindView(R.id.video_player)
    JzvdStd videoPlayer;

    public static String VIDEO_URL = "video_url";
    public static String VIDEO_NAME = "video_name";
    private String url;
    private String name;
    Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent!=null){
            url = intent.getStringExtra(VIDEO_URL);
            name = intent.getStringExtra(VIDEO_NAME);
            videoPlayer.setUp(url,name,JzvdStd.SCREEN_WINDOW_FULLSCREEN); //直接全屏播放
            Glide.with(this).load(getNetVideoBitmap(url)).into(videoPlayer.thumbImageView);
        }
    }


    @Override
    public void onBackPressed() {
        if(videoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
