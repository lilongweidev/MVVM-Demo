package com.llw.mvvm.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

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

/**
 * 视频
 * @author llw
 */
public class VideoFragment extends Fragment {

    private VideoFragmentBinding binding;
    private VideoViewModel mViewModel;

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
        mViewModel = new ViewModelProvider(this).get(VideoViewModel.class);

        //获取新闻数据
        mViewModel.getVideo();
        binding.rv.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //数据刷新
        mViewModel.video.observe(requireActivity(),videoResponse ->
                binding.rv.setAdapter(new VideoAdapter(videoResponse.getResult())));
        mViewModel.failed.observe(requireActivity(), failed -> Toast.makeText(requireActivity(), failed, Toast.LENGTH_SHORT).show());
    }

}