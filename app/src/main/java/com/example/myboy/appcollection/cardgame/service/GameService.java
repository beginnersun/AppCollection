package com.example.myboy.appcollection.cardgame.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.Nullable;


/**
 * Created by This on 2018/6/8.
 */

public class GameService extends Service {
    private BufferedReader reader;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

         /*
         * 这里返回状态有三个值，分别是:
         * 1、START_STICKY：当服务进程在运行时被杀死，系统将会把它置为started状态，但是不保存其传递的Intent对象，之后，系统会尝试重新创建服务;
         * 2、START_NOT_STICKY：当服务进程在运行时被杀死，并且没有新的Intent对象传递过来的话，系统将会把它置为started状态，
         *   但是系统不会重新创建服务，直到startService(Intent intent)方法再次被调用;
         * 3、START_REDELIVER_INTENT：当服务进程在运行时被杀死，它将会在隔一段时间后自动创建，并且最后一个传递的Intent对象将会再次传递过来。
         */
         String content;
        try {
            while((content=reader.readLine())!=null){
                intent.setAction("card.game.receive");
                intent.putExtra(GameReceiver.GAME_CONTENT,content);
                sendBroadcast(intent);
                if(content.startsWith("gameOver:")){
                    stopSelf();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
//        try {
//            reader = new BufferedReader(new InputStreamReader(RecyclerViewActivity.socket.getInputStream()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
