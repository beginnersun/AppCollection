package com.example.myboy.appcollection.search;

import android.util.Log;

import com.example.myboy.appcollection.search.utils.ArraysToString;

public class QuickSort<T> {

    private <T extends Comparable<? super T> > int sort(T[] x,int start,int end){
        T pivot = x[start];
        int left = start;
        int right = end;
        String preString = ArraysToString.arraysToString(x);
        while(left<right){
            while (pivot.compareTo(x[right])<0&&left<right){
                right--;
            }
            if(left < right) {
                x[left++] = x[right];
            }
            while(pivot.compareTo(x[left])>0&&left<right){
                left++;
            }
            if(left < right) {
                x[right] = x[left];
                right -- ;
            }
        }
        x[left] = pivot;
        String endString = ArraysToString.arraysToString(x);
        return left;
    }

    public <T extends Comparable<? super T> > void quick(T[] x,int left,int right){
        if(left<right&&left>=0&&right<x.length){
            int mid = sort(x,left,right);
            quick(x, left, mid - 1);
            quick(x, mid+1, right);
        }
    }


}
