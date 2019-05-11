package com.platzi.platzivideos.model;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

/**
 * Data Model object to containing Video List Result from a Repository.
 */
public class VideoListResult {

    private LiveData<PagedList<Video>> videos;

    public VideoListResult(LiveData<PagedList<Video>> videos) {
        this.videos = videos;
    }

    public LiveData<PagedList<Video>> getVideos() {
        return videos;
    }
}
