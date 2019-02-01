package com.example.myboy.appcollection.cardgame.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {

    public static String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String hour = "";
        if(calendar.get(Calendar.AM_PM) == 0){
            hour = String.valueOf(calendar.get(Calendar.HOUR));
        }else{
            hour = String.valueOf(calendar.get(Calendar.HOUR)+12);
        }
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        return new StringBuffer(year).append("-").append(month).append("-").append(day).append(" ")
                .append(hour).append(":").append(minute).append(":").append(second).toString();
    }

}
