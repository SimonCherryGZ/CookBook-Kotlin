package com.simoncherry.cookbookkotlin.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MobAPIService{
    companion object {
        val MOB_API_KEY = "1c7ba126d698a"
        val MOB_API_SERCET = "e3c74a50deb7cd4503f3d27743ead9e8"
        val MOB_API_BASE_URL = "http://apicloud.mob.com/v1/cook/"

        fun getMobAPI(): MobAPI {
            val retrofit = Retrofit.Builder()
                    .client(OkHttpClient())
                    .baseUrl("http://apicloud.mob.com/v1/cook/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(MobAPI::class.java)
        }
    }
}