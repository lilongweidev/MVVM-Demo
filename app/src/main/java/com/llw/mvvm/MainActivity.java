package com.llw.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.llw.mvvm.databinding.ActivityMainBinding;
import com.llw.mvvm.model.User;
import com.llw.mvvm.viewmodels.MainViewModel;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding dataBinding;
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //数据绑定视图
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainViewModel = new MainViewModel();
        //Model → View
        User user = new User("admin", "123456");
        mainViewModel.getUser().setValue(user);
        //获取观察对象
        MutableLiveData<User> user1 = mainViewModel.getUser();
        user1.observe(this, user2 -> dataBinding.setViewModel(mainViewModel));

        dataBinding.btnLogin.setOnClickListener(v -> {
            if (mainViewModel.user.getValue().getAccount().isEmpty()) {
                Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mainViewModel.user.getValue().getPwd().isEmpty()) {
                Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        });
    }
}