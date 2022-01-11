package com.llw.mvvm.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ItemVideoBinding;
import com.llw.mvvm.model.VideoResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

/**
 * 视频列表适配器
 *
 * @author llw
 */
public class VideoAdapter extends BaseQuickAdapter<VideoResponse.ResultBean, BaseDataBindingHolder<ItemVideoBinding>> {

    public VideoAdapter(@Nullable List<VideoResponse.ResultBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemVideoBinding> bindingHolder, VideoResponse.ResultBean dataBean) {
        ItemVideoBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setVideo(dataBean);
            binding.setOnClick(new ClickBinding());
            binding.executePendingBindings();
        }
    }

    public static class ClickBinding {
        public void itemClick(@NotNull VideoResponse.ResultBean resultBean, View view) {
            if (resultBean.getShare_url() != null) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(resultBean.getShare_url())));
            } else {
                Toast.makeText(view.getContext(), "视频地址为空", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
