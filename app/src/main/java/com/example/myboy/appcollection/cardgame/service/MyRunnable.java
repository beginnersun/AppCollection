package com.example.myboy.appcollection.cardgame.service;

/**
 * Created by This on 2018/6/7.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MyRunnable implements Runnable{
    private Socket socket;
    private BufferedReader reader;

    private GetCardCallBack getCardCallBack;

    public MyRunnable(Socket socket,GetCardCallBack getCardCallBack) {
        this.socket = socket;
        this.getCardCallBack = getCardCallBack;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String content;
        try {
            while((content = reader.readLine())!=null){
                if(this.getCardCallBack!=null){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public interface GetCardCallBack{
        //准备出牌动画
        void preAnimation();

        //出牌
        void sendCards();

        //根据牌显示不同动画
        void showAnimation();
    }
}