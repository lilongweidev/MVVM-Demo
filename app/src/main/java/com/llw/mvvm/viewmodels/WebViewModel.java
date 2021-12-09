package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;

import com.llw.mvvm.model.NewsDetailResponse;
import com.llw.mvvm.repository.WebRepository;

/**
 * NewsFragment数据提供
 * @author llw
 * {@link com.llw.mvvm.ui.activity.WebActivity}
 */
public class WebViewModel extends BaseViewModel {

    public LiveData<NewsDetailResponse> newsDetail;

    public void getNewDetail(String uniquekey) {
        WebRepository webRepository = new WebRepository();
        failed = webRepository.failed;
        newsDetail = webRepository.getNewsDetail(uniquekey);
    }
}