package com.simoncherry.cookbookkotlin.api

import com.simoncherry.cookbookkotlin.model.Constant
import com.simoncherry.cookbookkotlin.util.NetworkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

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
        //val MOB_API_SERCET = "e3c74a50deb7cd4503f3d27743ead9e8"
        val MOB_API_BASE_URL = "http://apicloud.mob.com/v1/cook/"

        fun getMobAPI(): MobAPI {
            val retrofit = Retrofit.Builder()
                    //.client(OkHttpClient())
                    .client(getOkHttpClient())
                    .baseUrl(MOB_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(MobAPI::class.java)
        }

        fun getCacheInterceptor(): Interceptor {
            val cacheInterceptor = Interceptor { chain ->
                var request = chain.request()
                if (!NetworkUtils.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build()
                }
                val response = chain.proceed(request)
                if (NetworkUtils.isNetworkConnected()) {
                    val maxAge = 0
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build()
                } else {
                    // 无网络时，设置超时为4周
                    val maxStale = 60 * 60 * 24 * 28
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build()
                }
                response
            }
            return cacheInterceptor
        }

        fun getOfflineInterceptor(): Interceptor {
            val interceptor = Interceptor { chain ->
                var request = chain.request()
                /**
                 * 未联网获取缓存数据
                 */
                if (!NetworkUtils.isNetworkConnected()) {
                    //在20秒缓存有效，此处测试用，实际根据需求设置具体缓存有效时间
                    val cacheControl = CacheControl.Builder()
                            .maxStale(30, TimeUnit.DAYS)
                            .build()
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build()
                }

                chain.proceed(request)
            }
            return interceptor
        }

        fun getOnlineInterceptor(): Interceptor {
            return Interceptor { chain ->
                val response = chain.proceed(chain.request())

                // re-write response header to force use of cache
                // 正常访问同一请求接口（多次访问同一接口），给30秒缓存，超过时间重新发送请求，否则取缓存数据
                val cacheControl = CacheControl.Builder()
                        .maxAge(3, TimeUnit.SECONDS)
                        .build()

                response.newBuilder()
                        .header("Cache-Control", cacheControl.toString())
                        .build()
            }
        }

        fun getOkHttpClient(): OkHttpClient{
            val cacheFile = File(Constant.PATH_CACHE)
            val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())

            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            //设置缓存
//            builder.addNetworkInterceptor(getCacheInterceptor())
//            builder.addInterceptor(getCacheInterceptor())

            .addInterceptor(getOfflineInterceptor())//离线
            .addNetworkInterceptor(getOnlineInterceptor())//在线

            builder.cache(cache)
            //设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS)
            builder.readTimeout(20, TimeUnit.SECONDS)
            builder.writeTimeout(20, TimeUnit.SECONDS)
            //错误重连
            builder.retryOnConnectionFailure(true)
            return builder.build()
        }
    }
}