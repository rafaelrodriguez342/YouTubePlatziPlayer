package com.platzi.platzivideos.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Rafael on 8/08/16.
 */
@Entity
public class VideoState {

    @NonNull
    @PrimaryKey
    private String id = "";

    @ColumnInfo(name = "current_time")
    private int currentTime = 0;

    public VideoState(@NonNull String id, int currentTime) {
        this.id = id;
        this.currentTime = currentTime;
    }

    public String getId() {
        return id;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof VideoState)) return false;
        VideoState otherVideoState = (VideoState) other;
        return otherVideoState.id.equals(getId()) && otherVideoState.currentTime == getCurrentTime();
    }
}
