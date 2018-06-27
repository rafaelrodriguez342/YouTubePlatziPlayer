package com.platzi.platzivideos.model;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

public class VideoInfoListResult {

    private LiveData<PagedList<Video>> videos;

    public VideoInfoListResult(LiveData<PagedList<Video>> videos) {
        this.videos = videos;
    }

    public LiveData<PagedList<Video>> getVideos() {
        return videos;
    }
}
