package com.llw.mvvm.ui.adapter;

import android.content.Intent;
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

/**
 * 笔记列表适配器
 *
 * @author llw
 */
public class NotebookAdapter extends BaseQuickAdapter<Notebook, BaseDataBindingHolder<ItemNotebookBinding>> {

    public NotebookAdapter(@Nullable List<Notebook> data) {
        super(R.layout.item_notebook, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemNotebookBinding> bindingHolder, Notebook notebook) {
        ItemNotebookBinding binding = bindingHolder.getDataBinding();
        if (binding != null) {
            binding.setNotebook(notebook);
            binding.setOnClick(new NotebookAdapter.ClickBinding());
            binding.executePendingBindings();
        }
    }

    public static class ClickBinding {
        public void itemClick(Notebook notebook, View view) {
            Intent intent = new Intent(view.getContext(), EditActivity.class);
            intent.putExtra("uid", notebook.getUid());
            view.getContext().startActivity(intent);
        }
    }
}
