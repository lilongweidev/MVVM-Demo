package com.llw.mvvm.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.llw.mvvm.db.bean.User;
import com.llw.mvvm.repository.UserRepository;


/**
 * 注册页面数据提供
 * @author llw
 */
public class RegisterViewModel extends BaseViewModel {

    public MutableLiveData<User> user;


    public MutableLiveData<User> getUser(){
        if(user == null){
            user = new MutableLiveData<>();
        }
        return user;
    }

    /**
     * 注册
     */
    public void register() {
        UserRepository userRepository = new UserRepository();
        failed = userRepository.failed;
        user.getValue().setUid(1);
        Log.d("TAG", "register: "+new Gson().toJson(user.getValue()));
        userRepository.saveUser(user.getValue());
    }
}
