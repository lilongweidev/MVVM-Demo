package com.llw.mvvm.viewmodels;

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

    public LiveData<User> user;

    public void getBiying() {
        failed = MainRepository.getInstance().failed;
        biying = MainRepository.getInstance().getBiYing();
    }

    public void getWallPaper() {
        failed = MainRepository.getInstance().failed;
        wallPaper = MainRepository.getInstance().getWallPaper();
    }

    public void getUser() {
        user = new UserRepository().getUser();
    }

}
