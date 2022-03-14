package com.llw.mvvm.ui.activity;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivityEditBinding;
import com.llw.mvvm.db.bean.Notebook;
import com.llw.mvvm.viewmodels.EditViewModel;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 编辑日志页面
 *
 * @author llw
 */
@AndroidEntryPoint
@SuppressLint("NonConstantResourceId")
public class EditActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = EditActivity.class.getSimpleName();
    private ActivityEditBinding binding;

    private InputMethodManager inputMethodManager;

    private EditViewModel viewModel;

    private int uid;
    private Notebook mNotebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        viewModel = new ViewModelProvider(this).get(EditViewModel.class);
        setStatusBar(!isNight());
        back(binding.toolbar);

        initView();
    }

    private void initView() {
        //监听输入
        listenInput(binding.etTitle);
        listenInput(binding.etContent);
        binding.ivOk.setOnClickListener(this);
        binding.ivDelete.setOnClickListener(this);
        uid = getIntent().getIntExtra("uid", -1);
        if (uid == -1) {
            //新增时 获取焦点
            showInput();
        } else {
            //修改
            viewModel.queryById(uid);
            viewModel.notebook.observe(this, notebook -> {
                mNotebook = notebook;
                binding.setNotebook(mNotebook);
            });
            viewModel.failed.observe(this, result -> Log.e(TAG, result));
            binding.ivDelete.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 监听输入
     *
     * @param editText 输入框
     */
    private void listenInput(final AppCompatEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    binding.ivOk.setVisibility(View.VISIBLE);
                } else {
                    if (binding.etTitle.getText().length() == 0 && binding.etContent.getText().length() == 0) {
                        binding.ivOk.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 显示键盘
     */
    public void showInput() {
        binding.etContent.requestFocus();
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 隐藏键盘
     */
    public void dismiss() {
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(binding.etContent.getWindowToken(), 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_ok://提交
                if (uid == -1) {
                    viewModel.addNotebook(new Notebook(binding.etTitle.getText().toString(),
                            binding.etContent.getText().toString()));
                } else {
                    mNotebook.setTitle(binding.etTitle.getText().toString());
                    mNotebook.setContent(binding.etContent.getText().toString());
                    viewModel.updateNotebook(mNotebook);
                }
                finish();
                break;
            case R.id.iv_delete:
                viewModel.deleteNotebook(mNotebook);
                finish();
                break;
            default:break;
        }
    }
}