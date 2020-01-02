package com.markata.ganesh_hs.di

import com.markata.ganesh_hs.common.INetworkChecker
import com.markata.ganesh_hs.common.NetworkChecker
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { NetworkChecker() } bind INetworkChecker::class
}