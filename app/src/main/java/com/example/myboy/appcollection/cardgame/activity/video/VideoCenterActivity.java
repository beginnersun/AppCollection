package com.example.myboy.appcollection.cardgame.activity.video;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.activity.video.adapter.VideoAdapter;
import com.example.myboy.appcollection.cardgame.activity.video.presenter.Presenter;
import com.example.myboy.appcollection.cardgame.activity.video.presenter.VideoPresenter;
import com.example.myboy.appcollection.cardgame.base.BaseActivity;
import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import com.example.myboy.appcollection.cardgame.bean.VideoBean;
import com.example.myboy.appcollection.videoplayer.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;


public class VideoCenterActivity extends BaseActivity implements Presenter.View {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.share_to_qq)
    TextView shareToQW;
    @BindView(R.id.share_to_wx)
    TextView shareToWX;
    @BindView(R.id.share_to_wb)
    TextView shareToWB;
    @BindView(R.id.switch_share_or_btn)
    TextView switchShareOrBtn;
    @BindView(R.id.share)
    LinearLayout share;
    @BindView(R.id.follow)
    TextView follow;
    @BindView(R.id.add_friend)
    TextView addFriend;
    @BindView(R.id.go_he)
    TextView goHe;
    @BindView(R.id.share_video)
    TextView shareVideo;
    @BindView(R.id.btn)
    LinearLayout function;

    VideoPresenter presenter;
    VideoAdapter adapter;
    List<VideoBean> videos = new ArrayList<VideoBean>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new VideoPresenter(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VideoBean videoBean = videos.get(i);
                Intent intent = new Intent(VideoCenterActivity.this, VideoPlayerActivity.class);
                intent.putExtra(VideoPlayerActivity.VIDEO_NAME, videoBean.getTitle());
                intent.putExtra(VideoPlayerActivity.VIDEO_URL, videoBean.getUrl());
                startActivity(intent);
            }
        });
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }
        };
        asyncTask.execute("");
    }

    @Override
    protected void requestPermissionFailed(String permission) {

    }

    @Override
    protected void requestPermissionSuccess(String permission) {

    }

    @Override
    protected void setPresenter(BasePresenter basePresenter) {
        this.presenter = (VideoPresenter) basePresenter;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_video_center;
    }

    @Override
    protected void queryInfo() {
        presenter.queryVideo();
    }

    @Override
    public void setData(List<VideoBean> videos) {
        this.videos = videos;
        adapter = new VideoAdapter(this, R.layout.item_video, this.videos);
        listView.setAdapter(adapter);
    }

    @Override
    public void openShare() {
        share.setVisibility(View.VISIBLE);
        function.setVisibility(View.GONE);
    }

    @Override
    public void closeShare() {
        share.setVisibility(View.GONE);
        function.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.share_to_qq, R.id.share_to_wx, R.id.share_to_wb, R.id.switch_share_or_btn, R.id.follow, R.id.add_friend, R.id.go_he, R.id.share_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share_to_qq:
                break;
            case R.id.share_to_wx:
                break;
            case R.id.share_to_wb:
                break;
            case R.id.switch_share_or_btn:
                presenter.openShare();
                break;
            case R.id.follow:
                break;
            case R.id.add_friend:
                break;
            case R.id.go_he:
                break;
            case R.id.share_video:
                presenter.openShare();
                break;
        }
    }
}
