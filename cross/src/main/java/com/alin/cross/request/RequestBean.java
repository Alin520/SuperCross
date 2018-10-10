package com.alin.cross.request;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/8 17:26
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class RequestBean {
    private String mClassName;

    private String mReturnClassName;

    private String mMethodId;

    private RequestParameters[] mParameters;


    public String getClassName() {
        return mClassName == null ? "" : mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public String getReturnClassName() {
        return mReturnClassName == null ? "" : mReturnClassName;
    }

    public void setReturnClassName(String returnClassName) {
        mReturnClassName = returnClassName;
    }

    public String getMethodId() {
        return mMethodId == null ? "" : mMethodId;
    }

    public void setMethodId(String methodId) {
        mMethodId = methodId;
    }

    public RequestParameters[] getParameters() {
        return mParameters;
    }

    public void setParameters(RequestParameters[] parameters) {
        mParameters = parameters;
    }
}
