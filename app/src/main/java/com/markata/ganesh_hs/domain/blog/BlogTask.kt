package com.markata.ganesh_hs.domain.blog

import com.markata.ganesh_hs.data.blog.repo.IBlogRepository
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

interface IBlogTask {
    val CHAR_POSITION: Int
        get() = 10

    fun fetchBlogAndFind10thCharacter(): Single<Result<Char>>
    fun fetchBlogAndFindEvery10thCharacter(): Single<Result<String>>
    fun fetchBlogAndCountWords(): Single<Result<Int>>
}

class BlogTask : IBlogTask, KoinComponent {

    private val blogRepository: IBlogRepository by inject()

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