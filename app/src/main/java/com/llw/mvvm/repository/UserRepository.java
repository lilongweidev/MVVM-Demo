package com.llw.mvvm.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.llw.mvvm.BaseApplication;
import com.llw.mvvm.db.bean.User;
import com.llw.mvvm.db.bean.Video;
import com.llw.mvvm.db.bean.WallPaper;
import com.llw.mvvm.model.VideoResponse;
import com.llw.mvvm.network.utils.DateUtil;
import com.llw.mvvm.utils.Constant;
import com.llw.mvvm.utils.MVUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 用户数据提供
 *
 * @author llw
 */
public class UserRepository {

    @Inject
    UserRepository() {}

    private static final String TAG = UserRepository.class.getSimpleName();
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public final MutableLiveData<String> failed = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        Flowable<List<User>> listFlowable = BaseApplication.getDb().userDao().getAll();
        CustomDisposable.addDisposable(listFlowable, users -> {
            if (users.size() > 0) {
                for (User user : users) {
                    if (user.getUid() == 1) {
                        userMutableLiveData.postValue(user);
                        break;
                    }
                }
            } else {
                failed.postValue("你还没有注册过吧，去注册吧！");
            }
        });
        return userMutableLiveData;
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    public void updateUser(User user) {
        Completable update = BaseApplication.getDb().userDao().update(user);
        CustomDisposable.addDisposable(update, () -> {
            Log.d(TAG, "updateUser: " + "保存成功");
            failed.postValue("200");
        });
    }

    /**
     * 保存用户
     */
    public void saveUser(User user) {
        Completable deleteAll = BaseApplication.getDb().userDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, () -> {
            //保存到数据库
            Completable insertAll = BaseApplication.getDb().userDao().insert(user);
            //RxJava处理Room数据存储
            CustomDisposable.addDisposable(insertAll, () -> failed.postValue("200"));
        });
    }

}
