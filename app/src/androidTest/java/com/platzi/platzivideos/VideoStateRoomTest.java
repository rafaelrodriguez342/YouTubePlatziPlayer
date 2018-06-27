package com.platzi.platzivideos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.platzi.platzivideos.model.VideoState;

import com.platzi.platzivideos.repositories.database.VideoStateDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VideoStateRoomTest {

    private VideoStateDatabase videoStateDatabase;

    @Before
    public void setup() {
        videoStateDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), VideoStateDatabase.class).build();
    }

    @After
    public void closeDb() {
        videoStateDatabase.close();
    }

    @Test
    public void testVideoStateRetrieveAndInsertion() {
        final String testId = "testID";
        VideoState videoState = new VideoState("testID", 1000);
        videoStateDatabase.videoStateDao().insertAll(videoState);
        LiveData<VideoState> videoStateLiveData = videoStateDatabase.videoStateDao().findById(testId);
        videoStateLiveData.observeForever(DBVideoState -> {
            assert (videoState.equals(DBVideoState));
        });
    }
}
