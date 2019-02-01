package com.example.myboy.appcollection.cardgame.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动控制器   主要是为了能够随时一键退出程序
 */
public class ActivityCollector {

    public static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 关闭所有活动  也就是关闭程序
     */
    public static void finishAll(){
        for (Activity activity:activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
