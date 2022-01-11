package com.llw.mvvm.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
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
public class PictureViewModel extends BaseViewModel {

    private final PictureRepository pictureRepository;
    public LiveData<List<WallPaper>> wallPaper;

    @ViewModelInject
    PictureViewModel(PictureRepository pictureRepository){
        this.pictureRepository = pictureRepository;
    }

    public void getWallPaper() {
        failed = pictureRepository.failed;
        wallPaper = pictureRepository.getWallPaper();
    }
}
