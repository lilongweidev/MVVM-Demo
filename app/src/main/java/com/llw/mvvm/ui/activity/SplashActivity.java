package com.llw.mvvm.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
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
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        setStatusBar(true);
        EasyAnimation.moveViewWidth(binding.tvTranslate, () -> {
            binding.tvMvvm.setVisibility(View.VISIBLE);
            jumpActivityFinish(mvUtils.getBoolean(Constant.IS_LOGIN) ? MainActivity.class : LoginActivity.class);
        });
    }
}