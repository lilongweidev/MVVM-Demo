package com.llw.mvvm.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.model.User;

/**
 * @author llw
 * @description MainViewModel
 * @date 2021/10/18 10:20
 */
public class MainViewModel extends ViewModel {

    public MutableLiveData<User> user;

    public MutableLiveData<User> getUser(){
        if(user == null){
            user = new MutableLiveData<>();
        }
        return user;
    }

}
