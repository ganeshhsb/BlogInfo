package com.markata.ganesh_hs.di

import com.markata.ganesh_hs.data.blog.repo.BlogRepository
import com.markata.ganesh_hs.data.blog.repo.IBlogRepository
import com.markata.ganesh_hs.domain.blog.BlogTask
import com.markata.ganesh_hs.domain.blog.IBlogTask
import com.markata.ganesh_hs.ui.blog.BlogFragmentViewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val activityModule = module {
    single { BlogFragmentViewModel() }
    single { BlogTask() } bind IBlogTask::class
    single { BlogRepository() } bind IBlogRepository::class
}

