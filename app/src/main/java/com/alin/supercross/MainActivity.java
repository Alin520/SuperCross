package com.alin.supercross;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alin.cross.core.Cross;
import com.alin.supercross.bean.User;
import com.alin.supercross.bean.UserInfo;
import com.alin.supercross.test.CallActivity;
import com.alin.supercross.test.ThreeActivity;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cross.getDefault().register(UserInfo.class);
    }

    //    设置数据
    public void setUserInfoClick(View view) {
        UserInfo.getInstance().setUser(new User("名字TestActivity",222222));
        UserInfo.getInstance().setAddress("TestActivity设置的数据===>");

    }


    //    获取数据
    public void getUserInfo(View view) {
        Log.e(TAG,"address===>" + UserInfo.getInstance().getAddress());
        Log.e(TAG,"user===>" + UserInfo.getInstance().getUser());
    }


    public void change(View view) {
        startActivity(new Intent(this,CallActivity.class));
    }

    public void changeThree(View view) {
        startActivity(new Intent(this,ThreeActivity.class));
    }
}
