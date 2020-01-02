package com.markata.ganesh_hs.common

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface RestfulAPIClient {
    @GET(LIFE_AS_AN_ENGINEER_BLOG)
    fun fetchBlog():Single<String>
}