package com.alin.cross.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/9 15:46
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class ObjectCache {

    private static ObjectCache sObjectCache;

    private ConcurrentHashMap<String,Object> mCacheMap;

    public static ObjectCache getInstance(){
        if (sObjectCache == null) {
            synchronized (ObjectCache.class){
                sObjectCache = new ObjectCache();
            }
        }
        return sObjectCache;
    }

    public ObjectCache(){
        mCacheMap = new ConcurrentHashMap<>();
    }

    public void put(String key,Object value){
        mCacheMap.put(key,value);
    }

    public Object get(String key){
        return mCacheMap.get(key);
    }
}

