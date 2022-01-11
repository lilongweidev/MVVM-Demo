package com.llw.mvvm.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

import com.llw.mvvm.model.NewsDetailResponse;
import com.llw.mvvm.repository.WebRepository;

/**
 * NewsFragment数据提供
 * @author llw
 * {@link com.llw.mvvm.ui.activity.WebActivity}
 */
public class WebViewModel extends BaseViewModel {

    private final WebRepository webRepository;
    public LiveData<NewsDetailResponse> newsDetail;

    @ViewModelInject
    WebViewModel(WebRepository webRepository){
        this.webRepository = webRepository;
    }

    public void getNewDetail(String uniquekey) {
        failed = webRepository.failed;
        newsDetail = webRepository.getNewsDetail(uniquekey);
    }
}