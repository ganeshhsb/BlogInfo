package com.markata.ganesh_hs.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.markata.ganesh_hs.common.BASE_URL
import com.markata.ganesh_hs.common.RestfulAPIClient
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val restfulAPIClientModule = module {
    single { gson() }
    single { gsonConverterFactory(get()) }
    single { retrofit(get(), get()) }
    single { providesRepoApiService(get()) }
}

fun providesRepoApiService(retrofit: Retrofit): RestfulAPIClient {
    return retrofit.create(RestfulAPIClient::class.java)
}


private fun retrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(gsonConverterFactory)
        .build()
}


private fun gson(): Gson {
    val gsonBuilder = GsonBuilder()
    return gsonBuilder.create()
}

private fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
    return GsonConverterFactory.create(gson)
}
