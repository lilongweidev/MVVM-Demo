package com.llw.mvvm.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ItemNewsBinding;
import com.llw.mvvm.model.NewsResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 新闻列表适配器
 * @author llw
 */
public class NewsAdapter extends BaseQuickAdapter<NewsResponse.ResultBean.DataBean, BaseDataBindingHolder<ItemNewsBinding>> {

    public NewsAdapter(@Nullable List<NewsResponse.ResultBean.DataBean> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemNewsBinding> bindingHolder, NewsResponse.ResultBean.DataBean dataBean) {
        ItemNewsBinding binding = bindingHolder.getDataBinding();
        binding.setNews(dataBean);
        binding.executePendingBindings();
    }
}
