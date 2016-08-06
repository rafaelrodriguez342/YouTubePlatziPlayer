package com.platzi.platzivideos.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 3/08/16.
 */
public class PlayListPlatzi {
    private String nextPageToken="";
    private List<VideoPlatzi> items;

    public PlayListPlatzi(){
        items= new ArrayList<VideoPlatzi>();
    }

    public List<VideoPlatzi> getItems() {
        return items;
    }

    public void setItems(List<VideoPlatzi> items) {
        this.items = items;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void addNewVideos(JSONObject jsonObject){
        try {
            if(nextPageToken.equals("")){
                items = new ArrayList<>();
            }
            nextPageToken = jsonObject.getString("nextPageToken");
            JSONArray items = jsonObject.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                VideoPlatzi video = new VideoPlatzi(items.getJSONObject(i));
                getItems().add(video);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
