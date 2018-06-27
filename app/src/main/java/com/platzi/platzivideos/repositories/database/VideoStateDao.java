package com.platzi.platzivideos.repositories.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.platzi.platzivideos.model.VideoState;

@Dao
public interface VideoStateDao {

    @Query("SELECT * FROM videostate WHERE id == :id")
    LiveData<VideoState> findById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(VideoState... states);

}
