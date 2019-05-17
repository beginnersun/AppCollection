package com.example.myboy.appcollection.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class OkHttpProxy implements InvocationHandler {

    private Object targetObject;

    public Object newProxyInstance(Object targetObject){
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),targetObject.getClass().getInterfaces(),this);  //返回一个代理的对象   代理对象执行方法是就会进入下面的invoke方法进行处理
    }

    /**
     * @param proxy  代理对象
     * @param method  操纵的方法
     * @param args  参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return null;
    }
}
