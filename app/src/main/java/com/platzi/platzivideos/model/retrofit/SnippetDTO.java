package com.platzi.platzivideos.model.retrofit;

public class SnippetDTO {
    private String title;
    private ThumbnailDTO thumbnails;
    private ResourceIdDTO resourceId;


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
