package com.example.myboy.appcollection.cardgame.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.text.TextUtils;

/**
 * Created by This on 2018/6/7.
 */

public class GameReceiver extends BroadcastReceiver {

    public static String GAME_CONTENT = "game_content";

    private ReceiverCallBack receiverCallBack;

    public GameReceiver() {
    }

    public GameReceiver(ReceiverCallBack receiverCallBack) {
        this.receiverCallBack = receiverCallBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String content = intent.getStringExtra(GAME_CONTENT);
        if (TextUtils.isEmpty(content)){
            return;
        }
        if(content.startsWith("addRoom:")){
            content = content.replace("addRoom:","");
            if(!content.contains("加入失败")) {
                this.receiverCallBack.addRoomSuccess(content);
            }else{
                this.receiverCallBack.addRoomFailed(content);
            }
        }else if(content.startsWith("preGame:")){
            content = content.replace("preGame:","");
            if(!content.contains("准备失败")){
                String judge[] = content.split(",");
                if(judge!=null&&judge.length==2) {
                    this.receiverCallBack.preGame(Integer.parseInt(judge[0]), Boolean.parseBoolean(judge[1]));
                }
            }
        }else if (content.startsWith("preCards:")){  //先给牌 然后确定地主
            content = content.replace("preCards:","");
            this.receiverCallBack.preCards(content);

        }else if (content.startsWith("preDizhu:")){//初始化地主 和地主牌
            content = content.replace("preDizhu:","");
            String info[] = content.split(",");
            this.receiverCallBack.startGame(Integer.parseInt(info[0]),info[1],info[2]);

        }else if(content.startsWith("sureBig:")){  //开始确定地主
            content = content.replace("sureBig:","");
            this.receiverCallBack.sureBig(Integer.parseInt(content));

        }else if(content.startsWith("Big:")){ //确定地主
            content = content.replace("Big:","");
            this.receiverCallBack.startGame(Integer.parseInt(content));
        }else if(content.startsWith("lose:")){
            content = content.replace("lost:","");
            this.receiverCallBack.peopleLose(Integer.parseInt(content));
        }else if(content.startsWith("gameOver:")){
            content = content.replace("gameOver:","");
            this.receiverCallBack.gameOver(content);
        }
    }

    public interface ReceiverCreateRoomCallBack {
        void createRoomSuccess();

        void createRoomFailed();
    }

    public interface ReceiverCallBack{
        //加入成功
        void addRoomSuccess(String content);  //uid:id,index:id; 三个人的
        //加入失败
        void addRoomFailed(String content);
        //准备游戏
        void preGame(int num, boolean pre);
        //开始确定地主
        void startGame(int num, String bigCards, String dCards);
        //开始游戏
        void startGame(int bnum);
        //准备
        void preCards(String cards);
        //确定big
        void sureBig(int big);
        //有人离开
        void peopleLose(int num);
        //重新进入
        void peopleLogin();
        //结束
        void gameOver(String win);
    }
}
