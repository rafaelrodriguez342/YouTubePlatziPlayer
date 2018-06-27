package com.platzi.platzivideos;

import com.platzi.platzivideos.di.components.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.HasActivityInjector;

/**
 * Created by Rafael on 8/08/16.
 */
public class ApplicationClass extends DaggerApplication implements HasActivityInjector {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
