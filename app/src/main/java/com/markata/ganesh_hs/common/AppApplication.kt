package com.markata.ganesh_hs.common

import android.app.Application
import com.markata.ganesh_hs.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(activityModule + okHttpClientModule + picassoModule + restfulAPIClientModule+ appModule)
        }
    }
}