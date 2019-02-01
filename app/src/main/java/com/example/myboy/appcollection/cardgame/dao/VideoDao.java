package com.example.myboy.appcollection.cardgame.dao;

import com.example.myboy.appcollection.cardgame.api.VideoApI;
import com.example.myboy.appcollection.cardgame.bean.UserBean;
import com.example.myboy.appcollection.cardgame.bean.VideoBean;
import com.example.myboy.appcollection.cardgame.utils.HttpManager;
import com.example.myboy.appcollection.cardgame.utils.TimeUtils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;


public class VideoDao {

    VideoApI videoApI;
    HttpManager manager;
    public VideoDao() {
        manager = HttpManager.getInstance();
        videoApI = manager.getmRetrofit().create(VideoApI.class);
    }

    public void queryAllVideo(Observer observer){
//        videoApI.queryVideo().subscribe(observer);
        String title = "可塑性记忆";
        Observable<ArrayList<VideoBean>> observable = Observable.just("http://106.12.200.228:8080/video/memory/memory").flatMap(new Function<String, ObservableSource<ArrayList<String>>>() {
            @Override
            public ObservableSource<ArrayList<String>> apply(String s) throws Exception {
                ArrayList<String> list = new ArrayList<>();
                for(int i=1;i<=13;i++){
                    list.add(s + ((i>=10)?i+"":("0"+i)) + ".mp4");
                }
                return Observable.just(list);
            }
        }).flatMap(new Function<ArrayList<String>, ObservableSource<ArrayList<VideoBean>>>() {
            @Override
            public ObservableSource<ArrayList<VideoBean>> apply(ArrayList<String> strings) throws Exception {
                ArrayList<VideoBean> videoBeans = new ArrayList<>();
                for(String string:strings){
                    VideoBean bean = new VideoBean();
                    bean.setTime(TimeUtils.getCurrentTime());
                    bean.setUrl(string);
                    bean.setTitle(title+string.substring(string.lastIndexOf(".")+1));
                    bean.setUserBean(new UserBean());
                    videoBeans.add(bean);
                }
                return Observable.just(videoBeans);
            }
        });
        observable.subscribe(observer);
    }

}
