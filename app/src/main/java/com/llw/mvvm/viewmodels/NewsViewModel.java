package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.model.NewsResponse;
import com.llw.mvvm.repository.NewsRepository;

/**
 * NewsFragment数据提供
 * @author llw
 * {@link com.llw.mvvm.ui.fragment.NewsFragment}
 */
public class NewsViewModel extends BaseViewModel {

    public LiveData<NewsResponse> news;

    public void getNews() {
        NewsRepository newsRepository = new NewsRepository();
        failed = newsRepository.failed;
        news = newsRepository.getNews();
    }
}