package com.example.myboy.appcollection.demo.activity.login;

import android.util.Log;
import android.view.View;

import com.example.myboy.appcollection.demo.base.BaseViewModel;
import com.example.myboy.appcollection.demo.bean.User;

import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<User> liveData = new MutableLiveData<>();

    public MutableLiveData<User> getLiveData() {
        return liveData;
    }

    public void login(){
        Log.e("userInfo",liveData.getValue().toString());
    }
}
