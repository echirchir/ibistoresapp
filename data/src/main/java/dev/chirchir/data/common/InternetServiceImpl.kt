package dev.chirchir.data.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dev.chirchir.domain.common.service.InternetService

class InternetServiceImpl(
    private val context: Context
): InternetService {
    override fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}