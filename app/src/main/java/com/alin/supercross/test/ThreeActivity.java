package com.alin.supercross.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alin.cross.core.Cross;
import com.alin.cross.core.CrossService;
import com.alin.supercross.R;

/**
 * ================================================
 * 作    者   ： alin
 * 创建时间    ： 2018/10/10 11:45
 * 版    本   ： ${TODO}
 * 描    述   ：  ${TODO}
 * ================================================
 */
public class ThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_three);
        Cross.getDefault().connect(this, CrossService.CrossService1.class);

    }
}
