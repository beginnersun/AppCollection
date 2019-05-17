package com.example.myboy.appcollection.testserivice;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.example.myboy.appcollection.cardgame.utils.ToastUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class WeChatLogService extends AccessibilityService {

    private String search_id = "com.tencent.mm:id/l3";
    private String result = "search_result";
    private int count = 0;

    Process process;
    public WeChatLogService() {

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        event.getSource();  // 获得对应点击的结点
        event.getItemCount(); // 获得对应节点下的子节点数量
        switch (event.getEventType()){
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                getViews(rootNode);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Toast.makeText(this,"切换页面",Toast.LENGTH_LONG).show();
                AccessibilityNodeInfo node = finNodeInfo(getRootInActiveWindow(),search_id);  //判断search_id是否存在
                if (node != null){   //是否在有搜索框的微信公众号搜索页面
                    Toast.makeText(this,"更换搜索条件",Toast.LENGTH_LONG).show();
                    if (!TextUtils.isEmpty(node.getText()) && TextUtils.equals(node.getText(),"搜索公众号")){   //搜索框中内容不为空且是 搜索公众号的时候执行 更换搜索条见并搜索功能
                        setNodeText(node, "Android");  //设置好字符串然后 执行搜索
                        clickEvent("adb shell input keyevent 84");  //84 代表对应的keyevent（也就是事件）中的软键盘搜索
                    }

                    /**
                     * 搜索完成后寻找结果组件  如果找得到 就可以执行点击结果的第一条
                     */
                    AccessibilityNodeInfo search_result = finNodeInfo(getRootInActiveWindow(),result);
                    if (search_result!=null){
                        // TODO: 2019/5/7  搜索完成后默认点开第一个 获取完信息后 点击一次返回后   触发切换页面操作 然后执行  清空按钮
                        if (search_result.getChildCount()>=2){ //大于2 说明最少有一个公众号  那么就点击第一个item 检索信息
                            AccessibilityNodeInfo head = search_result.getChild(0);
                            if (!TextUtils.isEmpty(head.getText()) && TextUtils.equals("公众号",head.getText())){  //双重保证 此页面是微信公众号搜索页面
                                AccessibilityNodeInfo first = search_result.getChild(1);
                                first.performAction(AccessibilityNodeInfo.ACTION_CLICK); //模拟点击   会触发跳转页面



                                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);// 全局 执行返回
                            }
                        }
                    }
                }else {  //不是判断是否是在公众号详情页面

                }
                break;
        }
    }

    @Override
    protected void onServiceConnected() {
        Toast.makeText(this, "服务已开启", Toast.LENGTH_SHORT).show();

        //服务开启后申请root权限
        // 申请获取root权限，这一步很重要，不然会没有作用
        try {
            process = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            Toast.makeText(this, "请开启root权限", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        super.onServiceConnected();
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this, "我快被终结了啊-----", Toast.LENGTH_SHORT).show();
    }

    public void setNodeText(AccessibilityNodeInfo node ,String text){

        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,text);
        arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT,0);
        arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT,text.length());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        node.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
        node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,arguments);
        Toast.makeText(this,"设置字符串"+text,Toast.LENGTH_LONG).show();
    }

    /**
     * 更具id寻找组件
     * @param nodeInfo
     * @return
     */
    public AccessibilityNodeInfo finNodeInfo(AccessibilityNodeInfo nodeInfo,String id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            List<AccessibilityNodeInfo> nodeInfoList = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            if (nodeInfoList!=null && nodeInfoList.size() >0){
                AccessibilityNodeInfo node = nodeInfoList.get(0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                    if (node != null){
                        return null;
                    }else {
                        return node;
                    }
                }
            }else {
                return null;
            }
        }
        return null;
    }

    //这条命令相当于按了设备的Backkey键

    public void clickEvent(String cmd){
        try {
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            Log.d("Debug微信", "adbShell: 输出命令:"+cmd+"成功");
        } catch (Throwable t) {
            t.printStackTrace();
            Log.d("Debug微信", "adbShell: 输出命令:"+cmd+"失败");
        }
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
