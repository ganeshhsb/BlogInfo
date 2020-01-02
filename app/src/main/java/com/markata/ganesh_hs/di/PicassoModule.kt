package com.markata.ganesh_hs.di

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import okhttp3.OkHttpClient
import com.squareup.picasso.Picasso
import org.koin.dsl.module


val picassoModule = module {
    single { picasso(get(), get()) }
    single { okHttp3Downloader(get()) }
}


private fun picasso(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso {
    return Picasso.Builder(context).downloader(okHttp3Downloader).build()
}


private fun okHttp3Downloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
    return OkHttp3Downloader(okHttpClient)
}
