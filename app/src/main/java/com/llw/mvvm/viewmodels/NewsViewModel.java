package com.llw.mvvm.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
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

    private final NewsRepository newsRepository;

    @ViewModelInject
    public NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void getNews() {
        failed = newsRepository.failed;
        news = newsRepository.getNews();
    }
}