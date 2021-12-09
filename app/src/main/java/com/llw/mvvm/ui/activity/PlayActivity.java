package com.llw.mvvm.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.llw.mvvm.R;
import com.llw.mvvm.databinding.ActivityPlayBinding;

import cn.jzvd.Jzvd;

/**
 * 播放页面
 * @author llw
 */
public class PlayActivity extends BaseActivity {

    private static final String TAG = PlayActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlayBinding binding = DataBindingUtil.setContentView(context, R.layout.activity_play);
        setStatusBar(false);
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        Log.d(TAG, "onCreate: "+url);
        //binding.videoView.setVideoURI(Uri.parse(url));
//        binding.videoView.setVideoURI(Uri.parse("http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4"));
//        binding.videoView.start();
        binding.jzVideo.setUp("http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", title);
        binding.jzVideo.posterImageView.setImageURI(Uri.parse("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}