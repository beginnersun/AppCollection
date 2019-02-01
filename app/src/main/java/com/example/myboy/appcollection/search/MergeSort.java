package com.example.myboy.appcollection.search;

import java.util.ArrayList;

public class MergeSort<T>{

    public <T extends Comparable<? super T>> void sort(T[] x,int len){
        T[] y = (T[]) new Object[x.length]; //创建用来保存排序后的数组
        for(int i=1 ;i<len; i=i*2){
            for(int j = 0 ;j<len ;j =j + i*2){ //每次从0开始 每次的长度为i  j = j + i*2 是因为每次长度有2个i(x和y)
                merge(x,j,j+i,i,y);
            }
            System.arraycopy(y,0,x,0,len);
        }
    }

    /**
     * 归并排序
     * @param x 要排序的数组
     * @param s_x x的开始下标
     * @param s_y y的开始下标
     * @param len 此次排序长度
     * @param y 用来临时保存的数组
     * @param <T>
     */
    private  <T extends Comparable<? super T>> void merge(T[] x,int s_x,int s_y,int len,T[] y){
        int index = s_x;
        int i,j;
        for(i = s_x,j=s_y;i<s_x+len&&j<s_y+len&&i<x.length&&j<y.length;){
            if(x[i].compareTo(x[j])<0){
                y[index++] = x[i++];
            }else{
                y[index++] = x[j++];
            }
        }
        while (i<x.length){
            y[index++] = x[i++];
        }
        while(j<x.length){
            y[index++] = x[j++];
        }
    }

}
