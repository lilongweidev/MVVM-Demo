package com.llw.mvvm.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.llw.mvvm.db.bean.Image;
import com.llw.mvvm.db.bean.Notebook;
import com.llw.mvvm.db.bean.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 笔记数据操作
 *
 * @author llw
 */
@Dao
public interface NotebookDao {

    @Query("SELECT * FROM notebook")
    Flowable<List<Notebook>> getAll();

    @Query("SELECT * FROM notebook WHERE uid=:uid")
    Flowable<Notebook> findById(int uid);

    // ||相当于+号
    @Query("SELECT * FROM notebook WHERE title LIKE '%' || :input || '%' OR content LIKE '%' || :input || '%' ")
    Flowable<List<Notebook>> searchNotebook(String input);

    @Update
    Completable update(Notebook notebook);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Notebook notebook);

    @Delete
    Completable delete(Notebook... notebook);

}
