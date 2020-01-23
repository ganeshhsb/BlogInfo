package com.markata.ganesh_hs.ui.blog.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.markata.ganesh_hs.data.blog.repo.BlogRepository
import com.markata.ganesh_hs.data.blog.repo.IBlogRepository
import com.markata.ganesh_hs.di.dagger.AppViewModelFactory
import com.markata.ganesh_hs.di.dagger.ViewModelKey
import com.markata.ganesh_hs.domain.blog.BlogTask
import com.markata.ganesh_hs.domain.blog.IBlogTask
import com.markata.ganesh_hs.ui.blog.BlogFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BlogModule{

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [OnboardingViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(BlogFragmentViewModel::class)
    fun bindOnboardingViewModel(viewModel: BlogFragmentViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: AppViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    fun provideBlogTask(blogTask: BlogTask): IBlogTask

    @Binds
    fun provideBlogRepository(blogRepository: BlogRepository): IBlogRepository
}