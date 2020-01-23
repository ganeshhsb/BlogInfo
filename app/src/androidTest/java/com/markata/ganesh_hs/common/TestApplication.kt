package com.markata.ganesh_hs.common

import android.app.Application
import android.content.Context
import com.markata.ganesh_hs.data.blog.repo.Blog
import com.markata.ganesh_hs.data.blog.repo.IBlogRepository
import com.markata.ganesh_hs.di.dagger.DaggerAppComponent
import io.reactivex.Single
import org.koin.dsl.bind
import org.koin.dsl.module


val onlineNetworkChecker = module {
    single {
        object : INetworkChecker {
            override fun isDeviceOnline(context: Context?): Boolean {
                return true
            }

        }
    } bind INetworkChecker::class
}

val offlineNetworkChecker = module {
    single {
        object : INetworkChecker {
            override fun isDeviceOnline(context: Context?): Boolean {
                return false
            }

        }
    } bind INetworkChecker::class
}

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
    }
}