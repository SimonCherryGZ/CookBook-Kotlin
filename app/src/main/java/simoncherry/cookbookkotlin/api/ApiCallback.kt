package simoncherry.cookbookkotlin.api

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
interface ApiCallback {
    interface QueryCategoryCallback : BaseCallback {
        fun onQueryCategorySuccess(value: MobCategoryResult)
        fun onQueryCategoryEmpty()
    }

    interface QueryRecipeCallback : BaseCallback {
        fun onQueryRecipeSuccess(value: MobRecipeResult)
        fun onQueryRecipeEmpty()
    }

    interface QueryDetailCallback : BaseCallback {
        fun onQueryDetailSuccess(value: MobRecipe)
        fun onQueryDetailEmpty()
    }
}