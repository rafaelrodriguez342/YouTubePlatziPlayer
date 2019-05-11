package com.platzi.platzivideos.model.retrofit;

/**
 * Data transfer Object for Thumbnail, used in Retrofit api client to parse response.
 */
public class ThumbnailDTO {
    private final MediumDTO medium;

    public ThumbnailDTO() {
        this.medium = null;
    }

    public MediumDTO getMedium() {
        return medium;
    }
}
