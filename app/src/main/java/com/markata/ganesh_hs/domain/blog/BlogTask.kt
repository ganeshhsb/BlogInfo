package com.markata.ganesh_hs.domain.blog

import com.markata.ganesh_hs.data.blog.repo.BlogRepository
import com.markata.ganesh_hs.data.blog.repo.IBlogRepository
import com.markata.ganesh_hs.ui.blog.di.PerFragment
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import javax.inject.Inject
import javax.inject.Singleton

interface IBlogTask {
    val CHAR_POSITION: Int
        get() = 10

    fun fetchBlogAndFind10thCharacter(): Single<Result<Char>>
    fun fetchBlogAndFindEvery10thCharacter(): Single<Result<String>>
    fun fetchBlogAndCountWords(): Single<Result<Int>>
}

//@PerFragment
class BlogTask @Inject constructor(private val blogRepository: IBlogRepository) : IBlogTask, KoinComponent {
    override fun fetchBlogAndFind10thCharacter(): Single<Result<Char>> {
        return blogRepository.fetchBlog().map { blog ->
            blog.getCharAt(CHAR_POSITION)
        }
    }

    override fun fetchBlogAndFindEvery10thCharacter(): Single<Result<String>> {
        return blogRepository.fetchBlog().map { blog ->
            blog.getEveryTenthChar()
        }
    }

    override fun fetchBlogAndCountWords(): Single<Result<Int>> {
        return blogRepository.fetchBlog().map { blog ->
            blog.countWords()
        }
    }
}