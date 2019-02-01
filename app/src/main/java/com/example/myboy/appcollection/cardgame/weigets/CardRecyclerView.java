package com.example.myboy.appcollection.cardgame.weigets;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.myboy.appcollection.cardgame.adapter.CardAdapter;
import com.example.myboy.appcollection.cardgame.adapter.MyAdapter;
import com.example.myboy.appcollection.cardgame.bean.CardBean;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by This on 2018/6/8.
 */

public class CardRecyclerView extends RecyclerView {

    float down_x;  //点击位置
    float down_y;
    boolean first = false;  //第一次要记录下第一次的位置
    int first_position;   //

    private int old_Position = -1;
    private boolean hasAddHandle = false;
    boolean action_up = false;  //是否抬起手

    MyAdapter adapter;   //适配器
    ArrayList<CardBean> cardBeans = new ArrayList<>();  //牌
    TreeSet<CardBean> treeSet = new TreeSet<>();  //拖拽选中的牌

    TreeSet<CardBean> cardsSet = new TreeSet<>();

    MyLinearLayoutManager linearLayoutManager;

    public CardRecyclerView(Context context) {
        super(context);
    }

    public CardRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        action_up = false;
                        down_x = event.getX();
                        down_y = event.getY();
                    }
                    case MotionEvent.ACTION_MOVE:{
                        action_up = false;
                        down_x = event.getX();
                        down_y = event.getY();
                        int position = linearLayoutManager.pointToPosition((int)event.getX(),(int)event.getY());
                        if(!first&&position!=-1){
                            first_position = position+1;
                            first = true;
                        }
                        if(position!=-1) {
                            addSelectPosition(position + 1);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        addToCardSend();
                        action_up = true;
                        first = false;
                        hasAddHandle = false;
                        first_position = -1;
                        old_Position = -1;
                        cardsUp();
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:{
                        break;
                    }
                }
                return false;
            }
        });
    }

    private void setMyAdapter(MyAdapter adapter){
        linearLayoutManager = (MyLinearLayoutManager) getLayoutManager();
        this.adapter = adapter;
        this.setAdapter(adapter);
    }

    private void addSelectPosition(final int position){
        if(position != old_Position&&position>=0&&position<cardBeans.size()&&first_position>=0&&first_position<cardBeans.size()){
            if(!hasAddHandle) {
                old_Position = position;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!action_up) {
                            if (first_position > position) {
                                for (int i = 0; i < cardBeans.size(); i++) {
                                    if(i>=position&&i<=first_position){
                                        cardBeans.get(i).setSelected(true);
                                    }else{
                                        cardBeans.get(i).setSelected(false);
                                    }
                                }
                            } else {
                                for (int i = 0; i < cardBeans.size(); i++) {
                                    if(i>=first_position&&i<=position){
                                        cardBeans.get(i).setSelected(true);
                                    }else{
                                        cardBeans.get(i).setSelected(false);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                            hasAddHandle = false;
                        }
                    }
                },10);
                hasAddHandle = true;
            }
        }
    }

    public void setListData(ArrayList<CardBean> cards){
        this.cardBeans = cards;
        final MyAdapter adapter = new CardAdapter(this.cardBeans);
        adapter.setCallBack(new MyAdapter.SelectItemCard() {
            @Override
            public void selectItem(int position) {
                CardBean cardBean = cardBeans.get(position);
                cardsSet.add(cardBean);
                cardBean.setUp(true);
                adapter.notifyItemChanged(position);
            }

            @Override
            public void removeItem(int position) {
                CardBean cardBean = cardBeans.get(position);
                cardsSet.remove(cardBean);
                cardBean.setUp(false);
                adapter.notifyItemChanged(position);
            }
        });
        this.setMyAdapter(adapter);
    }

    public TreeSet<CardBean> getCardsSet() {
        return cardsSet;
    }

    /**
     * 托转选中的上移或下跌
     */
    private void cardsUp(){
        for(CardBean bean:cardBeans){
            bean.setSelected(false);
        }
        if(treeSet.size()>0){
            Iterator<CardBean> iterator = treeSet.iterator();
            while(iterator.hasNext()){
                CardBean bean = iterator.next();
                bean.setUp(!bean.isUp());
            }
        }
        treeSet.clear();
        adapter.notifyDataSetChanged();
    }

    //加入到准备发牌
    private void addToCardSend(){
        if(old_Position>=0&&old_Position<cardBeans.size()&&first_position>=0&&first_position<cardBeans.size()) {
            int position = old_Position;
            if (first_position > position) {
                for (int i = 0; i < cardBeans.size(); i++) {
                    if(i>=position&&i<=first_position){
                        treeSet.add(cardBeans.get(i));
                    }
                }
            } else {
                for (int i = 0; i < cardBeans.size(); i++) {
                    if(i>=first_position&&i<=position){
                        treeSet.add(cardBeans.get(i));
                    }
                }
            }
        }
    }
}
