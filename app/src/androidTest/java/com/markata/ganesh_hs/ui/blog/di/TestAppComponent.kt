package com.markata.ganesh_hs.ui.blog.di

import android.content.Context
import com.markata.ganesh_hs.di.dagger.AppComponent
import com.markata.ganesh_hs.di.dagger.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// restful ->Repo-> task ->viewmodel
@Singleton
@Component(
    modules = [
        TestAppModule::class
    ]
)
interface TestAppComponent:AppComponent {
    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }

    //override fun getRestfulAPIClient(): RestfulAPIClient
}