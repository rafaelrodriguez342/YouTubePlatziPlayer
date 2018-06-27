package com.platzi.platzivideos.model.retrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 3/08/16.
 */
public class PlayListDTO {
    private String nextPageToken = "";
    private String prevPageToken = "";

    private List<VideoDTO> items;

    public PlayListDTO() {
        items = new ArrayList<>();
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
