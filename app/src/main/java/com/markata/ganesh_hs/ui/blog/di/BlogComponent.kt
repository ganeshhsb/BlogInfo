package com.markata.ganesh_hs.ui.blog.di

import com.markata.ganesh_hs.di.dagger.AppComponent
import com.markata.ganesh_hs.ui.blog.BlogFragment
import dagger.Component
import javax.inject.Scope

@Scope
@MustBeDocumented
@kotlin.annotation.Retention
annotation class PerFragment

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [BlogModule::class])
interface BlogComponent {
    fun inject(blogFragment: BlogFragment)
}