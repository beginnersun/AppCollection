package com.example.myboy.appcollection.cardgame.api;

import com.example.myboy.appcollection.cardgame.bean.UserBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApI {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @GET("cardgame/login")
    Observable<UserBean> login(@Query("username")String username,@Query("password")String password);

}
