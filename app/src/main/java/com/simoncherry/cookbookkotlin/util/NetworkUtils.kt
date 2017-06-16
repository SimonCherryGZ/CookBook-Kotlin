package com.simoncherry.cookbookkotlin.util

import android.content.Context
import android.net.ConnectivityManager
import com.simoncherry.cookbookkotlin.MyApplication

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/06/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class NetworkUtils {

    companion object {
        fun isNetworkConnected(): Boolean {
            val connectivityManager: ConnectivityManager = MyApplication.instance.applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null
        }
    }
}