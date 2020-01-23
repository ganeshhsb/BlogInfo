package com.markata.ganesh_hs.ui.blog.di

import com.markata.ganesh_hs.di.dagger.AppComponent
import com.markata.ganesh_hs.ui.blog.BlogFragment
import dagger.Component
import java.lang.annotation.Documented
import javax.inject.Scope

@Scope
@Documented
@kotlin.annotation.Retention
annotation class PerFragment

@PerFragment
@Component(dependencies = [AppComponent::class], modules = [TestBlogModule::class])
interface TestBlogComponent {
    fun inject(blogFragment: BlogFragment)
}