package com.alin.cross.make;

import java.lang.reflect.InvocationTargetException;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/9 11:56
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class InstanceMakeResponse extends IMakeResponse{

    @Override
    protected Object invokeMethod() {
        Object result = null;
        try {
            result = mMethod.invoke(null, mRealParameters);
            mObjectCache.put(result.getClass().getName(),result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
