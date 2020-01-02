package com.markata.ganesh_hs.common

import android.content.Context
import android.net.ConnectivityManager

interface INetworkChecker {
    fun isDeviceOnline(context: Context?): Boolean
}

class NetworkChecker : INetworkChecker {
    private val TAG = "NetworkChecker"

    override fun isDeviceOnline(context: Context?): Boolean {
        var isConnected = false
        val connectivityManager: ConnectivityManager? =
            context?.applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.activeNetworkInfo?.isConnected?.let {
            isConnected = it
        }
        return isConnected
    }
}