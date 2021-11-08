package com.llw.mvvm;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import androidx.room.Room;
import com.llw.mvvm.db.AppDatabase;
import com.llw.mvvm.network.NetworkApi;
import com.llw.mvvm.utils.MVUtils;
import com.llw.mvvm.utils.SPUtils;
import com.tencent.mmkv.MMKV;

/**
 * 自定义 Application
 * @author llw
 */
public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    //数据库
    public static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化网络框架
        NetworkApi.init(new NetworkRequiredInfo(this));
        //MMKV初始化
        MMKV.initialize(this);
        //工具类初始化
        MVUtils.getInstance();
        //创建本地数据库
        db = AppDatabase.getInstance(this);
    }

    public static Context getContext() {
        return context;
    }

    public static AppDatabase getDb(){
        return db;
    }
}
