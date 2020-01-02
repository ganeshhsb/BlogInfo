package com.markata.ganesh_hs.data.blog.repo

import com.markata.ganesh_hs.common.RestfulAPIClient
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

// implement as interface
interface IBlogRepository {
    fun fetchBlog(): Single<Blog>
}

class BlogRepository : IBlogRepository, KoinComponent {
    private val restfulAPIClient: RestfulAPIClient by inject()
    override fun fetchBlog(): Single<Blog> {
        return restfulAPIClient.fetchBlog()
            .map { content ->
                Blog(content)
            }
    }
}