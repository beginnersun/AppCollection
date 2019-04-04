package com.example.myboy.appcollection.databinding.model;

import com.example.myboy.appcollection.databinding.bean.WeatherBean;

import java.util.Random;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherActivityModel extends ViewModel {

    private MutableLiveData<WeatherBean> liveData = new MutableLiveData<>();

    public MutableLiveData<WeatherBean> getLiveData() {
        return liveData;
    }

    public void refreshing() {
        WeatherBean weather = new WeatherBean();
        weather.setCity("厦门");
        weather.setBg_url("http://5b0988e595225.cdn.sohucs.com/images/20171030/2254593cabdd4d67abf29d2af02688a1.jpeg");
        Random random = new Random();
        int du = random.nextInt(20);
        weather.setTemperature(du + " °");
        weather.setSuggestion("不太冷不太热，你爱咋地咋地吧");
        liveData.postValue(weather);
    }
}
