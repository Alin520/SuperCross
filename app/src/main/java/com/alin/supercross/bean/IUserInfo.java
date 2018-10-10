package com.alin.supercross.bean;


import com.alin.cross.annotation.ClassId;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/9/21 16:27
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
@ClassId("com.alin.supercross.bean.UserInfo")
public interface IUserInfo {

    void setUser(User user);

    User getUser();

   String getAddress();

   void setAddress(String address);

}
