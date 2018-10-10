package com.alin.cross.core;

import android.util.Log;

import com.alin.cross.response.Response;
import com.alin.cross.response.ResponseBean;
import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/9 11:37
 * 版    本   ： ${TODO}
 * 描    述   ：  ${
 *          1、由于客户端（SecondActivity）没有需要操作的对象（如：UserInfo,在服务端）,所以，从服务端获取的对象只能是一个代理的对象，不能是具体的对象。
 *          同时，调用获取的对象中的方法，由于该对象在服务端是没有的，所以，执行该对象的方法，通过用动态代理实现。在代理中，去执行该方法，然后进行客户端（SecondActivity）
 *          与服务端（MainActivity）间的进程通信。若有返回值，则需要还原返回值，最后通过代理返回
 *       2、代理方法执行的进程是在客户端（SecondActivity）
 * }
 * ================================================
 */
public class CrossInvocationHandler implements InvocationHandler {
    private static String TAG = CrossInvocationHandler.class.getSimpleName();


    private Class<? extends CrossService> mService;
    private Class mClzz;
    private final Gson mGson;

    public <T> CrossInvocationHandler(Class<? extends CrossService> crossService, Class<T> clzz) {
        mService = crossService;
        mClzz = clzz;
        mGson = new Gson();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Response response = Cross.getDefault().sendRequest(mService, mClzz, method, args);
        Object realResponse = null;
        if (response != null) {
            try {
                String result = response.getResult();
                ResponseBean responseBean = mGson.fromJson(result, ResponseBean.class);
                Log.e(TAG,"responseBean===>" + responseBean.toString());
                Object resultObj = responseBean.getResult();
                String resultJson = mGson.toJson(resultObj);
                Class<?> returnType = method.getReturnType();
                realResponse = mGson.fromJson(resultJson, returnType);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return realResponse;
    }
}
