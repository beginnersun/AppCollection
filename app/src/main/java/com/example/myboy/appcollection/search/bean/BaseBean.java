package com.example.myboy.appcollection.search.bean;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.myboy.appcollection.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import dalvik.system.DexFile;

public class BaseBean extends AppCompatActivity {

    private String name="hjo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Package p = getClass().getPackage();
        Log.e(name,p.getName());
        Log.e(name,getPackageName());
        Log.e(name,getPackageCodePath());
        Log.e(name,getPackageResourcePath());

        scan(this,getPackageName());

        DexFile dexFile = null;
        try {
            dexFile = new DexFile(BaseBean.this.getPackageCodePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration<String> enumeration = dexFile.entries();
        while (enumeration.hasMoreElements()) {
            String className = enumeration.nextElement();
            Log.i("hjo_classname", className);
        }
    }

    public static Map scan(Context context, String packageName) {
//生成类名对应类名的映射 WifiController --> com.kehuantiantang.controller.WifiController
        Map classes = new HashMap<>();
        try {
//apk的dex文件,获得源码来反射
            DexFile dex = new DexFile(context.getPackageCodePath());
//枚举类,来遍历所有源代码
            Enumeration entries = dex.entries();
            Log.e("hjo","fasdfdas");
            while (entries.hasMoreElements()) {
                String className = (String) entries.nextElement();
//Add whole classname --> package + classname
//classList.add(className);
//指定package包里面的文件
                if (className.contains(packageName)) {
                    Log.e("hjo",className);
//排除包含有excludeString的类
//                    if (!className.matches(excludeString)) {
////需不需要留结尾的tag
//                        if (!"".equals(endTag)) {
//                            classes.put(className.substring(className.lastIndexOf(".") + 1, className.length() - endTag.length()), className);
//                        } else {
//                            classes.put(className.substring(className.lastIndexOf(".") + 1, className.length()), className);
//                        }
//                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private List getClasses(Context mContext, String packageName) {
        ArrayList classes = new ArrayList<>();
        try {
            String packageCodePath = mContext.getPackageCodePath();
            File dir = new File(packageCodePath).getParentFile();
            Log.e("hjo",dir.getAbsolutePath());
            Log.e("hjo",dir.getCanonicalPath());
            Log.e("hjo",dir.getName());

            DexFile df = new DexFile(packageCodePath);
            String regExp = "^" + packageName + ".\\w+$";
            for (Enumeration iter = df.entries(); iter.hasMoreElements(); ) {
                String className = (String) iter.nextElement();
                Log.e("hjo",className);
                if (className.matches(regExp)) {
                    classes.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public List<String > getClassName(String packageName){
        List<String >classNameList=new ArrayList<String >();
        try {
            DexFile df = new DexFile(this.getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = (String) enumeration.nextElement();
                if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classNameList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  classNameList;
    }

}
