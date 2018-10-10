package com.alin.cross.util;

import android.text.TextUtils;


import com.alin.cross.config.Config;
import com.alin.cross.config.ConfigKey;
import com.alin.cross.request.RequestBean;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/8 15:02
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class Dispatcher {

    private static  Dispatcher sInstance;

    private ConcurrentHashMap<String,Class> mFindClassCaches;
    private ConcurrentHashMap<Class,ConcurrentHashMap<String,Method>> mFindMethodCaches;
    private final CrossUtils mCrossUtils;
    private final Config mConfig;

    public static Dispatcher getInstance(){
        if (sInstance == null) {
            synchronized (Dispatcher.class){
                if (sInstance == null){
                    sInstance = new Dispatcher();
                }
            }
        }
        return sInstance;
    }

    private Dispatcher(){
        mFindClassCaches = new ConcurrentHashMap<>();
        mFindMethodCaches = new ConcurrentHashMap<>();
        mCrossUtils = CrossUtils.getInstance();
        mConfig = Config.getInstance();
    }

    public  void register(Class<?> clzz) {
        if (clzz == null) {
            throw new IllegalArgumentException("Cross register method params class is not null");
        }
        registerClass(clzz);
        registerMethod(clzz);
        mConfig.updata(ConfigKey.KEY_REGISETER.name());
    }


    /**
     *  缓存class，以及父类中的所有方法
     * @param clzz
     */
    private void registerMethod(Class<?> clzz) {
        Method[] methods = clzz.getDeclaredMethods();
        if (methods == null || methods.length <= 0) {
            return;
        }
        mFindMethodCaches.putIfAbsent(clzz,new ConcurrentHashMap<String, Method>());
        ConcurrentHashMap<String, Method> methodMap = mFindMethodCaches.get(clzz);
        for (Method method : methods) {
            String methodId = mCrossUtils.findMethodId(method);
            methodMap.put(methodId,method);
        }
        if (clzz.getSuperclass() != null && clzz.getSuperclass() != Object.class) {
            registerMethod(clzz.getSuperclass());
        }

        if (clzz.getInterfaces() != null && clzz.getInterfaces().length > 0){
            for (Class<?> interfaceClz : clzz.getInterfaces()) {
                registerMethod(interfaceClz);
            }
        }
    }

    private void registerClass(Class<?> clzz) {
        try {
            String name = clzz.getName();
            mFindClassCaches.put(name,clzz);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  根据className，去缓存表mFindClassCaches中查找类class
     * @param className
     * @return
     */
    public Class<?> findClass(String className){
        Class<?> resultClz = null;
        try {
            if (mFindClassCaches.containsKey(className)){
                resultClz = mFindClassCaches.get(className);
            }else {
                resultClz = Class.forName(className);
                mFindClassCaches.put(className,resultClz);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultClz;
    }

    /**
     *  根据methodId，去缓存表mFindMethodCaches中查找方法
     * @param requestBean
     * @return
     */
    public Method findMethod(RequestBean requestBean) {
        Method method = null;
        if (requestBean == null) {
            return method;
        }
        String methodId = requestBean.getMethodId();
        Class<?> aClass = findClass(requestBean.getClassName());

        if (aClass != null && !TextUtils.isEmpty(methodId)) {
            ConcurrentHashMap<String, Method> hashMap = mFindMethodCaches.get(aClass);
            method = hashMap.get(methodId);
        }
        return method;
    }

}
