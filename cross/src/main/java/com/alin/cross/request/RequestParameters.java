package com.alin.cross.request;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/8 17:54
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class RequestParameters {

    private String parameterName;

    private String parameterValue;

    public RequestParameters(String parameterName, String parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public String getParameterName() {
        return parameterName == null ? "" : parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue == null ? "" : parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
