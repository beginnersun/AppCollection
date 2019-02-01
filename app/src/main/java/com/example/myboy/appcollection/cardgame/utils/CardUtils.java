package com.example.myboy.appcollection.cardgame.utils;

import com.example.myboy.appcollection.cardgame.bean.CardBean;
import com.example.myboy.appcollection.cardgame.bean.CardTreeOne;
import com.example.myboy.appcollection.cardgame.bean.CardTreeTwo;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * 判断出牌的类别是属于什么
 * Created by This on 2018/6/5.
 */

public class CardUtils {

    /**
     * 判断传入牌组是否为王炸  规则：两张牌分别为16和17
     * @param treeSet treeSet 输入牌组
     * @return
     */
    public static boolean isQueue(TreeSet<CardBean> treeSet){
        return treeSet.size()==2&&treeSet.first().getCategory()==16&&treeSet.last().getCategory()==17;
    }

    /**
     * 判断是否为炸  规则：四张数字相同为炸
     * @param treeSet
     * @return
     */
    public static boolean isZha(TreeSet<CardBean> treeSet){
        return false;
    }

    /**
     * 炸弹大小的比较
     * @param src 被比较
     * @param des 要比较
     * @return 如果src比des大 则返回true
     */
    public static boolean ZhaCompare(TreeSet<CardBean> src,TreeSet<CardBean> des){
        return src.first().getNumber()>des.first().getNumber();
    }

    /**
     * 将输入的牌组转换为三带一类 方便操作
     * @param treeSet 输入牌组 转换为三带一
     * @return
     * treeSet已经为排好序的列表 所以只需要判断1-3是否相等或者2-4是否相等就好
     */
    public static CardTreeOne converSan1(TreeSet<CardBean> treeSet){
        if(treeSet.size()!=4){
            return  null;
        }
        CardTreeOne cardTreeOne = new CardTreeOne();
        Iterator<CardBean> iterator = treeSet.iterator();
        CardBean first = iterator.next();
        CardBean two = iterator.next();
        CardBean three = iterator.next();
        CardBean four = iterator.next();
        if(first.getNumber()==two.getNumber()&&two.getNumber()==three.getNumber()){
            cardTreeOne.getThree().add(first);
            cardTreeOne.getThree().add(two);
            cardTreeOne.getThree().add(three);
            cardTreeOne.setOne(four);
            return cardTreeOne;
        }
        if(four.getNumber()==two.getNumber()&&two.getNumber()==three.getNumber()){
            cardTreeOne.getThree().add(two);
            cardTreeOne.getThree().add(three);
            cardTreeOne.getThree().add(four);
            cardTreeOne.setOne(first);
            return cardTreeOne;
        }
        return null;
    }

    /**
     * 转换为三代二的类
     * @param treeSet 输入牌组
     * @return 三带二 需要判断是否是一对  是否是三个 所以判断规则为12相同且345相同 或者123相同45相同
     */
    public static CardTreeTwo converSan2(TreeSet<CardBean> treeSet){
        if(treeSet.size()!=5){
            return null;
        }
        CardTreeTwo cardTreeTwo = new CardTreeTwo();
        Iterator<CardBean> iterator = treeSet.iterator();
        CardBean first = iterator.next();
        CardBean two = iterator.next();
        CardBean three = iterator.next();
        CardBean four = iterator.next();
        CardBean five = iterator.next();
        if(first.getNumber()==two.getNumber()&&two.getNumber()==three.getNumber()){
            cardTreeTwo.getThree().add(first);
            cardTreeTwo.getThree().add(two);
            cardTreeTwo.getThree().add(three);
            cardTreeTwo.getTwo().add(four);
            cardTreeTwo.getTwo().add(five);
            return cardTreeTwo;
        }
        if(four.getNumber()==five.getNumber()&&five.getNumber()==three.getNumber()){
            cardTreeTwo.getThree().add(three);
            cardTreeTwo.getThree().add(four);
            cardTreeTwo.getThree().add(five);
            cardTreeTwo.getTwo().add(first);
            cardTreeTwo.getTwo().add(two);
            return cardTreeTwo;
        }
        return null;
    }

    /**
     * 比较三带一的大小
     * @param src
     * @param des
     * @return
     */
    public static boolean compareSAN1(TreeSet<CardBean> src,TreeSet<CardBean> des){ //比较大小判断是否可以出
        CardTreeOne a = converSan1(src);
        CardTreeOne b = converSan1(des);
        if(a.getThree().get(0).compareTo(b.getThree().get(0))>0){
            return true;
        }
        return false;
    }

    /**
     * 比较三带二的大小
     * @param src
     * @param des
     * @return
     */
    public static boolean compareSAN2(TreeSet<CardBean> src,TreeSet<CardBean> des){
        CardTreeTwo a = converSan2(src);
        CardTreeTwo b = converSan2(des);
        if(a.getThree().get(0).compareTo(b.getThree().get(0))>0){
            return true;
        }
        return false;
    }

    public static boolean isDouble(TreeSet<CardBean> treeSet){
        return treeSet.first().getNumber() == treeSet.last().getNumber();
    }

    public static boolean compareDouble(TreeSet<CardBean> src,TreeSet<CardBean> des){
        if(!isDouble(src)||!isDouble(des)){
            return false;
        }
        return src.first().getNumber() > des.first().getNumber();
    }

    /*
     * 一定要从小到大排列
     */
    public static boolean isLianxu(TreeSet<CardBean> treeSet){
        if(treeSet.size()<5){
            return false;
        }
        Iterator<CardBean> iterator = treeSet.iterator();
        int number =iterator.next().getNumber();
        while(iterator.hasNext()){
            int key = iterator.next().getNumber();
            if(key == 16 || key == 17 || key == 15){ //不能有王和2
                return false;
            }
            if(number - key != -1){
                return false;
            }
            number = key;
        }
        return true;
    }

    public static boolean compareLianxu(TreeSet<CardBean> src,TreeSet<CardBean> des){
        if(isLianxu(src)&&isLianxu(des)){
            return src.first().getNumber()>des.first().getNumber();
        }
        return false;
    }
}
