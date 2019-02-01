package com.example.myboy.appcollection.search;

import android.util.Log;

/**
 * 直接插入排序
 * @param <T>
 */
public class InsertionSort<T> {

    /**
     * 思想 假定前i个数组已经有序 然后每插入一个数据就和数组的第i个比较 如果比他大说明直接插入最尾端 否则找到合适位置
     * @param x
     * @param <T>
     */
    public <T extends Comparable<? super T>> void sort(T x[]){
        Log.e("InsertionSort","直接插入排序");
        T pivot;
        for(int i = 1;i<x.length;i++){
            pivot = x[i];
            for(int j=i-1;j>=0;j--){
                if(pivot.compareTo(x[j])>=0){
                    x[j+1] = pivot;
                    break;
                }
                if(pivot.compareTo(x[j])<0){
                    x[j+1] = x[j];
                }
            }
        }
    }

}
