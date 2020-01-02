package com.markata.ganesh_hs.common

import android.app.Application
import android.content.Context
import com.markata.ganesh_hs.data.blog.repo.Blog
import com.markata.ganesh_hs.data.blog.repo.IBlogRepository
import com.markata.ganesh_hs.di.okHttpClientModule
import com.markata.ganesh_hs.di.picassoModule
import com.markata.ganesh_hs.di.restfulAPIClientModule
import com.markata.ganesh_hs.domain.blog.BlogTask
import com.markata.ganesh_hs.domain.blog.IBlogTask
import com.markata.ganesh_hs.ui.blog.BlogFragmentViewModel
import io.reactivex.Single
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
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
        startKoin {
            androidContext(this@TestApplication)

            val activityModule = module {
                single { BlogFragmentViewModel() }
                single { BlogTask() } bind IBlogTask::class
                single { getBlogRepository() } bind IBlogRepository::class
            }

//            val appModule = module { single { NetworkChecker() } bind INetworkChecker::class }

            modules(activityModule + okHttpClientModule + picassoModule + restfulAPIClientModule)
        }
    }

    private fun getBlogRepository(): IBlogRepository {
        return object : IBlogRepository {
            override fun fetchBlog(): Single<Blog> {
                val tenthChar = ' '
                val templateString = "qwertyuio$tenthChar"
                var content = ""
                for (i in 1..5) {
                    content += templateString
                }
                return Single.just(Blog(content))
            }
        }
    }
}