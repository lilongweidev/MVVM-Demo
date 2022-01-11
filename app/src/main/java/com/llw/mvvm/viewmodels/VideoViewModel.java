package com.llw.mvvm.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.model.VideoResponse;
import com.llw.mvvm.repository.VideoRepository;

/**
 * VideoFragment数据提供
 * @author llw
 * {@link com.llw.mvvm.ui.fragment.VideoFragment}
 */
public class VideoViewModel extends BaseViewModel {


    public LiveData<VideoResponse> video;

    private final VideoRepository videoRepository;

    @ViewModelInject
    VideoViewModel(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void getVideo() {
        failed = videoRepository.failed;
        video = videoRepository.getVideo();
    }
}