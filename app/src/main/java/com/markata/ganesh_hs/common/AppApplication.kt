package com.markata.ganesh_hs.common

import android.app.Application
import com.markata.ganesh_hs.di.dagger.AppComponent
import com.markata.ganesh_hs.di.dagger.DaggerAppComponent

open class AppApplication : Application() {
    protected var appComponent1:AppComponent? = null
    override fun onCreate() {
        super.onCreate()
         appComponent1 = DaggerAppComponent.factory().create(this)
    }

    open fun getAppComponent(): AppComponent? {
        return appComponent1
    }

}