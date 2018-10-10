package com.alin.cross.core;


import android.content.Context;
import android.util.Log;


import com.alin.cross.annotation.ClassId;
import com.alin.cross.config.Config;
import com.alin.cross.manager.ServiceConnectionManger;
import com.alin.cross.request.Request;
import com.alin.cross.request.RequestBean;
import com.alin.cross.request.RequestParameters;
import com.alin.cross.response.Response;
import com.alin.cross.util.CrossUtils;
import com.alin.cross.util.Dispatcher;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/8 14:42
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class Cross {
    private static String TAG = Cross.class.getSimpleName();


    //得到对象
    public static final int TYPE_NEW = 0;
    //得到单例
    public static final int TYPE_INSTANCE = 1;


    private static  Cross sDefault;
    private final Dispatcher mDispatcher;
    private final ServiceConnectionManger mServiceConnectionManger;
    private Context mContext;
    private final Gson mGson;
    private final CrossUtils mCrossUtils;
    private final Config mConfig;


    private Cross(){
        mDispatcher = Dispatcher.getInstance();
        mServiceConnectionManger = ServiceConnectionManger.getInstance();
        mCrossUtils = CrossUtils.getInstance();
        mGson = new Gson();
        mConfig = Config.getInstance();
    }
    
    
    public static Cross getDefault() {
        if (sDefault == null) {
            synchronized (Cross.class){
                if (sDefault == null){
                    sDefault = new Cross();
                    Log.i(TAG,"Cross======>" + sDefault);
                }
            }
        }
        return sDefault;
    }

    /**
     *  注册事件event，缓存event的对象、class、以及方法
     * @param clzz
     */
    public void register(Class<?> clzz) {
        mDispatcher.register(clzz);
    }


//    连接Service
    public void connect(Context context, Class<? extends CrossService> service) {
        connect(context,null,service);
    }

    public void connect(Context context,String serviceName, Class<? extends CrossService> service) {
        initContext(context);
        mServiceConnectionManger.connect(context,serviceName,service);
    }

    private void initContext(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     *  如： UserInfo info = UserInfo.getInstance("张三")
     * @param clzz     事件event 对象的class
     * @param parameters   事件event 对象的class
     * @param <T>      实例化事件event 对象需要传入的参数（"张三"）
     * @return   需要获取的事件event对象
     */
    public <T> T getInstance(Class<T> clzz, Object... parameters) {
       return  getInstance(mServiceConnectionManger.getCurrentService(),clzz,parameters);
    }


    public <T> T getInstance(Class<? extends CrossService> crossService, Class<T> clzz, Object... parameters) {
        mConfig.checkConnectServiceConfig();
        Response response = sendRequest(crossService, clzz,null,parameters);
        return invokeProxy(crossService,clzz);
    }

    /**
     *  如： UserInfo info = UserInfo.getInstance("张三")
     * @param clzz     事件event 对象的class
     * @param parameters   事件event 对象的class
     * @param method
     * @param <T>      实例化事件event 对象需要传入的参数（"张三"）
     * @return   需要获取的事件event对象
     */
    public  <T> Response sendRequest(Class<? extends CrossService> crossService, Class<T> clzz,Method method, Object... parameters) {
        RequestBean requestBean = new RequestBean();
        String className;
        String returnClassName;
        if (clzz.getAnnotation(ClassId.class) == null) {
            className = clzz.getName();
            returnClassName = clzz.getName();
        }else {
            className = clzz.getAnnotation(ClassId.class).value();
            returnClassName = clzz.getAnnotation(ClassId.class).value();
        }
        requestBean.setClassName(className);
        requestBean.setReturnClassName(returnClassName);

        /**
         *  客户端需要拿到调用服务端进程方法的methodId。因为只有拿到了这个methodId，才能在服务端去找到对应的调用的方法
         *  methodId: 方法全类名 ,方法名 统一传 方法名+参数名  getInstance(java.lang.String)
         */
        String methodId;
        if (method != null) {
            /**
             * 若method方法不为空，则是已经获取到了class的单例对象。然后根据对象调用其内部方法
             */
             methodId = mCrossUtils.findMethodId(method);
            requestBean.setMethodId(methodId);
        }else {
            /**
             *  若method方法为空，则是获取class的单例对象。此时需要构建methodId，因为在服务端的缓存列表mFindMethodInfoCaches中，
             *   是根据methodId去查找获取单例的方法getInstance()的
             */
            methodId = mCrossUtils.getInstanceMethodId("getInstance",parameters);
        }
        Log.i(TAG,"methodId====>" + methodId);
        requestBean.setMethodId(methodId);
        RequestParameters[] requestParameters;
        if (parameters != null && parameters.length > 0) {
            requestParameters = new RequestParameters[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                String parameterName = parameter.getClass().getName();
                String parameterValue = mGson.toJson(parameter);
                RequestParameters requestParameter = new RequestParameters(parameterName, parameterValue);
                requestParameters[i] = requestParameter;
            }
        }else {
            requestParameters = new RequestParameters[0];
        }
        requestBean.setParameters(requestParameters);


        int type = method == null ? TYPE_INSTANCE : TYPE_NEW;
        Request request = new Request(mGson.toJson(requestBean), type);
        return mServiceConnectionManger.request(crossService,request);
    }

    private <T> T invokeProxy(Class<? extends CrossService> crossService, Class<T> clzz) {
        ClassLoader loader = crossService.getClassLoader();
        T proxyInstance = (T) Proxy.newProxyInstance(loader, new Class[]{clzz}, new CrossInvocationHandler(crossService,clzz));
        return proxyInstance;
    }


}
