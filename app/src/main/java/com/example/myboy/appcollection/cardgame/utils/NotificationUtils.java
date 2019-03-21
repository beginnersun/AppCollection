package com.example.myboy.appcollection.cardgame.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.myboy.appcollection.SampleApplication;
import com.example.myboy.appcollection.cardgame.activity.SampleApplicationLike;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationUtils {

    /**
     * 创建通知，并弹出
     * @param channelId 渠道id 必须有
     * @param title     通知标题
     * @param content   内容
     * @param largIcon  图标
     */
    public static void NotificationGameFail(@NonNull String channelId, String title, String content, Bitmap largIcon) {
        NotificationManager manager = (NotificationManager) SampleApplicationLike.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(SampleApplicationLike.getInstance(), channelId)
                .setContentTitle(title).setContentText(content).setAutoCancel(true).setLargeIcon(largIcon).
                        setWhen(System.currentTimeMillis()).build();
        manager.notify(1, notification);
    }

    /**
     * 创建渠道组
     *
     * @param groupId   唯一标识——id
     * @param groupName 渠道名称
     */
    public static void createNotificationGroup(@NonNull String groupId, @NonNull String groupName) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannelGroup group = new NotificationChannelGroup(groupId, groupName);
            NotificationManager manager = (NotificationManager) SampleApplicationLike.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannelGroup(group);
        }

    }

    /**
     * 创建渠道
     *
     * @param channelId   渠道ID
     * @param channelName 渠道名
     * @param importance  NotificationManager.IMPORTANCE_HIGH 高  、NotificationManager.IMPORTANCE_MAX 最高等等
     * @param groupId     属于哪个渠道组（不传默认不分组）
     */
    public static void createNotificationChannel(@NonNull String channelId,@NonNull String channelName, int importance, String groupId) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance==0?NotificationManager.IMPORTANCE_DEFAULT:importance);
            NotificationManager manager = (NotificationManager) SampleApplicationLike.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
            if (!TextUtils.isEmpty(groupId)) {
                channel.setGroup(groupId);
            }
            manager.createNotificationChannel(channel);
        }
    }

    /**
     * 判断渠道是否被关闭
     * @param channelId
     * @return true 关闭 false（版本低于26或者默认为没关）没关
     */
    public static boolean isClose(@NonNull String channelId){
        if (Build.VERSION.SDK_INT >= 26){
            @SuppressLint("ServiceCast") NotificationChannel channel = ((NotificationManager) SampleApplicationLike.getInstance().getSystemService(Context.NOTIFICATION_SERVICE)).getNotificationChannel(channelId);
            return channel.getImportance() == NotificationManager.IMPORTANCE_NONE;
        }
        return false;
    }

}
