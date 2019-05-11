package com.platzi.platzivideos.model.retrofit;

/**
 * Data transfer Object for Video, used in Retrofit api client to parse response.
 */
public class VideoDTO {

    private final SnippetDTO snippet;

    public VideoDTO() {
        snippet = null;
    }

    public SnippetDTO getSnippet() {
        return snippet;
    }
}
