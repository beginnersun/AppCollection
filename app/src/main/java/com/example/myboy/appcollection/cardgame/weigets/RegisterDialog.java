package com.example.myboy.appcollection.cardgame.weigets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.utils.CountDownTimerUtils;
import com.example.myboy.appcollection.cardgame.utils.ToastUtils;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class RegisterDialog extends Dialog {

    CountDownTimerUtils countDownTimerUtils = null;

    EditText edPhone;
    EditText edPassword;
    EditText edCode;
    TextView tvCode;
    TextView tvSure;
    ImageView close;
    private Context context;
    private RegisterClickListener registerClickListener;
    private String country,phone;
    private boolean autoCode;

    public RegisterDialog(@NonNull Context context, int themeResId,RegisterClickListener listener,boolean autoCode) {
        super(context, themeResId);
        this.context = context;
        this.registerClickListener = listener;
        this.country = "86";
        this.autoCode = autoCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        if(countDownTimerUtils == null){
            countDownTimerUtils = new CountDownTimerUtils(60*1000,1000,new SoftReference<TextView>(tvCode));
        }
        View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_register, null);
        setContentView(view);

        edCode = view.findViewById(R.id.ed_code);
        edPhone = view.findViewById(R.id.ed_phone);
        edPassword = view.findViewById(R.id.ed_password);
        tvCode = view.findViewById(R.id.tv_code);
        tvSure = view.findViewById(R.id.tv_sure);
        close = view.findViewById(R.id.close);

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2019/1/20  检查验证码 并且跳转注册
                if(registerClickListener != null){
                    registerClickListener.register(edPhone.getText(),edPassword.getText());
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();//点击关闭直接消失
            }
        });

        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edPhone!=null) {
                    sendCode(country, edPhone.getText().toString());
                }else{
                    ToastUtils.toastError(context,"手机号不能为空",Toast.LENGTH_LONG);
                }
            }
        });

        registerSMS();
        //获取短信支持的国家列表
        SMSSDK.getSupportedCountries();

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    public interface RegisterClickListener {

        void register(Editable phone, Editable password);

    }

    public void setCode(String code){
        if(!TextUtils.isEmpty(code)&&autoCode) {
            edCode.setText(code);
        }
    }

    @Override
    public void dismiss() {
        countDownTimerUtils.cancel();
        countDownTimerUtils = null;
        SMSSDK.unregisterEventHandler(eh);
        super.dismiss();
    }

    public void registerSMS() {
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    public void sendCode(String country,String phone){
        SMSSDK.getVerificationCode(country,phone);
    }

    public void submitCode(String country,String phone,String code){
        SMSSDK.submitVerificationCode(country,phone,code);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                if(result == SMSSDK.RESULT_COMPLETE){//获取验证码成功
                    countDownTimerUtils.start();
                }else{
                    ((Throwable) data).printStackTrace();
                    ToastUtils.toastError(context,"获取验证码失败！！！",Toast.LENGTH_LONG);
                }
            }
            else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                if(result == SMSSDK.RESULT_COMPLETE){ //校验验证码成功
                    if(registerClickListener!=null){
                        registerClickListener.register(edPhone.getText(),edPassword.getText());
                    }
                    dismiss();
                }else{
                    ToastUtils.toastError(context,"验证码错误，请重新输入！！！",Toast.LENGTH_LONG);
                }
            }else if(event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                if(result == SMSSDK.RESULT_COMPLETE){
//                    Log.e("国家列表",data.toString());
//                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;

                }
            }
        }
    };

    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.obj = data;
            msg.arg1 = event;
            msg.arg2 = result;
            handler.sendMessage(msg);
        }
    };


    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };

}
