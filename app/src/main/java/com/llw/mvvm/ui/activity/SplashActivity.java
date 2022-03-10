package com.llw.mvvm.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivitySplashBinding;
import com.llw.mvvm.utils.Constant;
import com.llw.mvvm.utils.EasyAnimation;
import com.llw.mvvm.utils.MVUtils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 欢迎页面
 *
 * @author llw
 */
@AndroidEntryPoint
public class SplashActivity extends BaseActivity {

    @Inject
    MVUtils mvUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivitySplashBinding.inflate(getLayoutInflater()).getRoot());
        new Handler().postDelayed(() -> jumpActivityFinish(mvUtils.getBoolean(Constant.IS_LOGIN) ? MainActivity.class : LoginActivity.class),400);
    }
}