package com.alin.cross.config;

import java.util.HashMap;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/10 13:38
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class Config {

    private static final HashMap<String,Boolean> CONFIGS = new HashMap<>();

    public static class Holder{
        public static final Config INSTANCE = new Config();
    }

    public static Config getInstance(){
        return Holder.INSTANCE;
    }

    private Config(){
        CONFIGS.put(ConfigKey.KEY_CONNECTED.name(),false);
        CONFIGS.put(ConfigKey.KEY_REGISETER.name(),false);
    }

    public Boolean get(String key){
        return CONFIGS.get(key);
    }

    public void  put(String key, Boolean value){
        CONFIGS.put(key,value);
    }

    public void updata(String key){
        CONFIGS.put(key,true);
    }

    public void checkRegisterConfig() {
        boolean isRegister = CONFIGS.get(ConfigKey.KEY_REGISETER.name());
        if (!isRegister) {
            throw new RuntimeException("not init error, please compelete register in server .");
        }

    }
    public void checkConnectServiceConfig() {
        boolean isConnected = CONFIGS.get(ConfigKey.KEY_CONNECTED.name());
        if (!isConnected) {
            throw new RuntimeException("not init error, please compelete connect service  in client .");
        }
    }

}
