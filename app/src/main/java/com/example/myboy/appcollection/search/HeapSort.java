package com.example.myboy.appcollection.search;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆排序
 * 创建为小顶堆
 */
public class HeapSort<T> {

    private <T extends Comparable<? super T>> void createHeap(T[] x,int len) {
        Log.e("createHeap","创建");
        for (int i = len / 2; i > 0; i--) {
            adjustHeap(x,i,len);
        }
    }

    private  <T extends Comparable<? super T>> void adjustHeap(T[] x, int i,int len) {
        Log.e("HeadJust","调整数组");
        x[0] = x[i];
        for (int j = 2 * i; j <= len; j = j * 2) {
            if (j < len && x[j].compareTo(x[j+1]) > 0) { //如果左树比右树大 则选择和右树比
                j++;
            }
            if (x[0].compareTo(x[j]) > 0) {
                x[i] = x[j]; //比较小的值复制给顶点
                i = j;//保存交换后的位置(此时x[j]的值还不是pivot 但是我们最后将pivot赋值
            }
        }
        x[i] = x[0];
    }

    public <T extends Comparable<? super T>> List<T> sort(T[] x, int len){
        ArrayList<T> list = new ArrayList<>();
        createHeap(x,len);
        T pivot;
        for(int i=len ;i>1;i--){ //调整n-1就行 最后剩下一个就够了
            /**
             * 下三行  是为了将小顶堆的顶部弄出来 最后加到list上
             */
            pivot = x[1];
            x[1] = x[i];
            x[i] = pivot;

            list.add(pivot);
            adjustHeap(x,1,i-1);
        }
        list.add(x[0]);
        return list;
    }
}