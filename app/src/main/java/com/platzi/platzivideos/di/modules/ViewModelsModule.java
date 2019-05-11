package com.platzi.platzivideos.di.modules;

import android.arch.lifecycle.ViewModel;

import com.platzi.platzivideos.annotations.ViewModelKey;
import com.platzi.platzivideos.viewModels.GalleryViewModel;
import com.platzi.platzivideos.viewModels.VideoDetailViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

import static com.platzi.platzivideos.utils.ApplicationViewModelFactory.VIEW_MODELS_HASH;

/**
 * Module to declare available view models.
 */
@Module
public class ViewModelsModule {

    @Provides
    @IntoMap
    @Named(VIEW_MODELS_HASH)
    @ViewModelKey(GalleryViewModel.class)
    ViewModel bindMainViewModel(GalleryViewModel galleryViewModel) {
        return galleryViewModel;
    }

    @Provides
    @IntoMap
    @Named(VIEW_MODELS_HASH)
    @ViewModelKey(VideoDetailViewModel.class)
    ViewModel bindDetailViewModel(VideoDetailViewModel videoDetailViewModel) {
        return videoDetailViewModel;
    }
}
