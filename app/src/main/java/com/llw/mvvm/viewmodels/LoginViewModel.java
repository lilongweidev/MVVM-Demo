package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.llw.mvvm.model.User;
import com.llw.mvvm.repository.UserRepository;

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

    public LiveData<com.llw.mvvm.db.bean.User> localUser;

    public void getLocalUser(){
        UserRepository userRepository = new UserRepository();
        localUser = userRepository.getUser();
        failed = userRepository.failed;
    }
}
