//package com.markata.ganesh_hs.di.dagger
//
//import android.content.Context
//import com.markata.ganesh_hs.common.AppApplication
//import com.markata.ganesh_hs.common.TestApplication
//import dagger.BindsInstance
//import dagger.Component
//import dagger.android.AndroidInjector
//import dagger.android.support.AndroidSupportInjectionModule
//import javax.inject.Singleton
//
//@Singleton
//@Component(modules = [NetworkModule::class, AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class, RepoModule::class, ViewModelModule::class])
//interface AppComponent : AndroidInjector<TestApplication> {
//    @Component.Factory
//    interface Factory {
//        fun create(@BindsInstance appApplicationContext: Context): AppComponent
//    }
//}