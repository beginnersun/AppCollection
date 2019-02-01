package com.example.myboy.appcollection.cardgame.activity.setting.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myboy.appcollection.cardgame.activity.GameApplication;

public class GamePresenter implements Presenter {

    View view;
    SharedPreferences sharedPreferences;
    public GamePresenter(View view) {
        this.view = view;
        sharedPreferences = GameApplication.getInstance().getSharedPreferences("game_setting",Context.MODE_PRIVATE); sharedPreferences = GameApplication.getInstance().getSharedPreferences("game_setting",Context.MODE_PRIVATE);
    }

    @Override
    public void querySetting() {
        view.setSound(sharedPreferences.getBoolean("sound",true));
        view.setMusicBg(sharedPreferences.getBoolean("music",true));
        view.setVolume(sharedPreferences.getInt("volume",50));
    }

    @Override
    public void setVolume(int size) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("volume",size);
        editor.commit();
        editor.apply();
    }

    @Override
    public void setMusicBg(boolean open) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("music",open);
        editor.commit();
        editor.apply();
    }

    @Override
    public void setSound(boolean open) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("sound",open);
        editor.commit();
        editor.apply();
    }
}
