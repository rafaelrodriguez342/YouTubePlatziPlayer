package com.platzi.platzivideos.model;

public class VideoInfoResult {
    private Video video;
    private VideoState videoState;

    public VideoInfoResult(Video video, VideoState videoState) {
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
        if (!(other instanceof VideoInfoResult)) return false;
        VideoInfoResult otherVideoState = (VideoInfoResult) other;
        return otherVideoState.video.equals(getVideo()) && otherVideoState.videoState == getVideoState();
    }
}
