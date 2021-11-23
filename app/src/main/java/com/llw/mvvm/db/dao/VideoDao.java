package com.llw.mvvm.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.llw.mvvm.db.bean.News;
import com.llw.mvvm.db.bean.Video;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 视频数据操作
 * @author llw
 */
@Dao
public interface VideoDao {

    @Query("SELECT * FROM video")
    Flowable<List<Video>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Video> videos);

    @Query("DELETE FROM video")
    Completable deleteAll();
}
