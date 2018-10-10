package com.alin.cross.make;

import java.lang.reflect.InvocationTargetException;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/9 11:58
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class ObjectMakeResponse extends IMakeResponse {

    @Override
    protected Object invokeMethod() {
        Object obj = mObjectCache.get(mInstanceClass.getName());
        Object result = null;
        try {
            result = mMethod.invoke(obj, mRealParameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }
}
