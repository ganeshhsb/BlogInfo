package com.markata.ganesh_hs.common

import android.content.Context
import com.markata.ganesh_hs.ui.blog.di.DaggerTestAppComponent
import org.koin.dsl.bind
import org.koin.dsl.module


val onlineNetworkChecker = object : INetworkChecker {
    override fun isDeviceOnline(context: Context?): Boolean {
        return true
    }
}

val offlineNetworkChecker = object : INetworkChecker {
    override fun isDeviceOnline(context: Context?): Boolean {
        return false
    }
}

class TestApplication : AppApplication() {
    override fun onCreate() {
        super.onCreate()
        appComponent1 = DaggerTestAppComponent.factory().create(this)
    }
}