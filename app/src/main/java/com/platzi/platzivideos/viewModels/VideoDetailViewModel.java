package com.platzi.platzivideos.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.model.VideoInfo;
import com.platzi.platzivideos.model.VideoState;
import com.platzi.platzivideos.repositories.VideoStateRepository;

import javax.inject.Inject;

/**
 * View Model Class for Video Detail.
 */
public class VideoDetailViewModel extends ViewModel {
    private VideoStateRepository videoStateRepository;
    private MutableLiveData<Video> videoLiveData = new MutableLiveData<>();
    private LiveData<VideoState> videoStateLiveData = Transformations.switchMap(videoLiveData, input -> videoStateRepository.getVideoState(input.getId()));
    private LiveData<VideoInfo> videoInfoResultLiveData = Transformations.map(videoStateLiveData, input -> new VideoInfo(videoLiveData.getValue(), input));

    @Inject
    public VideoDetailViewModel(VideoStateRepository videoStateRepository) {
        this.videoStateRepository = videoStateRepository;
    }

    public void initWithVideo(Video video) {
        videoLiveData.setValue(video);
    }

    public void saveVideoState(int currentTimeInMilliseconds) {
        final Video video = videoLiveData.getValue();
        if (video != null) {
            videoStateRepository.saveVideoState(new VideoState(videoLiveData.getValue().getId(), currentTimeInMilliseconds));
        }
    }

    public LiveData<VideoInfo> getVideoInfoResultLiveData() {
        return videoInfoResultLiveData;
    }

    public void reloadVideo() {
        videoLiveData.setValue(videoLiveData.getValue());
    }
}
