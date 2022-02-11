package com.llw.mvvm.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivityNotebookBinding;
import com.llw.mvvm.db.bean.Notebook;
import com.llw.mvvm.ui.adapter.NotebookAdapter;
import com.llw.mvvm.viewmodels.NotebookViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 记事本
 * @author llw
 */
@AndroidEntryPoint
public class NotebookActivity extends BaseActivity {

    private static final String TAG = NotebookActivity.class.getSimpleName();
    private ActivityNotebookBinding binding;
    private NotebookViewModel viewModel;
    private boolean hasNotebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notebook);
        viewModel = new ViewModelProvider(this).get(NotebookViewModel.class);
        setStatusBar(true);
        back(binding.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getNotebooks();
        viewModel.notebooks.observe(this, notebooks -> {
            if (notebooks.size() > 0) {
                binding.rvNotebook.setLayoutManager(new LinearLayoutManager(context));
                binding.rvNotebook.setAdapter(new NotebookAdapter(notebooks));
                hasNotebook = true;
            } else {
                hasNotebook = false;
            }
            binding.setHasNotebook(hasNotebook);
        });
        viewModel.failed.observe(this, result -> Log.d(TAG, "onResume: "+ result));
    }

    /**
     * 去编辑
     */
    public void toEdit(View view) {
        jumpActivity(EditActivity.class);
    }
}