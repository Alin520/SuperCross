package com.alin.supercross.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alin.cross.core.Cross;
import com.alin.cross.core.CrossService;
import com.alin.supercross.R;
import com.alin.supercross.bean.IUserInfo;
import com.alin.supercross.bean.User;



/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/9/28 14:11
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class CallActivity extends AppCompatActivity {

    private String TAG = "CallActivity";
    IUserInfo mIUserInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Cross.getDefault().connect(this, CrossService.CrossService0.class);
    }

//    获取对象
    public void getUserInfoClick(View view) {
        mIUserInfo = Cross.getDefault().getInstance(IUserInfo.class);
        Log.e(TAG,"mIUserInfo=====>" + mIUserInfo.toString());
    }

//    获取数据
    public void getDataClick(View view) {
        Log.e(TAG,"user=====>" + mIUserInfo.getUser().toString());
        Log.e(TAG,"mIUserInfo=====>" + mIUserInfo.toString());
    }

//    设置数据
    public void setDataClick(View view) {
        mIUserInfo.setUser(new User("CallActivity张三",888888));
        mIUserInfo.setAddress("来自CallActivity...地址");
    }
}
