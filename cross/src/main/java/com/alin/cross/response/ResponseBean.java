package com.alin.cross.response;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/9 17:20
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class ResponseBean {
    private Object result;

    public ResponseBean(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
