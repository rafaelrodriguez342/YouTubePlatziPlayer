package com.platzi.platzivideos.repositories.database;

import android.arch.lifecycle.LiveData;

import com.platzi.platzivideos.model.VideoState;
import com.platzi.platzivideos.repositories.VideoStateRepository;

import javax.inject.Inject;

/**
 * Room Repository for Video States.
 */
public class RoomVideoStateRepo implements VideoStateRepository {

    private VideoStateDao videoStateDao;

    @Inject
    public RoomVideoStateRepo(VideoStateDatabase videoStateDatabase) {
        videoStateDao = videoStateDatabase.videoStateDao();
    }

    @Override
    public void saveVideoState(VideoState... videoState) {
        new Thread(() -> videoStateDao.insertAll(videoState)).start();
    }

    @Override
    public LiveData<VideoState> getVideoState(String videoId) {
        return videoStateDao.findById(videoId);
    }
}
