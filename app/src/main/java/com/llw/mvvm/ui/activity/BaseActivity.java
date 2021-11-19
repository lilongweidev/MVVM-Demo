package com.llw.mvvm.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * 基础Activity
 *
 * @author llw
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showMsg(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongMsg(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void jumpActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    protected void setStatusBar(Activity activity, boolean isLight) {
        View decor = getWindow().getDecorView();
        WindowInsetsControllerCompat controller = ViewCompat.getWindowInsetsController(decor);
        if(controller != null){
            boolean appearanceLightStatusBars = controller.isAppearanceLightStatusBars();
            Log.d("TAG", "setStatusBar: "+appearanceLightStatusBars);
            controller.setAppearanceLightStatusBars(isLight);
        }
    }

    protected void setStatusBar(boolean dark) {
        View decor = getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
