package com.platzi.platzivideos;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.model.VideoInfoResult;
import com.platzi.platzivideos.model.VideoState;
import com.platzi.platzivideos.repositories.VideoStateRepository;
import com.platzi.platzivideos.viewModels.VideoDetailViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VideoDetailViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    VideoStateRepository videoStateRepository;

    @Mock
    Observer<VideoInfoResult> videoInfoObserver;

    private VideoDetailViewModel videoDetailViewModel;
    private Video video;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        videoDetailViewModel = new VideoDetailViewModel(videoStateRepository);
        video = new Video("https://i.ytimg.com/vi/UJSPLAThppY/mqdefault.jpg", "Test Video", "UJSPLAThppY");
    }

    @Test
    public void testInitWithVideo() {
        VideoInfoResult expectedVideoInfoResult = mockVideoInfoResult(0, video);
        videoDetailViewModel.getVideoInfoResultLiveData().observeForever(videoInfoObserver);
        videoDetailViewModel.initWithVideo(video);
        verify(videoInfoObserver).onChanged(expectedVideoInfoResult);
    }

    @Test
    public void testSaveVideoState() {
        videoDetailViewModel.initWithVideo(video);
        final int currentTime = 1000;
        videoDetailViewModel.saveVideoState(currentTime);
        verify(videoStateRepository).saveVideoState(new VideoState(video.getId(), currentTime));
    }

    @Test
    public void testReloadVideo() {
        VideoInfoResult expectedVideoInfoResult = mockVideoInfoResult(2000, video);
        videoDetailViewModel.getVideoInfoResultLiveData().observeForever(videoInfoObserver);
        videoDetailViewModel.initWithVideo(video);
        verify(videoInfoObserver).onChanged(expectedVideoInfoResult);
        videoDetailViewModel.reloadVideo();
        verify(videoInfoObserver).onChanged(expectedVideoInfoResult);
    }

    private VideoInfoResult mockVideoInfoResult(int currentTime, Video video) {
        VideoState videoState = new VideoState(video.getId(), currentTime);
        VideoInfoResult expectedVideoInfoResult = new VideoInfoResult(video, videoState);
        MutableLiveData<VideoState> videoStateLiveData = new MutableLiveData<>();
        videoStateLiveData.setValue(videoState);
        when(videoStateRepository.getVideoState(video.getId())).thenReturn(videoStateLiveData);
        return expectedVideoInfoResult;
    }
}
