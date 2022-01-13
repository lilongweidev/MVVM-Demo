package com.llw.mvvm.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivityAboutBinding;
import com.llw.mvvm.utils.APKVersionInfoUtils;

/**
 * 关于我们
 *
 * @author llw
 */
public class AboutActivity extends BaseActivity {

    /**
     * 博客个人主页
     */
    private final String CSDN = "https://llw-study.blog.csdn.net/";
    /**
     * 博客地址
     */
    private final String CSDN_BLOG_URL = "https://blog.csdn.net/qq_38436214/category_11482619.html";
    /**
     * 源码地址
     */
    private final String GITHUB_URL = "https://github.com/lilongweidev/MVVM-Demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        back(binding.toolbar);
        binding.tvVersion.setText(APKVersionInfoUtils.getVerName(context));
        binding.tvBlog.setOnClickListener(v -> jumpUrl(CSDN_BLOG_URL));
        binding.tvCode.setOnClickListener(v -> jumpUrl(GITHUB_URL));
        binding.tvCopyEmail.setOnClickListener(v -> copyEmail());
        binding.tvAuthor.setOnClickListener(v -> jumpUrl(CSDN));

    }

    /**
     * 跳转URL
     *
     * @param url 地址
     */
    private void jumpUrl(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private void copyEmail() {
        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", "lonelyholiday@qq.com");
        myClipboard.setPrimaryClip(myClip);
        showMsg("邮箱已复制");
    }
}