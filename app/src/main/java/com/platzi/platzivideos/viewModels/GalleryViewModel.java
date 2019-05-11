package com.platzi.platzivideos.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.model.VideoListResult;
import com.platzi.platzivideos.repositories.VideoRepository;

import javax.inject.Inject;

/**
 * ViewModel Class for GalleryActivity.
 */
public class GalleryViewModel extends ViewModel {

    private MutableLiveData<VideoListResult> repoResult = new MutableLiveData<>();
    private LiveData<PagedList<Video>> videoList = Transformations.switchMap(repoResult, VideoListResult::getVideos);

    @Inject
    public GalleryViewModel(VideoRepository videoRepository) {
        repoResult.setValue(videoRepository.getVideoListInfo());
    }

    public LiveData<PagedList<Video>> getVideoListLiveData() {
        return videoList;
    }
}
