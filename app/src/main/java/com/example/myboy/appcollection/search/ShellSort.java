package com.example.myboy.appcollection.search;

import android.util.Log;

/**
 * 希尔排序
 * @param <T>
 */
public class ShellSort<T> {

    public <T extends Comparable<? super T>> void sort(T[] x){
        Log.e("ShellSort","start");
        T pivot;
        for(int d = x.length/2;d>0;d--){
            for(int i=d;i<x.length;i++){
                pivot = x[i];
                int j;
                for(j=i-d;j>=0;j=j-d){
                    if(pivot.compareTo(x[j])<0){
                        x[j+d] = x[j];
                    }else{
                        x[j+d] = pivot;
                        break;
                    }
                }
                x[j+d] = pivot;
            }
        }
    }

}
