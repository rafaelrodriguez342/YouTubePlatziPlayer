package com.platzi.platzivideos.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rafael on 8/08/16.
 */
public class VideoState {
    private String currentVideo="";
    private int currentTime=0;

    public VideoState (JSONObject json){
        try {
            this.setCurrentTime(json.getInt("currentTime"));
            if(json.has("currentVideo")){
               this.currentVideo=json.getString("currentVideo");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public VideoState(String currentVideo,int currentTime){
        this.currentVideo=currentVideo;
        this.currentTime=currentTime;
    }

    public String getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(String currentVideo) {
        this.currentVideo = currentVideo;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
}
