package com.example.myboy.appcollection.cardgame;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.adapter.MyAdapter;
import com.example.myboy.appcollection.cardgame.bean.CardBean;
import com.example.myboy.appcollection.cardgame.service.GameReceiver;
import com.example.myboy.appcollection.cardgame.utils.CardUtils;
import com.example.myboy.appcollection.cardgame.weigets.CardItemDecoration;
import com.example.myboy.appcollection.cardgame.weigets.CardRecyclerView;
import com.example.myboy.appcollection.cardgame.weigets.MyLinearLayoutManager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GameActivity extends AppCompatActivity {

    public static Socket socket;

    CardRecyclerView recyclerView;
    ArrayList<String> Data;
    MyAdapter adapter;
    ArrayList<CardBean> cardBeans = new ArrayList<>();
    TreeSet<CardBean> treeSet = new TreeSet<>();  //保存出来的牌
    TreeSet<CardBean> preTreeSet = new TreeSet<>(); //上一个人的牌
    GameReceiver receiver;
    TextView tl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        if (intent!=null){
            intent.getData().getPath(); //获取隐式启动的Uri
        }

        initService(); // TODO: 2018/6/7 点击开始就应该启动服务然后 收到创建成功的回调之后开启房间

        recyclerView = findViewById(R.id.recyclerview);
        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        Data = new ArrayList<>();
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<CardBean> arrayList = new ArrayList<>();
        arrayList.add(new CardBean(0,3,false,false,arrayList));
        arrayList.add(new CardBean(0,3,false,false,arrayList));
        arrayList.add(new CardBean(0,4,false,false,arrayList));
        arrayList.add(new CardBean(0,5,false,false,arrayList));
        arrayList.add(new CardBean(0,6,false,false,arrayList));
        arrayList.add(new CardBean(0,7,false,false,arrayList));
        arrayList.add(new CardBean(0,8,false,false,arrayList));
        arrayList.add(new CardBean(0,9,false,false,arrayList));
        arrayList.add(new CardBean(0,10,false,false,arrayList));
        arrayList.add(new CardBean(0,11,false,false,arrayList));
        arrayList.add(new CardBean(0,12,false,false,arrayList));
        arrayList.add(new CardBean(0,13,false,false,arrayList));
        recyclerView.addItemDecoration(new CardItemDecoration());  //让每一个item之间间距减少200或者某个值（做出紧挨着的感觉  会根据剩余牌数进行变化）
//        adapter = new CardAdapter(arrayList);
        recyclerView.setListData(arrayList);
        cardBeans = arrayList;
        recyclerView.setClickable(true);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CardView","OnCLick");
            }
        });

    }

    public void getCards(){
        recyclerView.getCardsSet();
    }

    private void initService() {  //启动服务并注册广播
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(getPackageName(), "yyy.card.game");
        intent.setComponent(componentName);
        startService(intent);

        receiver = new GameReceiver(new GameReceiver.ReceiverCallBack() {

            @Override
            public void addRoomSuccess(String content) {
                // TODO: 2018/6/8  根据用户id 查询用户基本信息然后加载人物
            }

            @Override
            public void addRoomFailed(String content) {
                // TODO: 2018/6/8 加入失败 弹出提示框  然后跳出房间
            }

            @Override
            public void preGame(int num, boolean pre) {
                // TODO: 2018/6/8  准备游戏  根据pre显示不同提示图片（准备 或取消准备）
            }

            /**
             *
             * @param num  地主位置  0 1 2 总共三种
             * @param bigCards
             * @param dCards 用;隔开
             */
            @Override
            public void startGame(int num, String bigCards, String dCards) {
                // TODO: 2018/6/8  初步确定地主 index //位置  牌 和地主牌
            }

            @Override
            public void startGame(int bnum) {
                // TODO: 2018/6/8  开始游戏且地主确定
            }


            @Override
            public void preCards(String cards) {
                // TODO: 2018/6/8   准备好牌
            }            @Override
            public void sureBig(int big) {
                // TODO: 2018/6/8  跳转地主显示按钮
            }

            @Override
            public void peopleLose(int num) {

            }

            @Override
            public void peopleLogin() {

            }

            @Override
            public void gameOver(String win) {

            }

        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("card.game.receive");
        registerReceiver(receiver, filter);
    }

    /*
     * 出牌
     */
    private void sendCards(){
        if(!checkSure()){
            return ;
        }
        for(CardBean cardBean:treeSet){
            cardBean.removeInParent();
        }
        adapter.notifyDataSetChanged();
    }

    public void addCenter(){

    }

    private boolean checkSure(){
        if(treeSet.size()!=preTreeSet.size()){
            if(CardUtils.isZha(treeSet)&&!CardUtils.isQueue(preTreeSet)){
                return true;
            }else if(CardUtils.isQueue(treeSet)){
                return true;
            }
        }
        else {
            if(CardUtils.isZha(treeSet)&&CardUtils.isZha(preTreeSet)){
                return treeSet.first().getNumber()>preTreeSet.first().getNumber();
            }
            if(CardUtils.compareSAN1(treeSet,preTreeSet)){
                return true;
            }
            if(CardUtils.compareDouble(treeSet,preTreeSet)){
                return true;
            }
            if(CardUtils.compareSAN2(treeSet,preTreeSet)){
                return true;
            }
            if(CardUtils.compareLianxu(treeSet,preTreeSet)){//连续超过5张
                return true;
            }
            if(treeSet.size()==1&&preTreeSet.size()==1){//一张
                return treeSet.first().getNumber()>preTreeSet.first().getNumber();
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
