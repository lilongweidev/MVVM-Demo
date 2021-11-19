package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.db.bean.WallPaper;
import com.llw.mvvm.repository.PictureRepository;
import com.llw.mvvm.ui.activity.PictureViewActivity;

import java.util.List;

/**
 * PictureViewModel
 *
 * @author llw
 * 作用于 {@link PictureViewActivity}
 */
public class PictureViewModel extends ViewModel {

    public LiveData<List<WallPaper>> wallPaper;

    public void getWallPaper() {
        wallPaper = new PictureRepository().getWallPaper();
    }
}
