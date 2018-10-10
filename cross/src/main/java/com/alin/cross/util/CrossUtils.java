package com.alin.cross.util;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/8 15:12
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class CrossUtils {
    private static String TAG = CrossUtils.class.getSimpleName();

    private ConcurrentHashMap<String,ConcurrentHashMap<String,Method>> mMethodCache;

    private static CrossUtils sInstance;

    public static CrossUtils getInstance(){
        if (sInstance == null) {
            synchronized (CrossUtils.class){
                if (sInstance == null) {
                    sInstance = new CrossUtils();
                }
            }
        }
        return sInstance;
    }

    public CrossUtils(){
        mMethodCache = new ConcurrentHashMap<>();
    }

    public String findMethodId(Method method) {
        StringBuilder builder = new StringBuilder(method.getName());
        if (method == null) {
            return builder.toString();
        }
        builder.append("(").append(findMethodParams(method)).append(")");
        String methodId = builder.toString();
        ConcurrentHashMap<String, Method> values = mMethodCache.get(method.getName());
        if (values != null) {
            values.put(methodId,method);
        }else {
            mMethodCache.put(method.getName(),new ConcurrentHashMap<String, Method>());
            values = mMethodCache.get(method.getName());
            values.put(methodId,method);
        }
        //        mMethodCache.put(method.getName(),)
        return methodId;
    }

    private String findMethodParams(Method method) {
        StringBuilder builder = new StringBuilder();
        if (method == null) {
           return builder.toString();
        }
        Class<?>[] parameters = method.getParameterTypes();

        if (parameters != null && parameters.length > 0) {
            builder.append(getRealParameter(parameters[0]));
            if (parameters.length == 1) {
                return builder.toString();
            }
            for (Class<?> parameter : parameters) {
                builder.append(",").append(getRealParameter(parameter));
            }
        }

        return builder.toString();
    }

    private String getRealParameter(Class<?> parameter) {
        String parameterName = null;
        if (parameter == Byte.class || parameter == byte.class){
            parameterName = "byte";
        }else if (parameter == Float.class || parameter == float.class){
            parameterName = "float";
        }else if (parameter == Boolean.class || parameter == boolean.class){
            parameterName = "boolean";
        }else if (parameter == Integer.class || parameter == int.class){
            parameterName = "int";
        }else if (parameter == Double.class || parameter == double.class){
            parameterName = "double";
        }else if (parameter == Short.class || parameter == short.class){
            parameterName = "short";
        }else if (parameter == Long.class || parameter == long.class){
            parameterName = "long";
        }else if (parameter == Void.class || parameter == void.class){
            parameterName = "void";
        }else if (parameter == Character.class || parameter == char.class){
            parameterName = "char";
        }else {
            parameterName = parameter.getName();
        }
        return parameterName;
    }

    public static Class<?> findClass(String className) {
        Class<?> resultClzz = null;
        try {
            resultClzz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultClzz;
    }

    public Method findMethodByName(String methodName, Object[] parameters) {
        Method method = null;
        ConcurrentHashMap<String, Method> methodCacheMap = mMethodCache.get(methodName);
        if (methodCacheMap == null) {
            return method;
        }
        String methodId = getInstanceMethodId(methodName, parameters);
        method = methodCacheMap.get(methodId);
        return method;
    }

    public String getInstanceMethodId(String methodName, Object[] parameters) {
        StringBuilder builder = new StringBuilder();
        if (parameters == null || parameters.length == 0) {
            return builder.append(methodName).append("(").append(")").toString();
        }else {
            builder.append(methodName).append("(").append(getRealParameter(parameters[0].getClass()));
            if (parameters.length == 1) {
                return builder.append(")").toString();
            }
            for (int i = 1; i < parameters.length; i++) {
                builder.append(",").append(getRealParameter(parameters[i].getClass()));
            }
            builder.append(")");
        }
        Log.i(TAG,"getInstanceMethodId===22===>" + builder.toString());
        return builder.toString();
    }
}
