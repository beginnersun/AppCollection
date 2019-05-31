package com.example.myboy.appcollection.cardgame.utils;

import java.util.Calendar;

public class CalendarUtil {


    private static int a[][] = new int[6][7];
    private static Calendar calendar = Calendar.getInstance();

    private static void main(String[] args) {
        countDay();
        calendar.add(Calendar.MONTH, 1);
        countDay();
    }

    public static String getCurrentTime() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "年" + month + "月" + day + "日";
    }

    private static int getFirstDayOfWeek(int day) {
        switch (day) {
            case 1:
                return 0; //周日
            case 2:
                return 1; //周一  以此类推
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            default:
                return -1;
        }
    }

    private static int getDays(int month, int year) {
        if (month == 2) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            }
            if (year % 4 == 0) {
                return 29;
            } else {
                return 28;
            }
        } else if (month <= 7) {
            if (month % 2 == 0) {
                return 30;
            } else {
                return 31;
            }
        } else if (month >= 8 && month % 2 == 0) {
            if (month % 2 == 0) {
                return 31;
            } else {
                return 30;
            }
        }
        return -1;
    }

    public static int[][] currentTime() {
        return countDay();
    }

    public static int[][] nextMonth() {
        addTime(Calendar.MONTH, 1);
        return countDay();
    }

    public static int[][] nextYear() {
        addTime(Calendar.YEAR, 1);
        return countDay();
    }

    public static int[][] preMonth() {
        addTime(Calendar.MONTH, -1);
        return countDay();
    }

    public static int[][] preYear() {
        addTime(Calendar.YEAR, -1);
        return countDay();
    }


    /**
     * 返回当前月份的 日期
     * @return
     */
    public static int getDay(){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * @param field  Calendar.MONTH Calendar.YEAR  代表调整的时间类型
     * @param amount 调整多少
     */
    private static void addTime(int field, int amount) {
        calendar.add(field, amount);
    }

    private static int[][] countDay() {
        System.out.println();
        a = new int[6][7];
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.DATE, -day + 1);  //回到月初第一天  用于下一步得到第一天星期几
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println("日期" + year + " 年 " + month + " 月 " + day + " 日");

        int days = getDays(month, year);
        int firstWeek = getFirstDayOfWeek(dayOfWeek);
        System.out.println(days + "天" + "第一天是" + firstWeek);
        int count = 1;
        rl:
        for (int i = 0; i < 6; i++) {
            int j = 0;
            if (i == 0) {
                j = firstWeek;
            }
            for (j = j; j < 7; j++) {
                a[i][j] = count++;
                if (count > days) {
                    break rl;
                }
            }
        }
        return a;
//        for(int i = 0 ; i < 6 ;i++) {
//            for(int j =0 ;j < 7 ;j++) {
//                System.out.print(a[i][j] + " ");
//            }
//            System.out.println();
//        }
    }

}
