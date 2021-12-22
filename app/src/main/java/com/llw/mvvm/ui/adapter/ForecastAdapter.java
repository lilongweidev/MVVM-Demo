package com.llw.mvvm.ui.adapter;


import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ItemForecastBinding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * 天气预报适配器
 * @author llw
 */
public class ForecastAdapter extends BaseQuickAdapter<LocalDayWeatherForecast, BaseDataBindingHolder<ItemForecastBinding>> {

    public ForecastAdapter(@Nullable List<LocalDayWeatherForecast> data) {
        super(R.layout.item_forecast, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemForecastBinding> bindingHolder, LocalDayWeatherForecast localDayWeatherForecast) {
        ItemForecastBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setForecast(localDayWeatherForecast);
            binding.executePendingBindings();
        }
    }
}
