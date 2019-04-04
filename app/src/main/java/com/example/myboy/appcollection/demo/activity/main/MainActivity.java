package com.example.myboy.appcollection.demo.activity.main;

import android.os.Bundle;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.databinding.ActivityDemoBinding;
import com.example.myboy.appcollection.databinding.NavigationHeaderBinding;
import com.example.myboy.appcollection.demo.activity.main.adapter.ViewPagerAdapter;
import com.example.myboy.appcollection.demo.base.BaseActivity;
import com.example.myboy.appcollection.demo.base.BaseViewModel;
import com.example.myboy.appcollection.demo.bean.User;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class MainActivity extends BaseActivity {


    ActivityDemoBinding binding;
    MainViewModel viewModel;
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private List<Fragment> fragments = new ArrayList<Fragment>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void bindData(@Nullable Bundle savedInstanceState) {
        binding = (ActivityDemoBinding) getBinding();
        viewModel = (MainViewModel) getViewModel();
        binding.setModel(viewModel);

        toolbar = binding.toolbar;
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigation;

        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.mipmap.ic_launcher);  //设置logo
        toolbar.setNavigationIcon(R.mipmap.qq_icon); //设置用来打开 navigationView的图标（必须在此之前调用setSupportActionBar(toolbar)方法 把次toolbar设置为系统bar)

        //设置可旋转的导航菜单控件
        ActionBarDrawerToggle actionBarDrawerToggle=  new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        //同步状态，刷新页面

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        viewModel.RefreshUser();

        NavigationHeaderBinding navigationBinding = NavigationHeaderBinding.bind(navigationView.getHeaderView(0));

        viewModel.getLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                navigationBinding.setUser(user);
            }
        });
        navigationView.setItemIconTintList(null);

        binding.viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments,titles));
        binding.tablayout.setupWithViewPager(binding.viewpager);


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_demo;
    }

    @Override
    protected Class<? extends BaseViewModel> getModelClass() {
        return MainViewModel.class;
    }
}
