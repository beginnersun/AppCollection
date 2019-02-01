package com.example.myboy.appcollection.cardgame.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import com.example.myboy.appcollection.cardgame.GameActivity;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import androidx.annotation.Nullable;

/**
 * 负责监听是否存活
 * Created by This on 2018/6/7.
 */

public class KeepAliveService extends IntentService{

    public KeepAliveService() {
        super("intentService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            GameActivity.socket = new Socket("",50000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(GameActivity.socket.getOutputStream(),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            printWriter.println("keep:is alive");
            printWriter.flush();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
