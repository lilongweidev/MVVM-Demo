package com.llw.mvvm.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

import com.llw.mvvm.db.bean.User;
import com.llw.mvvm.repository.UserRepository;

/**
 * HomeViewModel
 * @author llw
 * {@link com.llw.mvvm.ui.activity.HomeActivity}
 */
public class HomeViewModel extends BaseViewModel {

    public LiveData<User> user;

    public String defaultName = "初学者-Study";
    public String defaultIntroduction = "Android | Java";
    private final UserRepository userRepository;

    @ViewModelInject
    HomeViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void getUser() {
        user = userRepository.getUser();
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
        failed = userRepository.failed;
        getUser();
    }
}
