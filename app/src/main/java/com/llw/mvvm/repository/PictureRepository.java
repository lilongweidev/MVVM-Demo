package com.llw.mvvm.repository;

import androidx.lifecycle.MutableLiveData;

import com.llw.mvvm.BaseApplication;
import com.llw.mvvm.db.bean.WallPaper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * 获取PictureViewActivity所需数据
 *
 * @author llw
 * 作用于 {@link com.llw.mvvm.viewmodels.PictureViewModel}
 */
public class PictureRepository {

    private final MutableLiveData<List<WallPaper>> wallPaper = new MutableLiveData<>();

    public final MutableLiveData<String> failed = new MutableLiveData<>();

    @Inject
    PictureRepository(){}

    public MutableLiveData<List<WallPaper>> getWallPaper() {
        Flowable<List<WallPaper>> listFlowable = BaseApplication.getDb().wallPaperDao().getAll();
        CustomDisposable.addDisposable(listFlowable, wallPapers -> {
            if (wallPapers.size() > 0) {
                wallPaper.postValue(wallPapers);
            } else {
                failed.postValue("暂无数据");
            }
        });
        return wallPaper;
    }
}
