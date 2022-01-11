package com.llw.mvvm.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.llw.mvvm.R;
import com.llw.mvvm.databinding.VideoFragmentBinding;
import com.llw.mvvm.ui.adapter.NewsAdapter;
import com.llw.mvvm.ui.adapter.VideoAdapter;
import com.llw.mvvm.viewmodels.NewsViewModel;
import com.llw.mvvm.viewmodels.VideoViewModel;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 视频
 * @author llw
 */
@AndroidEntryPoint
public class VideoFragment extends BaseFragment {

    private VideoFragmentBinding binding;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.video_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        VideoViewModel mViewModel = new ViewModelProvider(this).get(VideoViewModel.class);

        //获取视频数据
        mViewModel.getVideo();
        binding.rv.setLayoutManager(new LinearLayoutManager(context));
        //数据刷新
        mViewModel.video.observe(context, videoResponse ->
                binding.rv.setAdapter(new VideoAdapter(videoResponse.getResult())));
        mViewModel.failed.observe(context, this::showMsg);
    }

}