package com.platzi.platzivideos.repositories.network;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.platzi.platzivideos.model.Video;
import com.platzi.platzivideos.model.retrofit.PlayListDTO;
import com.platzi.platzivideos.model.retrofit.VideoDTO;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;

public class RetrofitDataSource extends PageKeyedDataSource<String, Video> {

    private static final String YOUTUBE_API_KEY = "AIzaSyBfi07_JiX6p_eNXCxiYGQNTOTlGb_MR1U";
    private static final String YOUTUBE_REQUEST_PLATZI_LIST_ID = "PLa28R7QEiMblmIF4y_ydG4eRr5K4TcVyk";
    public static final String YOUTUBE_REQUEST_MAX_RESULTS = "15";
    private static final String YOUTUBE_REQUEST_FIELDS = "items(contentDetails/videoId,snippet(thumbnails/medium,resourceId/videoId,title)),nextPageToken,prevPageToken";
    private static final String YOUTUBE_REQUEST_RESPONSE_TYPE = "snippet";

    private RetrofitApiClient retrofitApiClient;

    public RetrofitDataSource(RetrofitApiClient retrofitApiClient) {
        this.retrofitApiClient = retrofitApiClient;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Video> callback) {
        PlayListDTO playListPlatzi = getVideoListSync(params.requestedLoadSize, "");
        if (playListPlatzi != null) {
            List<Video> items = playListPlatzi.getItems().stream()
                    .filter(videoDTO -> !videoDTO.getSnippet().getTitle().equals("Private video"))
                    .map(videoDTO -> new Video(videoDTO.getSnippet().getThumbnails().getMedium().getUrl(), videoDTO.getSnippet().getTitle(), videoDTO.getSnippet().getResourceId().getVideoId()))
                    .collect(Collectors.toList());
            callback.onResult(items != null ? items : Collections.emptyList(), playListPlatzi.getPrevPageToken(), playListPlatzi.getNextPageToken());
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Video> callback) {
//ignored
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Video> callback) {
        PlayListDTO playListPlatzi = getVideoListSync(params.requestedLoadSize, params.key);
        if (playListPlatzi != null) {
            List<Video> items = playListPlatzi.getItems().stream()
                    .filter(videoDTO -> !videoDTO.getSnippet().getTitle().equals("Private video"))
                    .map(videoDTO -> new Video(videoDTO.getSnippet().getThumbnails().getMedium().getUrl(), videoDTO.getSnippet().getTitle(), videoDTO.getSnippet().getResourceId().getVideoId()))
                    .collect(Collectors.toList());
            callback.onResult(items != null ? items : Collections.emptyList(), playListPlatzi.getNextPageToken());
        }
    }

    private PlayListDTO getVideoListSync(int requestedLoadSize, String nextPageToken) {
        Call<PlayListDTO> request = retrofitApiClient.getVideoList(YOUTUBE_REQUEST_RESPONSE_TYPE, String.valueOf(requestedLoadSize), YOUTUBE_API_KEY, YOUTUBE_REQUEST_FIELDS, YOUTUBE_REQUEST_PLATZI_LIST_ID, nextPageToken);
        try {
            System.out.println(request.request().url());
            return request.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
