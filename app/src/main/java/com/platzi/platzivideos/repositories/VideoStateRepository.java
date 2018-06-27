package com.platzi.platzivideos.repositories;

import android.arch.lifecycle.LiveData;

import com.platzi.platzivideos.model.VideoState;

public interface VideoStateRepository {

    void saveVideoState(VideoState... videoState);

    LiveData<VideoState> getVideoState(String videoId);
}
