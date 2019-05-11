package com.platzi.platzivideos.model.retrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer Object for PlayList, used in Retrofit api client to parse response.
 */
public class PlayListDTO {
    private final String nextPageToken;
    private final String prevPageToken;

    private final List<VideoDTO> items;

    public PlayListDTO() {
        items = new ArrayList<>();
        nextPageToken = "";
        prevPageToken = "";
    }

    public List<VideoDTO> getItems() {
        return items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }
}
