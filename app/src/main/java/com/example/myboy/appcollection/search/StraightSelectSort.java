package com.example.myboy.appcollection.search;

/**
 * 直接选择排序//简单选择排序
 */
public class StraightSelectSort<T> {

    /**
     * 假定第一个或者某一个为最小 然后在所有元素中寻找最小的 找到后交换
     * @param x
     * @param <T>
     */
    public <T extends Comparable<? super T>> void sort(T[] x){
        T min;
        int k = 0;
        boolean hasMin;
        for(int i = 0;i<x.length ;i++){
            min = x[i];
            k = i;  //初始化k为i  确保如果没有最小的也不会发生数据交换
            for(int j = i+1;j<x.length ;j++){
                if(x[j].compareTo(min)<0){
                    min = x[j];
                    k = j;
                }
            }
            x[k] = x[i];
            x[i] = min;
        }
    }
}
