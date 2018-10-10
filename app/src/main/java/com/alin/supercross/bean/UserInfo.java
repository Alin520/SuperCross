package com.alin.supercross.bean;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/9/21 16:27
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class UserInfo implements IUserInfo{

    private User mUser;

    private String mAddress;

    private static UserInfo mInstance;

    private UserInfo(){}

    public static UserInfo getInstance(){
        if (mInstance == null) {
            mInstance = new UserInfo();
        }
        return mInstance;
    }

    @Override
    public void setUser(User user) {
        this.mUser = user;
    }

    @Override
    public User getUser() {
        return mUser;
    }

    @Override
    public String getAddress() {
        return mAddress;
    }

    @Override
    public void setAddress(String address) {
        this.mAddress = address;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "mUser=" + mUser +
                ", mAddress='" + mAddress + '\'' +
                '}';
    }
}
