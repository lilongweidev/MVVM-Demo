package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.db.bean.WallPaper;
import com.llw.mvvm.repository.PictureRepository;

import java.util.List;

/**
 * PictureViewModel
 *
 * @author llw
 * 作用于 {@link com.llw.mvvm.PictureViewActivity}
 */
public class PictureViewModel extends ViewModel {

    public LiveData<List<WallPaper>> wallPaper;

    public void getWallPaper() {
        wallPaper = new PictureRepository().getWallPaper();
    }
}
