package com.example.myboy.appcollection.cardgame.utils;

import android.content.Context;
import android.util.Log;

import com.example.myboy.appcollection.cardgame.activity.SampleApplicationLike;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtils {

    private static final String TAG = "FileUtils";

    /**
     * 保存内容时逐行保存
     * @param fileName 文件名
     * @param content 内容
     */
    public static void saveContentToApplicationFile(String fileName,String content){
        content = content + "\r\n";
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = SampleApplicationLike.getInstance().openFileOutput(fileName,Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "saveContentToApplicationFile: "+fileName + " is Not found");
        }
        try {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件中的某行
     * @param fileName 文件名
     * @param index 行数
     * @return
     */
    public static String readContentForApplicationFile(String fileName,int index,int len){
        StringBuffer buffer = new StringBuffer();
        FileInputStream in = null;
        BufferedReader reader = null;
        try {
            in = SampleApplicationLike.getInstance().openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int count = 0;
        //跳过index之前的所有行
        while(count<index){
            try {
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;
        }
        count = 0;
        //读取接下来的len行
        while(count!=len){
            try {
                buffer.append(reader.readLine()).append("|");
            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;
        }
        buffer.deleteCharAt(buffer.length()-1);
        try {
            in.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
