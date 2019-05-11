package com.platzi.platzivideos.model.retrofit;

/**
 * Data transfer Object for ResourceId, used in Retrofit api client to parse response.
 */
public class ResourceIdDTO {
    private final String videoId;

    public ResourceIdDTO() {
        this.videoId = "";
    }

    public String getVideoId() {
        return videoId;
    }
}
