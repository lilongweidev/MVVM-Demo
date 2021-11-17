package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.llw.mvvm.model.BiYingResponse;
import com.llw.mvvm.model.WallPaperResponse;
import com.llw.mvvm.repository.MainRepository;


/**
 * 主页面ViewModel
 *
 * @author llw
 * @description MainViewModel
 * {@link com.llw.mvvm.MainActivity}
 */
public class MainViewModel extends ViewModel {

    public LiveData<BiYingResponse> biying;

    public LiveData<WallPaperResponse> wallPaper;

    public void getBiying() {
        biying = new MainRepository().getBiYing();
    }

    public void getWallPaper() { wallPaper = new MainRepository().getWallPaper(); }

}
