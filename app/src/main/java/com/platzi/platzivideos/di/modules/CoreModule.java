package com.platzi.platzivideos.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.platzi.platzivideos.ApplicationClass;
import com.platzi.platzivideos.PlatziViewModelFactory;
import com.platzi.platzivideos.model.retrofit.PlayListDTO;
import com.platzi.platzivideos.repositories.VideoRepository;
import com.platzi.platzivideos.repositories.VideoStateRepository;
import com.platzi.platzivideos.repositories.database.RoomVideoStateRepo;
import com.platzi.platzivideos.repositories.database.VideoStateDatabase;
import com.platzi.platzivideos.repositories.network.RetrofitApiClient;
import com.platzi.platzivideos.repositories.network.VideoApiClientRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CoreModule {
    private final static String BASE_YOUTUBE_URL = "https://www.googleapis.com/";

    @Provides
    Context provideApplicationContext(ApplicationClass applicationClass) {
        return applicationClass;
    }

    @Provides
    @Singleton
    PlayListDTO providePlatziList() {
        return new PlayListDTO();
    }

    @Provides
    @Singleton
    public RetrofitApiClient provideVideosRetrofitApiClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_YOUTUBE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        return retrofit.create(RetrofitApiClient.class);
    }

    @Provides
    @Singleton
    public ViewModelProvider.Factory provideViewModelFactory(PlatziViewModelFactory platziViewModelFactory) {
        return platziViewModelFactory;
    }

    @Provides
    @Singleton
    public VideoRepository provideVideosRepository(VideoApiClientRepository videoApiClientRepository) {
        return videoApiClientRepository;
    }

    @Provides
    @Singleton
    public VideoStateRepository provideVideoStateRepository(RoomVideoStateRepo roomVideoStateRepo) {
        return roomVideoStateRepo;
    }

    @Provides
    @Singleton
    public VideoStateDatabase provideVideoStateDatabase(Context context) {
        return Room.databaseBuilder(context, VideoStateDatabase.class, "video_state").build();
    }
}
