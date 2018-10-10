package com.alin.cross.manager;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;


import com.alin.cross.config.Config;
import com.alin.cross.config.ConfigKey;
import com.alin.cross.core.CoreService;
import com.alin.cross.core.CrossService;
import com.alin.cross.request.Request;
import com.alin.cross.response.Response;
import com.alin.cross.util.Dispatcher;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/8 15:55
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class ServiceConnectionManger {
    private static String TAG = ServiceConnectionManger.class.getSimpleName();


    private static ServiceConnectionManger sInstance;
    private ConcurrentHashMap<Class<? extends CrossService>,CoreService> mCoreServiceCache;
    private ConcurrentHashMap<Class<? extends CrossService>,CrossServiceConnection> mServiceConnectionCache;
    private Stack<Class<? extends CrossService>> mServiceCache;
    private final Config mConfig;


    public static ServiceConnectionManger getInstance(){
        if (sInstance == null) {
            synchronized (Dispatcher.class){
                if (sInstance == null){
                    sInstance = new ServiceConnectionManger();
                }
            }
        }
        return sInstance;
    }

    private ServiceConnectionManger(){
        mServiceConnectionCache = new ConcurrentHashMap<>();
        mCoreServiceCache = new ConcurrentHashMap<>();
        mServiceCache = new Stack<>();
        mConfig = Config.getInstance();
    }

    public void connect(Context context, String serviceName, Class<? extends CrossService> service) {
        Intent intent = null;
        CrossServiceConnection connection = null;
        if (TextUtils.isEmpty(serviceName)) {
            intent = new Intent(context, service);
        }else {
            intent = new Intent();
            intent.setClassName(context.getPackageName(),serviceName);
        }

        if (mServiceConnectionCache.containsKey(service)) {
            connection = mServiceConnectionCache.get(service);
        }else {
            connection = new CrossServiceConnection(service);
            mServiceConnectionCache.put(service,connection);
        }
        context.bindService(intent,connection, Service.BIND_AUTO_CREATE);
    }

//    发起请求
    public Response request(Class<? extends CrossService> crossService, Request request) {
        Response response = null;
        CoreService service = mCoreServiceCache.get(crossService);
        if (service != null) {
            try {
                response = service.send(request);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public <T extends CrossService> Class<T>  getCurrentService(){
        Log.i(TAG,"mServiceCache===lastElement===>" + mServiceCache.lastElement());
        return (Class<T>) mServiceCache.lastElement();
    }

    //     接受远端的binder 对象   进程B就可以了通过Binder对象去操作 服务端的 方法
    public class CrossServiceConnection implements ServiceConnection{

       Class<? extends CrossService> mServiceClzz;
        public CrossServiceConnection(Class<? extends CrossService> service){
            mServiceClzz = service;
            mServiceCache.push(service);
            Log.i(TAG,"mServiceCache==>" + mServiceCache);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CoreService coreService = CoreService.Stub.asInterface(service);
            if (coreService != null) {
                mCoreServiceCache.put(mServiceClzz,coreService);
            }
            mConfig.updata(ConfigKey.KEY_CONNECTED.name());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (mServiceConnectionCache.containsKey(mServiceClzz)) {
                mServiceConnectionCache.remove(mServiceClzz);
            }
            if (mCoreServiceCache.containsKey(mServiceClzz)){
                mCoreServiceCache.remove(mServiceClzz);
            }
            mServiceCache.pop();
        }
    }
}
