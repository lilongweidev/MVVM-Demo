package com.llw.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.llw.mvvm.adapter.ImageAdapter;
import com.llw.mvvm.databinding.ActivityPictureViewBinding;
import com.llw.mvvm.db.bean.WallPaper;
import com.llw.mvvm.viewmodels.PictureViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 图片查看
 *
 * @author llw
 */
public class PictureViewActivity extends AppCompatActivity {

    private PictureViewModel viewModel;
    private ActivityPictureViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picture_view);
        viewModel = new ViewModelProvider(this).get(PictureViewModel.class);
        String img = getIntent().getStringExtra("img");
        //获取热门壁纸数据
        viewModel.getWallPaper();
        viewModel.wallPaper.observe(this, wallPapers -> {
            binding.vp.setAdapter(new ImageAdapter(wallPapers));
            for (int i = 0; i < wallPapers.size(); i++) {
                if (img == null) {
                    return;
                }
                if (wallPapers.get(i).getImg().equals(img)) {
                    binding.vp.setCurrentItem(i,false);
                    break;
                }
            }
        });
    }
}