package com.alin.cross.make;


import com.alin.cross.config.Config;
import com.alin.cross.request.Request;
import com.alin.cross.request.RequestBean;
import com.alin.cross.request.RequestParameters;
import com.alin.cross.response.Response;
import com.alin.cross.response.ResponseBean;
import com.alin.cross.util.CrossUtils;
import com.alin.cross.util.Dispatcher;
import com.alin.cross.util.ObjectCache;
import com.google.gson.Gson;

import java.lang.reflect.Method;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/9 11:56
 * 版    本   ： ${TODO}
 * 描    述   ：  ${这个过程是在服务端进行的}
 * ================================================
 */
public abstract class IMakeResponse {
    public Request mRequest;
    public Gson mGson;
    public Method mMethod;
    public Dispatcher mDispatcher;
    public Object[] mRealParameters;
    public ObjectCache mObjectCache;
    protected Class<?> mInstanceClass;
    private final Config mConfig;

    public IMakeResponse(){
        mGson = new Gson();
        mObjectCache = ObjectCache.getInstance();
        mConfig = Config.getInstance();
    }

    public Response makeResponse(Request request) {
        mConfig.checkRegisterConfig();
        mRequest = request;
        String requestData = request.getRequestData();
        RequestBean requestBean = mGson.fromJson(requestData, RequestBean.class);
        String returnClassName = requestBean.getReturnClassName();
        mInstanceClass = CrossUtils.findClass(returnClassName);
        mDispatcher = Dispatcher.getInstance();
        //        查找被调用方法的参数
        findInvokeMethodParameters(requestBean);
        //        查找被调用的方法
        mMethod =  findInvokeMethod(requestBean);
        //        反射调用已经被查找到的方法
        Object result = invokeMethod();
        //        被调用的方法有返回值

        Response response = null;
        if (result != null) {
            ResponseBean responseBean = new ResponseBean(result);
            response = new Response(mGson.toJson(responseBean));
        }
        return response;
    }

    protected abstract Object invokeMethod();

    /**
     *  获取需要调用的方法
     * @param requestBean
     * @return
     */
    private Method findInvokeMethod(RequestBean requestBean) {
        Method method = mDispatcher.findMethod(requestBean);
        return method;
    }

    //        查找被调用方法的参数
    private void findInvokeMethodParameters(RequestBean requestBean) {
        RequestParameters[] parameters = requestBean.getParameters();
        if (parameters.length > 0) {
            mRealParameters = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                RequestParameters parameter = parameters[i];
                String parameterName = parameter.getParameterName();
                Class<?> clzz = CrossUtils.findClass(parameterName);
                Object realParameter = mGson.fromJson(parameter.getParameterValue(), clzz);
                mRealParameters[i] = realParameter;
            }
        }else {
            mRealParameters = new Object[0];
        }
    }
}
