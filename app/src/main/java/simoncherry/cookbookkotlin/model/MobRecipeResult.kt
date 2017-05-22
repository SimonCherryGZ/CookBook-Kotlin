package simoncherry.cookbookkotlin.model

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
data class MobRecipeResult(
        var curPage: Int,
        var total: Int,
        var list: List<MobRecipe>?
)