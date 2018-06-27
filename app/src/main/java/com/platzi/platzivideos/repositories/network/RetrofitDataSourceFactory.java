package com.platzi.platzivideos.repositories.network;

import android.arch.paging.DataSource;

import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.model.retrofit.VideoDTO;

public class RetrofitDataSourceFactory extends DataSource.Factory<String, Video> {
    private RetrofitApiClient retrofitApiClient;

    public RetrofitDataSourceFactory(RetrofitApiClient retrofitApiClient) {
        this.retrofitApiClient = retrofitApiClient;
    }

    @Override
    public DataSource<String, Video> create() {
        return new RetrofitDataSource(retrofitApiClient);
    }
}
