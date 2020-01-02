package com.markata.ganesh_hs.di

import android.content.Context
import android.util.Log
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.io.File

val okHttpClientModule = module {
    single { cache(get()) }
    single { file(get()) }
    single { httpLoggingInterceptor() }
    single { okHttpClient(get(), get()) }
}

private fun okHttpClient(
    cache: Cache,
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .cache(cache)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}


private fun cache(cacheFile: File): Cache {
    return Cache(cacheFile, 10 * 1000 * 1000) //10 MB
}


private fun file(context: Context): File {
    val file = File(context.cacheDir, "HttpCache")
    file.mkdirs()
    return file
}


private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Log.d("HTTP LOGGER: ", message)
        })
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
}
