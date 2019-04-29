package com.example.myboy.appcollection.testserivice;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.example.myboy.appcollection.cardgame.utils.ToastUtils;

import java.util.List;

public class WeChatLogService extends AccessibilityService {
    public WeChatLogService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        switch (event.getEventType()){
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                getViews(rootNode);
                break;
        }
    }

    @Override
    protected void onServiceConnected() {
        Toast.makeText(this, "服务已开启", Toast.LENGTH_SHORT).show();
        super.onServiceConnected();
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this, "我快被终结了啊-----", Toast.LENGTH_SHORT).show();
    }


    public void getViews(AccessibilityNodeInfo nodeInfo){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> nodeInfoList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aa");
            if (nodeInfoList.size() == 0){
                System.err.println("找联系人id个数为0");
                return;
            }
            System.err.println("找消息记录个数为"+nodeInfoList.size());
            AccessibilityNodeInfo finalNode = nodeInfoList.get(nodeInfoList.size()-1);
            List<AccessibilityNodeInfo> content = finalNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ani");
            List<AccessibilityNodeInfo> imgaName = content.get(0).findAccessibilityNodeInfosByViewId("com.tencent.mm:id/oc");
            List<AccessibilityNodeInfo> record = finalNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/oe");
            System.out.println("找图片头像image个数" + imgaName.size() + "内容个数"+record.size());
            System.out.println("个数聊天内容为"+record.get(0).getContentDescription() + "," + record.get(0));
            if (record.get(0)!=null){
                Toast.makeText(this, "内容是:"+record.get(0).getText().toString(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "没有获取到内容", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "服务已被关闭", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }
}
