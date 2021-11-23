package com.llw.mvvm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 基础Activity
 *
 * @author llw
 */
public class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
    }

    protected void showMsg(CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongMsg(CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转页面
     * @param clazz 目标页面
     */
    protected void jumpActivity(final Class<?> clazz) {
        startActivity(new Intent(context, clazz));
    }

    /**
     * 跳转页面并关闭当前页面
     * @param clazz 目标页面
     */
    protected void jumpActivityFinish(final Class<?> clazz) {
        startActivity(new Intent(context, clazz));
        finish();
    }

    /**
     * 状态栏文字图标颜色
     * @param dark 深色 false 为浅色
     */
    protected void setStatusBar(boolean dark) {
        View decor = getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
