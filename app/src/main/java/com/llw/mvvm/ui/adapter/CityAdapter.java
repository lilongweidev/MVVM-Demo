package com.llw.mvvm.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ItemCityBinding;
import com.llw.mvvm.databinding.ItemNewsBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 城市适配器
 * @author llw
 */
public class CityAdapter extends BaseQuickAdapter<String, BaseDataBindingHolder<ItemCityBinding>> {

    public CityAdapter(@Nullable List<String> data) {
        super(R.layout.item_city, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemCityBinding> bindingHolder, String s) {
        ItemCityBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setCityName(s);
            binding.executePendingBindings();
        }
    }
}
