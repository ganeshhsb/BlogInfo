package com.markata.ganesh_hs.di.dagger

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.markata.ganesh_hs.BlogActivity
import com.markata.ganesh_hs.common.INetworkChecker
import com.markata.ganesh_hs.common.RestfulAPIClient
import com.markata.ganesh_hs.ui.blog.BlogFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// restful ->Repo-> task ->viewmodel
@Singleton
@Component(
    modules = [NetworkModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun getRestfulAPIClient(): RestfulAPIClient

    fun getNetworkchecker():INetworkChecker
}