package com.example.myboy.appcollection.cardgame.activity.video.presenter;

import android.app.Activity;

import com.example.myboy.appcollection.cardgame.bean.VideoBean;
import com.example.myboy.appcollection.cardgame.dao.VideoDao;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class VideoPresenter implements Presenter {

    View view;
    ArrayList<VideoBean> videoBeans;
    VideoDao videoDao;
    boolean open = false;
    public VideoPresenter(View view) {
        this.view = view;
        videoDao = new VideoDao();
        videoBeans = new ArrayList<VideoBean>();
        view.closeShare();
    }

    @Override
    public void queryVideo() {
        videoDao.queryAllVideo(new Observer<ArrayList<VideoBean>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(ArrayList<VideoBean> videoBeans) {
                Activity activity = (Activity) view;
                if(activity!=null && !activity.isFinishing()) {
                    view.setData(videoBeans);
                }
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void openShare() {
        this.open = !open;
        if(!open){
            view.openShare();
        }else{
            view.closeShare();
        }
    }
}
