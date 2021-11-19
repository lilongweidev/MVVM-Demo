package com.llw.mvvm.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
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
import com.llw.mvvm.databinding.NewsFragmentBinding;
import com.llw.mvvm.model.NewsResponse;
import com.llw.mvvm.ui.adapter.NewsAdapter;
import com.llw.mvvm.viewmodels.NewsViewModel;

/**
 * 新闻
 * @author llw
 */
public class NewsFragment extends Fragment {

    private NewsViewModel mViewModel;
    private NewsFragmentBinding binding;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.news_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        //获取新闻数据
        mViewModel.getNews();
        binding.rv.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //数据刷新
        mViewModel.news.observe(requireActivity(),newsResponse ->
                binding.rv.setAdapter(new NewsAdapter(newsResponse.getResult().getData())));
        mViewModel.failed.observe(requireActivity(), failed -> Toast.makeText(requireActivity(), failed, Toast.LENGTH_SHORT).show());
    }

}