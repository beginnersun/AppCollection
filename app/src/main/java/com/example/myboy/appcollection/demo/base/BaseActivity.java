package com.example.myboy.appcollection.demo.base;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseActivity extends AppCompatActivity {

    ViewDataBinding binding;
    BaseViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,getLayoutResId());
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(getModelClass());
        bindData(savedInstanceState);
    }
    protected ViewDataBinding getBinding(){
        return binding;
    }

    protected BaseViewModel getViewModel(){
        return viewModel;
    }

    protected abstract void bindData(@Nullable Bundle savedInstanceState);

    protected abstract int getLayoutResId();

    protected abstract @NonNull Class<? extends BaseViewModel> getModelClass();
}
