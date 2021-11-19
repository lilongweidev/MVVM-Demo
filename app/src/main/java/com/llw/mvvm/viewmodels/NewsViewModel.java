package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.model.NewsResponse;
import com.llw.mvvm.repository.NewsRepository;

/**
 * @author llw
 * {@link com.llw.mvvm.ui.fragment.NewsFragment}
 */
public class NewsViewModel extends ViewModel {

    public LiveData<String> failed;

    public LiveData<NewsResponse> news;

    public void getNews() {
        NewsRepository newsRepository = new NewsRepository();
        failed = newsRepository.failed;
        news = newsRepository.getNews();
    }
}