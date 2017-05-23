package com.simoncherry.cookbookkotlin.api

import com.simoncherry.cookbookkotlin.model.MobAPIResult
import com.simoncherry.cookbookkotlin.model.MobCategoryResult
import com.simoncherry.cookbookkotlin.model.MobRecipe
import com.simoncherry.cookbookkotlin.model.MobRecipeResult
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface MobAPI {
    @GET("category/query")
    fun queryCategory(@Query("key") key: String): Flowable<MobAPIResult<MobCategoryResult>>

    @GET("menu/search")
    fun queryRecipe(@QueryMap params: Map<String, String>): Flowable<MobAPIResult<MobRecipeResult>>

    @GET("menu/query")
    fun queryDetail(@QueryMap params: Map<String, String>): Flowable<MobAPIResult<MobRecipe>>
}