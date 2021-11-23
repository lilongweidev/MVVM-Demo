package com.llw.mvvm.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.llw.mvvm.db.bean.News;
import com.llw.mvvm.db.bean.WallPaper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 新闻数据操作
 * @author llw
 */
@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    Flowable<List<News>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<News> news);

    @Query("DELETE FROM news")
    Completable deleteAll();
}
