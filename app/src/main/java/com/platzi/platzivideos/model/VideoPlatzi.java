package com.platzi.platzivideos.model;
import android.content.Context;

import com.platzi.platzivideos.database.SqlVideoStatesHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rafael on 3/08/16.
 */
public class VideoPlatzi {
    private String urlImage;
    private String title;
    private String id;


    public VideoPlatzi(JSONObject json){
        try {
            JSONObject snippet =json.getJSONObject("snippet");
            JSONObject resource =snippet.getJSONObject("resourceId");

            id=resource.getString("videoId");
            title=snippet.getString("title");
            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
            JSONObject medium = thumbnails.getJSONObject("medium");
            urlImage=medium.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VideoState getVideoState(Context context) {
        SqlVideoStatesHandler db = new SqlVideoStatesHandler(context);
        return db.getVideoState(getId());
    }

    public void saveVideoState(VideoState videoState,Context context) {
        SqlVideoStatesHandler db = new SqlVideoStatesHandler(context);
        db.addVideoState(videoState,getId());
    }
}
