package com.markata.ganesh_hs.ui.blog.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.markata.ganesh_hs.data.blog.repo.Blog
import com.markata.ganesh_hs.data.blog.repo.BlogRepository
import com.markata.ganesh_hs.data.blog.repo.IBlogRepository
import com.markata.ganesh_hs.di.dagger.AppViewModelFactory
import com.markata.ganesh_hs.di.dagger.ViewModelKey
import com.markata.ganesh_hs.domain.blog.BlogTask
import com.markata.ganesh_hs.domain.blog.IBlogTask
import com.markata.ganesh_hs.ui.blog.BlogFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.Single

@Module
class TestBlogModule {

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [OnboardingViewModel] class.
     */
    @Provides
    @IntoMap
    @ViewModelKey(BlogFragmentViewModel::class)
    fun bindOnboardingViewModel(viewModel: BlogFragmentViewModel): ViewModel {
        return viewModel
    }

    @Provides
    fun bindViewModelFactory(factory: AppViewModelFactory):
            ViewModelProvider.Factory {
        return factory
    }

    @Provides
    fun provideBlogTask(blogTask: BlogTask): IBlogTask {
        return blogTask
    }

    @Provides
    fun provideBlogRepository(blogRepository: BlogRepository): IBlogRepository {
        return getBlogRepository()
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