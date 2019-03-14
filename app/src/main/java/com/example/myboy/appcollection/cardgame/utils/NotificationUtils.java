package com.example.myboy.appcollection.cardgame.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;

import com.example.myboy.appcollection.cardgame.activity.SampleApplicationLike;

import androidx.core.app.NotificationCompat;

public class NotificationUtils {

    public static void NotificationGameFail(String title,String content,Bitmap largIcon){
        NotificationManager manager = (NotificationManager) SampleApplicationLike.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel =manager.getNotificationChannel("card_game");
            if(channel.getImportance() == NotificationManager.IMPORTANCE_NONE){
                Toast.makeText(SampleApplicationLike.getInstance(),"请打开通知栏权限",Toast.LENGTH_LONG).show();
            }else {
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);
            }
        }
        Notification notification = new NotificationCompat.Builder(SampleApplicationLike.getInstance(), "card_game")
                .setContentTitle(title).setContentText(content).setAutoCancel(true).setLargeIcon(largIcon).
                        setWhen(System.currentTimeMillis()).build();
        manager.notify(1,notification);
    }

}
