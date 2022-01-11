package com.llw.mvvm.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.llw.mvvm.model.User;
import com.llw.mvvm.repository.UserRepository;

import javax.inject.Inject;

/**
 * 登录页面ViewModel
 * @author llw
 */
public class LoginViewModel extends BaseViewModel {

    private final UserRepository userRepository;

    public MutableLiveData<User> user;

    public MutableLiveData<User> getUser(){
        if(user == null){
            user = new MutableLiveData<>();
        }
        return user;
    }

    @ViewModelInject
    LoginViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public LiveData<com.llw.mvvm.db.bean.User> localUser;

    public void getLocalUser(){
        localUser = userRepository.getUser();
        failed = userRepository.failed;
    }
}
