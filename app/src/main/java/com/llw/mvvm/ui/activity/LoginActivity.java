package com.llw.mvvm.ui.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import android.os.Bundle;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivityLoginBinding;
import com.llw.mvvm.model.User;
import com.llw.mvvm.utils.Constant;
import com.llw.mvvm.utils.MVUtils;
import com.llw.mvvm.viewmodels.LoginViewModel;

/**
 * 登录页面
 * @author llw
 */
public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding dataBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //数据绑定视图
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new LoginViewModel();
        //Model → View
        User user = new User("admin", "123456");
        loginViewModel.getUser().setValue(user);
        //获取观察对象
        MutableLiveData<User> user1 = loginViewModel.getUser();
        user1.observe(this, user2 -> dataBinding.setViewModel(loginViewModel));

        dataBinding.btnLogin.setOnClickListener(v -> {
            if (loginViewModel.user.getValue().getAccount().isEmpty()) {
                showMsg("请输入账号");
                return;
            }
            if (loginViewModel.user.getValue().getPwd().isEmpty()) {
                showMsg("请输入密码");
                return;
            }
            //记录已经登录过
            MVUtils.put(Constant.IS_LOGIN,true);
            showMsg("登录成功");
            jumpActivity(MainActivity.class);
        });
    }
}