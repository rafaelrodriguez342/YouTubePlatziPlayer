package com.platzi.platzivideos.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Data Model object for the VideoState.
 */
@Entity
public class VideoState {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "current_time")
    private int currentTime;

    public VideoState(@NonNull String id, int currentTime) {
        this.id = id;
        this.currentTime = currentTime;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public int getCurrentTime() {
        return currentTime;
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
