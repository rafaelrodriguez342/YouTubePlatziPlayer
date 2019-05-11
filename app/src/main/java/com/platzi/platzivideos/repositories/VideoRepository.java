package com.platzi.platzivideos.repositories;

import com.platzi.platzivideos.model.VideoListResult;

/**
 * Contract for Video Repositories.
 */
public interface VideoRepository {
    VideoListResult getVideoListInfo();
}
