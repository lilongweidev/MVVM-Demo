package com.llw.mvvm.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ItemNewsBinding;
import com.llw.mvvm.databinding.ItemVideoBinding;
import com.llw.mvvm.model.NewsResponse;
import com.llw.mvvm.model.VideoResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 视频列表适配器
 * @author llw
 */
public class VideoAdapter extends BaseQuickAdapter<VideoResponse.ResultBean, BaseDataBindingHolder<ItemVideoBinding>> {

    public VideoAdapter(@Nullable List<VideoResponse.ResultBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemVideoBinding> bindingHolder, VideoResponse.ResultBean dataBean) {
        ItemVideoBinding binding = bindingHolder.getDataBinding();
        binding.setVideo(dataBean);
        binding.executePendingBindings();
    }
}
