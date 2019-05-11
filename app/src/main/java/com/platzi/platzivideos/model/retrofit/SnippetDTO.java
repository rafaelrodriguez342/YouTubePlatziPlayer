package com.platzi.platzivideos.model.retrofit;

/**
 * Data transfer Object for Snippet, used in Retrofit api client to parse response.
 */
public class SnippetDTO {
    private final String title;
    private final ThumbnailDTO thumbnails;
    private final ResourceIdDTO resourceId;

    public SnippetDTO() {
        this.title = "";
        this.thumbnails = null;
        this.resourceId = null;
    }

    public String getTitle() {
        return title;
    }

    public ThumbnailDTO getThumbnails() {
        return thumbnails;
    }

    public ResourceIdDTO getResourceId() {
        return resourceId;
    }
}
