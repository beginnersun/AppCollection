package com.example.myboy.appcollection.cardgame.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.myboy.appcollection.cardgame.utils.ActivityCollector;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public abstract class BaseActivity extends AppCompatActivity {

    Unbinder unbinder;
    private static final String TAG = "BaseActivity";
    private OffLineReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getClass().getSimpleName());
        ActivityCollector.addActivity(this);
        setContentView(getLayoutResourceId());
        unbinder = ButterKnife.bind(this);
        queryInfo();
    }

    protected void requestPermissions(String... permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ //如果大于等于23才回动态申请否则
            if(permissions.length==1){ //如果权限只有一个 那就用一个的方法
                requestPermission(permissions[0]);
            }else {
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if(permission.granted){ //成功
                            requestPermissionSuccess(permission.name);
                        } else if(permission.shouldShowRequestPermissionRationale){ //请求失败 但是没有勾选不再提示 所以下次还可以继续申请
                            requestPermissionFailed(permission.name);
                        }
                        else{ //请求失败 且禁止提醒  （以后都不会有这个权限)
                            requestPermissionFailed(permission.name);//需要跳转到系统设置页面进行更改
                        }
                    }
                });
            }
        }else{ //  小于23 权限直接拥有
            for(String permission:permissions){
                requestPermissionSuccess(permission);
            }
        }
    }

    private void requestPermission(String permission){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permission).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if(aBoolean){
                    requestPermissionSuccess(permission);
                }else {
                    requestPermissionFailed(permission);
                }
            }
        });
    }

    /**
     * 申请权限失败
     * @param permission 权限名
     */
    protected abstract void requestPermissionFailed(String permission);
    /**
     * 申请权限成功
     * @param permission 权限名
     */
    protected abstract void requestPermissionSuccess(String permission);

    protected abstract void setPresenter(BasePresenter basePresenter);

    /**
     * 获取layout资源id
     * @return
     */
    protected abstract int getLayoutResourceId();

    /**
     * 请求信息
     */
    protected abstract void queryInfo();

    /**
     * 刷新屏幕，重新请求信息
     */
    protected void refresh(){
        queryInfo();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 只在栈顶的Activity中进行广播监听
     */
    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.myboy.appcollection");
        super.onResume();
    }

    /**
     * onPause 进行取消监听
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 下线通知
     */
    class OffLineReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
            builder.setTitle("异地登陆通知").setMessage("您的账号在另一台设备登陆，请您选择重新登陆或者退出账号！").setPositiveButton("重新登陆", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).create().show();

        }
    }
}
