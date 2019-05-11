package com.platzi.platzivideos;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.model.retrofit.PlayListDTO;
import com.platzi.platzivideos.repositories.VideoRepository;
import com.platzi.platzivideos.repositories.network.RetrofitApiClient;
import com.platzi.platzivideos.repositories.network.VideoApiClientRepository;
import com.platzi.platzivideos.viewModels.GalleryViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import retrofit2.mock.Calls;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GalleryViewModelTest {

    private static final String TEST_VIDEOS_PATH = "videos.json";
    private static final String TEST_EMPTY_VIDEOS_PATH = "empty_videos.json";
    private static final String TEST_MIXED_VIDEOS_PATH = "mixed_videos.json";
    private static final String TEST_PRIVATE_VIDEOS_PATH = "private_videos.json";

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    RetrofitApiClient retrofitApiClient;

    private GalleryViewModel galleryViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VideoRepository videoRepository = new VideoApiClientRepository(retrofitApiClient);
        galleryViewModel = new GalleryViewModel(videoRepository);
    }

    @Test
    public void testLoadVideosWithVideoList() throws IOException {
        mockDataSource(TEST_VIDEOS_PATH);
        Observer<PagedList<Video>> listObserver = videos -> {
            assertNotNull(videos);
            assertEquals(44, videos.size());
        };
        galleryViewModel.getVideoListLiveData().observeForever(listObserver);
    }

    @Test
    public void testLoadVideosWithEmptyList() throws IOException {
        mockDataSource(TEST_EMPTY_VIDEOS_PATH);
        Observer<PagedList<Video>> listObserver = videos -> {
            assertNotNull(videos);
            assertTrue(videos.isEmpty());
        };
        galleryViewModel.getVideoListLiveData().observeForever(listObserver);
    }

    @Test
    public void testLoadVideosWithMixedList() throws IOException {
        mockDataSource(TEST_MIXED_VIDEOS_PATH);
        Observer<PagedList<Video>> listObserver = videos -> {
            assertNotNull(videos);
            assertEquals(10, videos.size());
        };
        galleryViewModel.getVideoListLiveData().observeForever(listObserver);
    }

    @Test
    public void testLoadVideosWithPrivateList() throws IOException {
        mockDataSource(TEST_PRIVATE_VIDEOS_PATH);
        Observer<PagedList<Video>> listObserver = videos -> {
            assertNotNull(videos);
            assertTrue(videos.isEmpty());
        };
        galleryViewModel.getVideoListLiveData().observeForever(listObserver);
    }

    private void mockDataSource(String fileName) throws IOException {
        Gson gson = new GsonBuilder().create();
        PlayListDTO playListDTO = gson.fromJson(SampleDataHelper.loadSampleDataFromLocalJSON(fileName), PlayListDTO.class);
        when(retrofitApiClient.getVideoList(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(Calls.response(playListDTO));
    }
}
