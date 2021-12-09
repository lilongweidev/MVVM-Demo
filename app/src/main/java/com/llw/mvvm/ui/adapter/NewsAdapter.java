package com.llw.mvvm.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ItemNewsBinding;
import com.llw.mvvm.model.NewsResponse;
import com.llw.mvvm.model.WallPaperResponse;
import com.llw.mvvm.ui.activity.PictureViewActivity;
import com.llw.mvvm.ui.activity.WebActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 新闻列表适配器
 *
 * @author llw
 */
public class NewsAdapter extends BaseQuickAdapter<NewsResponse.ResultBean.DataBean, BaseDataBindingHolder<ItemNewsBinding>> {

    public NewsAdapter(@Nullable List<NewsResponse.ResultBean.DataBean> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemNewsBinding> bindingHolder, NewsResponse.ResultBean.DataBean dataBean) {
        ItemNewsBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setNews(dataBean);
            binding.setOnClick(new ClickBinding());
            binding.executePendingBindings();
        }
    }

    public static class ClickBinding {
        public void itemClick(NewsResponse.ResultBean.DataBean dataBean, View view) {
            if("1".equals(dataBean.getIs_content())){
                Intent intent = new Intent(view.getContext(), WebActivity.class);
                intent.putExtra("uniquekey", dataBean.getUniquekey());
                view.getContext().startActivity(intent);
            } else {
                Toast.makeText(view.getContext(), "没有详情信息", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
