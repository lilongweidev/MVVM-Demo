package com.llw.mvvm.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.db.bean.User;
import com.llw.mvvm.model.BiYingResponse;
import com.llw.mvvm.model.WallPaperResponse;
import com.llw.mvvm.repository.MainRepository;
import com.llw.mvvm.repository.UserRepository;
import com.llw.mvvm.ui.activity.MainActivity;


/**
 * 主页面ViewModel
 *
 * @author llw
 * {@link MainActivity}
 */
public class MainViewModel extends BaseViewModel {

    public LiveData<BiYingResponse> biying;

    public LiveData<WallPaperResponse> wallPaper;

    private final MainRepository mainRepository;

    @ViewModelInject
    MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public void getBiying() {
        failed = mainRepository.failed;
        biying = mainRepository.getBiYing();
    }

    public void getWallPaper() {
        failed = mainRepository.failed;
        wallPaper = mainRepository.getWallPaper();
    }
}
