package com.llw.mvvm.viewmodels;

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

    public void getUser() {
        user = UserRepository.getInstance().getUser();
    }

    public void updateUser(User user) {
        UserRepository.getInstance().updateUser(user);
        failed = UserRepository.getInstance().failed;
        getUser();
    }
}
