package com.example.myboy.appcollection.demo.activity.login;

import android.os.Bundle;
import android.util.Log;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.databinding.ActivityDemoLoginBinding;
import com.example.myboy.appcollection.demo.base.BaseActivity;
import com.example.myboy.appcollection.demo.base.BaseViewModel;
import com.example.myboy.appcollection.demo.bean.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

public class LoginActivity extends BaseActivity {

    LoginViewModel viewModel;
    ActivityDemoLoginBinding binding;

    @Override
    protected void bindData(@Nullable Bundle savedInstanceState) {

        binding = (ActivityDemoLoginBinding) getBinding();
        viewModel = (LoginViewModel) getViewModel();
        viewModel.getLiveData().setValue(new User());


        binding.setModel(viewModel);

        viewModel.getLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.e("","数据变化");
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_demo_login;
    }

    @NonNull
    @Override
    protected Class<? extends BaseViewModel> getModelClass() {
        return LoginViewModel.class;
    }
}
