package com.llw.mvvm.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.llw.mvvm.BaseApplication;
import com.llw.mvvm.api.ApiService;
import com.llw.mvvm.db.bean.News;
import com.llw.mvvm.model.NewsDetailResponse;
import com.llw.mvvm.model.NewsResponse;
import com.llw.mvvm.network.BaseObserver;
import com.llw.mvvm.network.NetworkApi;
import com.llw.mvvm.network.utils.DateUtil;
import com.llw.mvvm.utils.Constant;
import com.llw.mvvm.utils.MVUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * 对新闻详情数据进行处理
 *
 * @author llw
 * {@link com.llw.mvvm.viewmodels.VideoViewModel}
 */
@SuppressLint("CheckResult")
public class WebRepository {

    final MutableLiveData<NewsDetailResponse> newsDetail = new MutableLiveData<>();

    public final MutableLiveData<String> failed = new MutableLiveData<>();

    @Inject
    WebRepository(){}

    /**
     * 获取新闻详情数据
     * @param uniquekey 新闻ID
     * @return newsDetail
     */
    public MutableLiveData<NewsDetailResponse> getNewsDetail(String uniquekey) {
        NetworkApi.createService(ApiService.class, 2).
                newsDetail(uniquekey).compose(NetworkApi.applySchedulers(new BaseObserver<NewsDetailResponse>() {
            @Override
            public void onSuccess(NewsDetailResponse newsDetailResponse) {
                if (newsDetailResponse.getError_code() == 0) {
                    newsDetail.setValue(newsDetailResponse);
                } else {
                    failed.postValue(newsDetailResponse.getReason());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                failed.postValue("NewsDetail Error: " + e.toString());
            }
        }));
        return newsDetail;
    }
}
