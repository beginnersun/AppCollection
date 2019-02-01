package com.example.myboy.appcollection.cardgame.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String getNumber(String message){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(message);
        matcher.find();
        return matcher.group();
    }

}
