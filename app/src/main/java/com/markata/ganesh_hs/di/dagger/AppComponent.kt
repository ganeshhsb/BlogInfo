package com.markata.ganesh_hs.di.dagger

import android.content.Context
import com.markata.ganesh_hs.common.AppApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


// restful ->Repo-> task ->viewmodel
@Singleton
@Component(modules = [NetworkModule::class,
    RepoModule::class,
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<AppApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appApplicationContext: Context): AppComponent
    }
}