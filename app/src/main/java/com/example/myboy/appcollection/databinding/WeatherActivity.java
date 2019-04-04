package com.example.myboy.appcollection.databinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.TransitionManager;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.databinding.bean.WeatherBean;
import com.example.myboy.appcollection.databinding.model.WeatherActivityModel;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";

    ActivityWeatherBinding binding;
    WeatherActivityModel model;
    WeatherBean weather = new WeatherBean();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        model = ViewModelProviders.of(this).get(WeatherActivityModel.class);

        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                Log.e(TAG,"onPre");
                ViewGroup sceneRoot = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(sceneRoot);
                return super.onPreBind(binding);
            }

            @Override
            public void onCanceled(ViewDataBinding binding) {
                Log.e(TAG,"onCancel");
                super.onCanceled(binding);
            }

            @Override
            public void onBound(ViewDataBinding binding) {
                Log.e(TAG,"onBound");
                super.onBound(binding);
            }
        });

        binding.setLifecycleOwner(this);
        binding.setModel(model);

//        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        List<CityBean> list = new ArrayList<>();
//        CityBean cityBean = new CityBean();
//        binding.recyclerview.setAdapter(new WeatherAdapter(this,list));
    }


    class ClickListener{
        public void onRefresh(View view){
            Toast.makeText(WeatherActivity.this,"正在刷新数据...",Toast.LENGTH_LONG).show();
            weather.setTemperature("25°");
            weather.setSuggestion("有点热了，吃个冰棍吧！");
//            binding.setWeather(weather);
        }

        public void onExit(View view){
            Toast.makeText(WeatherActivity.this,"警告！警告！有人要关闭系统！",Toast.LENGTH_LONG).show();
        }
    }

    public void alert(View view){
        Toast.makeText(this,"警告！警告！温度过高！",Toast.LENGTH_LONG).show();
    }
}
