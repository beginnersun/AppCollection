package com.example.myboy.appcollection.cardgame.dao;

import com.example.myboy.appcollection.cardgame.api.UserApI;
import com.example.myboy.appcollection.cardgame.utils.HttpManager;

import io.reactivex.Observer;

public class UserDao {

    private UserApI userApI;
    private HttpManager httpManager;

    public UserDao() {
        httpManager = HttpManager.getInstance();
        this.userApI = httpManager.getmRetrofit().create(UserApI.class);
    }

    public void login(String username, String password, Observer observer){
        this.userApI.login(username,password).subscribe(observer);
    }

}
