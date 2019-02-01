package com.example.myboy.appcollection.cardgame.activity.start;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.activity.home.HomeActivity;
import com.example.myboy.appcollection.cardgame.activity.setting.GameSetting;
import com.example.myboy.appcollection.cardgame.activity.start.adapter.ContactFragmentAdapter;
import com.example.myboy.appcollection.cardgame.activity.start.fragment.contact.ContactFragment;
import com.example.myboy.appcollection.cardgame.activity.start.fragment.friend.FriendFragment;
import com.example.myboy.appcollection.cardgame.activity.start.presenter.Presenter;
import com.example.myboy.appcollection.cardgame.activity.start.presenter.StartPresenter;
import com.example.myboy.appcollection.cardgame.activity.video.VideoCenterActivity;
import com.example.myboy.appcollection.cardgame.base.BaseActivity;
import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class StartActivity extends BaseActivity implements Presenter.View {

    @BindView(R.id.noviciate)
    TextView noviciate;
    @BindView(R.id.junior)
    TextView junior;
    @BindView(R.id.intermediate)
    TextView intermediate;
    @BindView(R.id.senior)
    TextView senior;
    @BindView(R.id.ddz)
    ImageView ddz;
    @BindView(R.id.video_center)
    ImageView videoCenter;
    @BindView(R.id.ranking)
    ImageView ranking;
    @BindView(R.id.setting)
    ImageView setting;

    @BindView(R.id.open_drawer)
    TextView openDrawer;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    StartPresenter presenter;
    ContactFragment contactFragment;
    FriendFragment friendFragment;
    ArrayList<Fragment> fragments;
    ContactFragmentAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setPresenter(new StartPresenter(this));
        super.onCreate(savedInstanceState);
        fragments = new ArrayList<>();
        contactFragment = new ContactFragment();
        friendFragment = new FriendFragment();
        fragments.add(contactFragment);
        fragments.add(friendFragment);
        adapter = new ContactFragmentAdapter(getSupportFragmentManager(),fragments);
        viewpager.setAdapter(adapter);
        tablayout.setTabMode(TabLayout.GRAVITY_FILL);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    protected void requestPermissionFailed(String permission) {
        if(permission == Manifest.permission.READ_CONTACTS){
            contactFragment.setContactPermission(false);
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void requestPermissionSuccess(String permission) {
        if (permission == Manifest.permission.READ_CONTACTS){
            contactFragment.setContactPermission(false);
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void setPresenter(BasePresenter basePresenter) {
        this.presenter = (StartPresenter) basePresenter;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_start;
    }

    @Override
    protected void queryInfo() {
        this.presenter.queryPkCount();
    }

    @OnClick(R.id.open_drawer)
    public void openDrawer(){
        requestPermissions(Manifest.permission.READ_CONTACTS);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.noviciate, R.id.junior, R.id.intermediate, R.id.senior, R.id.ddz, R.id.video_center, R.id.ranking, R.id.setting})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.noviciate:
                intent.setClass(this, HomeActivity.class);
                break;
            case R.id.junior:
                intent.setClass(this, HomeActivity.class);
                break;
            case R.id.intermediate:
                intent.setClass(this, HomeActivity.class);
                break;
            case R.id.senior:
                intent.setClass(this, HomeActivity.class);
                break;
            case R.id.ddz:
                break;
            case R.id.video_center:
                intent.setClass(this, VideoCenterActivity.class);
                break;
            case R.id.ranking:
                break;
            case R.id.setting:
                intent.setClass(this, GameSetting.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void setNoviciateCount(int size) {
        noviciate.setText(String.valueOf(size));
    }

    @Override
    public void setJuniorCount(int size) {
        junior.setText(String.valueOf(size));
    }

    @Override
    public void setIntermediateCount(int size) {
        intermediate.setText(String.valueOf(size));
    }

    @Override
    public void setSeniorCount(int size) {
        senior.setText(String.valueOf(size));
    }
}
