package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.llw.mvvm.model.VideoResponse;
import com.llw.mvvm.repository.VideoRepository;

/**
 * VideoFragment数据提供
 * @author llw
 * {@link com.llw.mvvm.ui.fragment.VideoFragment}
 */
public class VideoViewModel extends ViewModel {

    public LiveData<String> failed;

    public LiveData<VideoResponse> video;

    public void getVideo() {
        VideoRepository videoRepository = new VideoRepository();
        failed = videoRepository.failed;
        video = videoRepository.getVideo();
    }
}