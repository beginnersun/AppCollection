package com.example.myboy.appcollection.search.utils;

import java.util.List;

public class ArraysToString {

    public static String arraysToString(Object info[]){
        StringBuffer buffer = new StringBuffer();
        for(Object o:info){
            buffer.append(o.toString()).append(",");
        }
        buffer.deleteCharAt(buffer.length()-1);
        return buffer.toString();
    }

    public static String ListToString(List list){
        StringBuffer buffer = new StringBuffer();
        for(Object o:list){
            buffer.append(o.toString()).append(",");
        }
        buffer.deleteCharAt(buffer.length()-1);
        return buffer.toString();
    }


}
