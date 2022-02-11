package com.llw.mvvm.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivityNotebookBinding;
import com.llw.mvvm.db.bean.Notebook;
import com.llw.mvvm.ui.adapter.NotebookAdapter;
import com.llw.mvvm.utils.Constant;
import com.llw.mvvm.utils.MVUtils;
import com.llw.mvvm.viewmodels.NotebookViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 记事本
 *
 * @author llw
 */
@AndroidEntryPoint
public class NotebookActivity extends BaseActivity {

    private static final String TAG = NotebookActivity.class.getSimpleName();
    private ActivityNotebookBinding binding;
    private NotebookViewModel viewModel;
    private boolean hasNotebook;
    //菜单Item
    private MenuItem itemViewType;

    @Inject
    MVUtils mvUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notebook);
        viewModel = new ViewModelProvider(this).get(NotebookViewModel.class);
        setStatusBar(true);
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
        back(binding.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getNotebooks();
        viewModel.notebooks.observe(this, notebooks -> {
            if (notebooks.size() > 0) {
                binding.rvNotebook.setLayoutManager(mvUtils.getInt(Constant.NOTEBOOK_VIEW_TYPE) == 1 ?
                        new GridLayoutManager(context, 2) : new LinearLayoutManager(context));
                binding.rvNotebook.setAdapter(new NotebookAdapter(notebooks));
                hasNotebook = true;
            } else {
                hasNotebook = false;
            }
            binding.setHasNotebook(hasNotebook);
        });
        viewModel.failed.observe(this, result -> Log.d(TAG, "onResume: " + result));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notebook_settings, menu);
        itemViewType = menu.findItem(R.id.item_view_type).setTitle(mvUtils.getInt(Constant.NOTEBOOK_VIEW_TYPE) == 1 ? "列表视图" : "宫格视图");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 0 是列表视图 1 是宫格视图
        int viewType = mvUtils.getInt(Constant.NOTEBOOK_VIEW_TYPE);
        if (item.getItemId() == R.id.item_view_type) {//视图方式
            if (viewType == 0) {
                viewType = 1;
                itemViewType.setTitle("列表视图");
                binding.rvNotebook.setLayoutManager(new GridLayoutManager(context, 2));
            } else {
                viewType = 0;
                itemViewType.setTitle("宫格视图");
                binding.rvNotebook.setLayoutManager(new LinearLayoutManager(context));
            }
            mvUtils.put(Constant.NOTEBOOK_VIEW_TYPE, viewType);
        } else if (item.getItemId() == R.id.item_batch_deletion) {//批量删除

        }
        return true;
    }

    /**
     * 去编辑
     */
    public void toEdit(View view) {
        jumpActivity(EditActivity.class);
    }
}