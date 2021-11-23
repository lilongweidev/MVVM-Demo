package com.llw.mvvm.ui.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivitySplashBinding;
import com.llw.mvvm.utils.Constant;
import com.llw.mvvm.utils.EasyAnimation;
import com.llw.mvvm.utils.MVUtils;

/**
 * 欢迎页面
 *
 * @author llw
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        setStatusBar(true);
        EasyAnimation.moveViewWidth(binding.tvTranslate, () -> {
            binding.tvMvvm.setVisibility(View.VISIBLE);
            jumpActivity(MVUtils.getBoolean(Constant.IS_LOGIN) ? MainActivity.class : LoginActivity.class);
        });
    }
}