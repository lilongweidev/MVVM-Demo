package com.llw.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.llw.mvvm.databinding.ActivityMainBinding;
import com.llw.mvvm.viewmodels.MainViewModel;

/**
 * 主页面
 * @author llw
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding dataBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //数据绑定视图
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //网络请求
        mainViewModel.getBiying();
        //返回数据时更新ViewModel，ViewModel更新则xml更新
        mainViewModel.biying.observe(this, biYingImgResponse -> dataBinding.setViewModel(mainViewModel));
    }
}