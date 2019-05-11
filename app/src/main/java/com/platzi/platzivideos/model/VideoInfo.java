package com.platzi.platzivideos.model;

/**
 * Data Model object for Video Info.
 */
public class VideoInfo {
    private Video video;
    private VideoState videoState;

    public VideoInfo(Video video, VideoState videoState) {
        this.video = video;
        this.videoState = videoState;
    }

    public Video getVideo() {
        return video;
    }

    public VideoState getVideoState() {
        return videoState;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof VideoInfo)) return false;
        VideoInfo otherVideoInfo = (VideoInfo) other;
        return otherVideoInfo.video.equals(getVideo()) && otherVideoInfo.videoState.equals(getVideoState());
    }
}
