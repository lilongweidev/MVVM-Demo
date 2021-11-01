package com.llw.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.llw.mvvm.databinding.ActivityLoginBinding;
import com.llw.mvvm.databinding.ActivityMainBinding;
import com.llw.mvvm.model.User;
import com.llw.mvvm.viewmodels.LoginViewModel;
import com.llw.mvvm.viewmodels.MainViewModel;

public class LoginActivity extends AppCompatActivity {

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
                Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (loginViewModel.user.getValue().getPwd().isEmpty()) {
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        });
    }
}