package com.platzi.platzivideos.di.modules;

import com.platzi.platzivideos.ui.MainActivity;
import com.platzi.platzivideos.ui.VideoDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract VideoDetailActivity bindDetailActivity();
}
