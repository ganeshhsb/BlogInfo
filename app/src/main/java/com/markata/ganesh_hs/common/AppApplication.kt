package com.markata.ganesh_hs.common

import android.app.Application
import com.markata.ganesh_hs.di.*
import com.markata.ganesh_hs.di.dagger.AppComponent
import com.markata.ganesh_hs.di.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class AppApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidContext(this@AppApplication)
//            modules(activityModule + okHttpClientModule + picassoModule + restfulAPIClientModule+ appModule)
//        }
    }
}