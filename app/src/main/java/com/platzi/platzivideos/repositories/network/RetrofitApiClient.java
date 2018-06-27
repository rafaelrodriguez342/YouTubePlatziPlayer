package com.platzi.platzivideos.repositories.network;

import com.platzi.platzivideos.model.retrofit.PlayListDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApiClient {

    @GET("/youtube/v3/playlistItems")
    Call<PlayListDTO> getVideoList(@Query("part") String part, @Query("maxResults") String maxResults,
                                   @Query("key") String key, @Query("fields") String fields,
                                   @Query("playlistId") String playlistId, @Query("pageToken") String pageToken);
}
