package com.platzi.platzivideos.di.components;

import com.platzi.platzivideos.ApplicationClass;
import com.platzi.platzivideos.di.modules.ActivitiesModule;
import com.platzi.platzivideos.di.modules.CoreModule;
import com.platzi.platzivideos.di.modules.ViewModelsModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {CoreModule.class, AndroidInjectionModule.class, ActivitiesModule.class, AndroidSupportInjectionModule.class, ViewModelsModule.class})
public interface AppComponent extends AndroidInjector<ApplicationClass> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<ApplicationClass> {}
}
