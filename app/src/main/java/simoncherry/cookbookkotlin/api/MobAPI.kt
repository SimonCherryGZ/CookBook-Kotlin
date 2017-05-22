package simoncherry.cookbookkotlin.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import simoncherry.cookbookkotlin.model.MobAPIResult
import simoncherry.cookbookkotlin.model.MobCategoryResult
import simoncherry.cookbookkotlin.model.MobRecipe
import simoncherry.cookbookkotlin.model.MobRecipeResult

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