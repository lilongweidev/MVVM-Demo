package com.llw.mvvm;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.llw.mvvm.network.NetworkApi;

/**
 * 自定义 Application
 * @author llw
 */
public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        NetworkApi.init(new NetworkRequiredInfo(this));
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
