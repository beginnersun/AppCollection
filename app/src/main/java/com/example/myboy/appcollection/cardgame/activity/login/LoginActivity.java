package com.example.myboy.appcollection.cardgame.activity.login;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.activity.login.presenter.LoginPresenter;
import com.example.myboy.appcollection.cardgame.activity.login.presenter.Presenter;
import com.example.myboy.appcollection.cardgame.base.BaseActivity;
import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import com.example.myboy.appcollection.cardgame.bean.UserBean;
import com.example.myboy.appcollection.cardgame.utils.StringUtils;
import com.example.myboy.appcollection.cardgame.utils.ToastUtils;
import com.example.myboy.appcollection.cardgame.weigets.RegisterDialog;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements Presenter.View,RegisterDialog.RegisterClickListener {

    private static final String TAG = "LoginActivity";
    LoginPresenter presenter;
    @BindView(R.id.ed_username)
    EditText edUsername;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    private RegisterDialog dialog;
    private IntentFilter filter;
    private CodeSMSReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setPresenter(new LoginPresenter(this));
        super.onCreate(savedInstanceState);
        requestPermissions(new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE});
    }

    @Override
    protected void requestPermissionFailed(String permission) {
        Toast.makeText(this,"不开启获取短信权限则无法自动获取验证码！",
                Toast.LENGTH_LONG).show();
        showRegisterDialog(false);
    }

    @Override
    protected void requestPermissionSuccess(String permission) {
        showRegisterDialog(true);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void setPresenter(BasePresenter basePresenter) {
        this.presenter = presenter;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void queryInfo() {

    }

    /**
     * 登录成功
     */
    @Override
    public void loginSuccess() {

    }

    /**
     * 登录失败
     *
     * @param error 失败原因
     */
    @Override
    public void loginFailed(String error) {
        ToastUtils.toastError(this,error,Toast.LENGTH_LONG);
    }

    @Override
    public void registerSuccess() {
        dialog.dismiss();
        ToastUtils.toastSuccess(this,"注册成功",Toast.LENGTH_LONG);
    }

    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                presenter.login(edUsername.getText(),edPassword.getText());
                break;
            case R.id.tv_register:
                requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS});
                break;
        }
    }

    public void showRegisterDialog(boolean autoCode){
        dialog = new RegisterDialog(this, R.style.MyMiddleDialogStyle,this,autoCode);
        dialog.show();
        if(autoCode){
            filter = new IntentFilter();
            receiver = new CodeSMSReceiver();
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.setPriority(1000);
            registerReceiver(receiver,filter);
        }
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                unregisterReceiver(receiver);
            }
        });
    }

    @Override
    public void register(Editable phone, Editable password) {
        UserBean userBean = new UserBean();
        if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(password)){
            userBean.setPhone(phone.toString());
            userBean.setPhone(password.toString());
            presenter.register(userBean);

        }
    }

    class CodeSMSReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Object[] objects = (Object[]) bundle.get("pdus");
            String format = intent.getStringExtra("format");
            for (Object object:objects){
                SmsMessage sms = null;
                if(Build.VERSION.SDK_INT >=23) {
                    sms = SmsMessage.createFromPdu((byte[]) object, format);
                }else{
                    sms = SmsMessage.createFromPdu((byte[]) object);
                }
                String address = sms.getOriginatingAddress();
                String message = sms.getDisplayMessageBody();
                if(!TextUtils.isEmpty(address)&&!TextUtils.isEmpty(message)){
                    if(address.equals("1069050046901166")||message.contains("我爱斗地主的验证码")){
                        dialog.setCode(StringUtils.getNumber(message));
                    }
                }
            }
        }
    }
}
