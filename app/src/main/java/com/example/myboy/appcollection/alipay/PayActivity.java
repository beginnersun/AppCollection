package com.example.myboy.appcollection.alipay;

import android.Manifest;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.example.myboy.appcollection.R;
import com.example.myboy.appcollection.cardgame.base.BaseActivity;
import com.example.myboy.appcollection.cardgame.base.BasePresenter;
import com.example.myboy.appcollection.cardgame.constant.Constant;
import java.util.Map;

public class PayActivity extends BaseActivity implements View.OnClickListener{

    private static final int SDK_PAY_FLAG = 1;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SDK_PAY_FLAG:  //支付回调
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus,"9000")){
                        // TODO: 2019/3/18 支付成功
                        showAlert("支付成功");
                    }
                    break;
            }
        }
    };

    public void pay(String title,String body,String price){
        Map<String,String> params = OrderInfoUtil2_0.buildOrderParamMap(Constant.appid,"",price,title,"不贵不贵,价格实惠！");
        String orderInfo = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params,Constant.PRIVATE_RSA2_KEY,true);
        final String orderParams = orderInfo + "&" + sign;
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(PayActivity.this);
                Map<String,String> params = payTask.payV2(orderParams,true);
                Message message = new Message();
                message.obj = SDK_PAY_FLAG;
                message.obj = params;
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    protected void requestPermissionFailed(String permission) {
    }

    @Override
    protected void requestPermissionSuccess(String permission) {
        pay("","","");
    }

    @Override
    protected void setPresenter(BasePresenter basePresenter) {

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_alipay;
    }

    @Override
    protected void queryInfo() {

    }

    @Override
    public void onClick(View v) {
        requestPermissions(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}
