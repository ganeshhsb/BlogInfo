package com.markata.ganesh_hs.di.dagger

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.markata.ganesh_hs.common.BASE_URL
import com.markata.ganesh_hs.common.RestfulAPIClient
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

@Module
class NetworkModule {

    @Provides
    fun okHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, 10 * 1000 * 1000) //10 MB
    }


    @Provides
    fun file(context: Context): File {
        val file = File(context.cacheDir, "HttpCache")
        file.mkdirs()
        return file
    }


    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d("HTTP LOGGER: ", message)
            })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun providesRepoApiService(retrofit: Retrofit): RestfulAPIClient {
        return retrofit.create(RestfulAPIClient::class.java)
    }

    // retrofit
    @Provides
    fun retrofit(
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

    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

}