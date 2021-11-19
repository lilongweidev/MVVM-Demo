package com.llw.mvvm.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.llw.mvvm.api.ApiService;
import com.llw.mvvm.model.NewsResponse;
import com.llw.mvvm.model.VideoResponse;
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
public class VideoRepository {

    final MutableLiveData<VideoResponse> video = new MutableLiveData<>();

    public final MutableLiveData<String> failed = new MutableLiveData<>();

    public MutableLiveData<VideoResponse> getVideo() {
        NetworkApi.createService(ApiService.class, 3)
                .video().compose(NetworkApi.applySchedulers(new BaseObserver<VideoResponse>() {
            @Override
            public void onSuccess(VideoResponse videoResponse) {
                if (videoResponse.getError_code() == 0) {
                    video.postValue(videoResponse);
                } else {
                    failed.postValue(videoResponse.getReason());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                failed.postValue("Video Error: " + e.toString());
            }
        }));
        return video;
    }
}
