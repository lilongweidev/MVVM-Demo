package com.llw.mvvm.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.llw.mvvm.api.ApiService;
import com.llw.mvvm.model.BiYingResponse;
import com.llw.mvvm.model.NewsResponse;
import com.llw.mvvm.network.BaseObserver;
import com.llw.mvvm.network.NetworkApi;
import com.llw.mvvm.network.utils.KLog;

/**
 * 对新闻数据进行处理
 *
 * @author llw
 * {@link com.llw.mvvm.viewmodels.VideoViewModel}
 */
@SuppressLint("CheckResult")
public class NewsRepository {

    final MutableLiveData<NewsResponse> news = new MutableLiveData<>();

    public final MutableLiveData<String> failed = new MutableLiveData<>();

    public MutableLiveData<NewsResponse> getNews() {
        NetworkApi.createService(ApiService.class, 2)
                .news().compose(NetworkApi.applySchedulers(new BaseObserver<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse newsResponse) {
                if (newsResponse.getError_code() == 0) {
                    news.postValue(newsResponse);
                } else {
                    failed.postValue(newsResponse.getReason());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                failed.postValue("News Error: " + e.toString());
            }
        }));
        return news;
    }
}
