package com.llw.mvvm.ui.adapter;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ItemImageBinding;
import com.llw.mvvm.db.bean.WallPaper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 图片适配器
 * @author llw
 */
public class ImageAdapter extends BaseQuickAdapter<WallPaper, BaseDataBindingHolder<ItemImageBinding>> {

    public ImageAdapter(@Nullable List<WallPaper> data) {
        super(R.layout.item_image, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemImageBinding> bindingHolder, WallPaper wallPaper) {
        if (wallPaper == null) {
            return;
        }
        ItemImageBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setWallPaper(wallPaper);
            binding.executePendingBindings();
        }
    }
}
