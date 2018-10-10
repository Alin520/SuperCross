package com.alin.cross.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.alin.cross.make.IMakeResponse;
import com.alin.cross.make.InstanceMakeResponse;
import com.alin.cross.make.ObjectMakeResponse;
import com.alin.cross.request.Request;
import com.alin.cross.response.Response;


/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/8 14:45
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class CrossService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

//    核心Service
    CoreService.Stub mBinder = new CoreService.Stub() {

        @Override
        public Response send(Request request) throws RemoteException {
            IMakeResponse make = null;
            switch (request.getRequestType()){
                case Cross.TYPE_INSTANCE:
                    make = new InstanceMakeResponse();
                    break;
                    case Cross.TYPE_NEW:
                        make = new ObjectMakeResponse();
                        break;
            }
            return make.makeResponse(request);
        }
    };


    public static class CrossService0 extends CrossService{}

    public static class CrossService1 extends CrossService{}

    public static class CrossService2 extends CrossService{}

    public static class CrossService3 extends CrossService{}

    public static class CrossService4 extends CrossService{}

    public static class CrossService5 extends CrossService{}

    public static class CrossService6 extends CrossService{}

    public static class CrossService7 extends CrossService{}

    public static class CrossService8 extends CrossService{}

    public static class CrossService9 extends CrossService{}






}
