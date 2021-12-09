package com.llw.mvvm.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.llw.mvvm.db.bean.User;
import com.llw.mvvm.db.bean.WallPaper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 用户数据操作
 * @author llw
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    Flowable<List<User>> getAll();

    @Update
    Completable update(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(User user);

    @Query("DELETE FROM user")
    Completable deleteAll();
}
