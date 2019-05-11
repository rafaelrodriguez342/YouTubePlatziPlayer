package com.platzi.platzivideos;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.ui.VideoDetailActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class VideoDetailActivityTest {

    @Test
    public void testVideoDataShown() {
        Video video = new Video("https://i.ytimg.com/vi/UJSPLAThppY/mqdefault.jpg", "Test Video", "UJSPLAThppY");
        Intent intent = VideoDetailActivity.createNewIntent(InstrumentationRegistry.getTargetContext(), video);
        InstrumentationRegistry.getInstrumentation().startActivitySync(intent);
        onView(withId(R.id.youtube_fragment))
                .check(matches(isDisplayed()));
    }
}
