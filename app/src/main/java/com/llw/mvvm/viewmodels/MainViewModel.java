package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.model.BiYingResponse;
import com.llw.mvvm.repository.MainRepository;

/**
 * 主页面ViewModel
 *
 * @author llw
 * @description MainViewModel
 */
public class MainViewModel extends ViewModel {

    public LiveData<BiYingResponse> biying;

    public void getBiying(){
        biying = new MainRepository().getBiYing();
    }

}
