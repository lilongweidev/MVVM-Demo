package com.llw.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.llw.mvvm.adapter.WallPaperAdapter;
import com.llw.mvvm.databinding.ActivityMainBinding;
import com.llw.mvvm.model.WallPaperResponse;
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
        //必应网络请求
        mainViewModel.getBiying();
        //返回数据时更新ViewModel，ViewModel更新则xml更新
        mainViewModel.biying.observe(this, biYingImgResponse -> dataBinding.setViewModel(mainViewModel));

        initView();
        //热门壁纸 网络请求
        mainViewModel.getWallPaper();
        mainViewModel.wallPaper.observe(this, wallPaperResponse -> dataBinding.rv.setAdapter(new WallPaperAdapter(wallPaperResponse.getRes().getVertical())));
    }

    /**
     * 初始化
     */
    private void initView() {
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        dataBinding.rv.setLayoutManager(manager);
        //伸缩偏移量监听
        dataBinding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {//收缩时
                    dataBinding.toolbarLayout.setTitle("MVVM-Demo");
                    isShow = true;
                } else if (isShow) {//展开时
                    dataBinding.toolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }
}