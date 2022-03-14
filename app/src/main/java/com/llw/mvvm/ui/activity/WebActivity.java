package com.llw.mvvm.ui.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivityWebBinding;
import com.llw.mvvm.viewmodels.WebViewModel;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 网页Activity
 * @author llw
 */
@AndroidEntryPoint
public class WebActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebBinding binding = ActivityWebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WebViewModel viewModel = new ViewModelProvider(this).get(WebViewModel.class);
        binding.webView.setWebViewClient(client);
        setStatusBar(!isNight());
        // enable:true(日间模式)，enable：false（夜间模式）
        binding.webView.getSettingsExtension().setDayOrNight(!isNight());
        // 在调用TBS初始化、创建WebView之前进行如下配置
        String uniquekey = getIntent().getStringExtra("uniquekey");
        if (uniquekey != null) {
            viewModel.getNewDetail(uniquekey);
            viewModel.newsDetail.observe(context, newsDetailResponse ->
                    binding.webView.loadUrl(newsDetailResponse.getResult().getDetail().getUrl()));
            viewModel.failed.observe(context, this::showMsg);
        }
    }

    private final WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView webview,
                                              com.tencent.smtt.export.external.interfaces.HttpAuthHandler httpAuthHandlerhost, String host,
                                              String realm) {
            boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            System.out.println("***********onReceivedError ************");
            super.onReceivedError(webView, i, s, s1);
        }

        @Override
        public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
            System.out.println("***********onReceivedHttpError ************");
            super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        }
    };
}