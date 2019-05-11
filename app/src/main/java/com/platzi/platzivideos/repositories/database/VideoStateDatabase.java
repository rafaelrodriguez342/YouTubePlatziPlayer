package com.platzi.platzivideos.repositories.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.platzi.platzivideos.model.VideoState;

/**
 * Room database class for VideoState
 */
@Database(entities = {VideoState.class}, version = 1)
public abstract class VideoStateDatabase extends RoomDatabase {
    public abstract VideoStateDao videoStateDao();
}
