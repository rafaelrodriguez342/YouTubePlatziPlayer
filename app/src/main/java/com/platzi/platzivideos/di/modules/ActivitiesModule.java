package com.platzi.platzivideos.di.modules;

import com.platzi.platzivideos.ui.GalleryActivity;
import com.platzi.platzivideos.ui.VideoDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Module to declare activities to be injected.
 */
@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract GalleryActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract VideoDetailActivity bindDetailActivity();
}
