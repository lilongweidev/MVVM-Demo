package com.llw.mvvm.ui.adapter;

import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ItemNotebookBinding;
import com.llw.mvvm.db.bean.Notebook;
import com.llw.mvvm.ui.activity.EditActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

/**
 * 笔记列表适配器
 *
 * @author llw
 */
public class NotebookAdapter extends BaseQuickAdapter<Notebook, BaseDataBindingHolder<ItemNotebookBinding>> {

    //是否批量删除
    private boolean isBatchDeletion;

    /**
     * 设置批量删除
     */
    public void setBatchDeletion(boolean batchDeletion) {
        isBatchDeletion = batchDeletion;
    }

    public NotebookAdapter(@Nullable List<Notebook> data) {
        super(R.layout.item_notebook, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemNotebookBinding> bindingHolder, Notebook notebook) {
        ItemNotebookBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setNotebook(notebook);
            binding.setIsBatchDeletion(isBatchDeletion);
            binding.executePendingBindings();
        }
    }
}
