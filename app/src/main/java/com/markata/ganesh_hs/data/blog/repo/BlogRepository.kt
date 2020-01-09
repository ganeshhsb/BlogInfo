package com.markata.ganesh_hs.data.blog.repo

import com.markata.ganesh_hs.common.RestfulAPIClient
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import javax.inject.Inject
import javax.inject.Singleton

// implement as interface
interface IBlogRepository {
    fun fetchBlog(): Single<Blog>
}

@Singleton
class BlogRepository @Inject constructor(private val restfulAPIClient: RestfulAPIClient) : IBlogRepository {

    override fun fetchBlog(): Single<Blog> {
        return restfulAPIClient.fetchBlog()
            .map { content ->
                Blog(content)
            }
    }
}