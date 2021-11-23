package com.llw.mvvm.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.model.User;

/**
 * 登录页面ViewModel
 * @author llw
 */
public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<User> user;

    public MutableLiveData<User> getUser(){
        if(user == null){
            user = new MutableLiveData<>();
        }
        return user;
    }
}
