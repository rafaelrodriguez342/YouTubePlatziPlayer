package com.platzi.platzivideos.model.retrofit;

/**
 * Created by Rafael on 3/08/16.
 */
public class VideoDTO {

    private final SnippetDTO snippet;


    // fields are set by Retrofit.

    public VideoDTO(){
      snippet = null;
    }

    public SnippetDTO getSnippet() {
        return snippet;
    }
}
