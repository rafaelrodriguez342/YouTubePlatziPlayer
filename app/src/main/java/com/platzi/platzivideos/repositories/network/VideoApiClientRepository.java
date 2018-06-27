package com.platzi.platzivideos.repositories.network;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.model.VideoInfoListResult;
import com.platzi.platzivideos.repositories.VideoRepository;

import javax.inject.Inject;

public class VideoApiClientRepository implements VideoRepository {

    private RetrofitApiClient retrofitApiClient;

    @Inject
    public VideoApiClientRepository(RetrofitApiClient retrofitApiClient) {
        this.retrofitApiClient = retrofitApiClient;
    }

    @Override
    public VideoInfoListResult getVideoListInfo() {
        RetrofitDataSourceFactory sourceFactory = new RetrofitDataSourceFactory(retrofitApiClient);
        LiveData<PagedList<Video>> livePagedList = new LivePagedListBuilder<>(sourceFactory, Integer.parseInt(RetrofitDataSource.YOUTUBE_REQUEST_MAX_RESULTS))
                .build();
        return new VideoInfoListResult(livePagedList);
    }
}
