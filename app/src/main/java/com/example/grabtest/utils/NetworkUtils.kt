package com.example.grabtest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkUtils {

    final val newsUrl = "https://newsapi.org/v2/top-headlines/"
    final val countryIN="in"
    final val key_country="country"
    final val apiKey="774c3f90d5ee4166bcb5719bd46d3bcd"
    final val PAGE="page"
    final val PAGE_SIZE="pageSize"


    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected

    }
}
