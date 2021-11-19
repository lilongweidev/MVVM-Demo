package com.llw.mvvm.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivityHomeBinding;

import org.jetbrains.annotations.NotNull;

/**
 * 主页面
 *
 * @author llw
 */
public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        //获取navController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.news_fragment:
                    binding.tvTitle.setText("头条新闻");
                    navController.navigate(R.id.news_fragment);
                    break;
                case R.id.video_fragment:
                    binding.tvTitle.setText("热门视频");
                    navController.navigate(R.id.video_fragment);
                    break;
                default:
            }
            return true;
        });
        //通过setupWithNavController将底部导航和导航控制器进行绑定
       // NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

    }


}