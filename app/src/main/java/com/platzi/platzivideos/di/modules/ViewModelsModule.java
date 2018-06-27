package com.platzi.platzivideos.di.modules;

import android.arch.lifecycle.ViewModel;

import com.platzi.platzivideos.annotations.ViewModelKey;
import com.platzi.platzivideos.viewModels.MainActivityViewModel;
import com.platzi.platzivideos.viewModels.VideoDetailViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

import static com.platzi.platzivideos.PlatziViewModelFactory.VIEW_MODELS_HASH;

@Module
public class ViewModelsModule {

    @Provides
    @IntoMap
    @Named(VIEW_MODELS_HASH)
    @ViewModelKey(MainActivityViewModel.class)
    ViewModel bindMainViewModel(MainActivityViewModel mainActivityViewModel) {
        return mainActivityViewModel;
    }

    @Provides
    @IntoMap
    @Named(VIEW_MODELS_HASH)
    @ViewModelKey(VideoDetailViewModel.class)
    ViewModel bindDetailViewModel(VideoDetailViewModel videoDetailViewModel) {
        return videoDetailViewModel;
    }
}
