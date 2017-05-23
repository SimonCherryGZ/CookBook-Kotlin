package com.simoncherry.cookbookkotlin.model

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
data class MobRecipe(
        var ctgIds: List<String>?,
        var ctgTitles: String?,
        var menuId: String?,
        var name: String?,
        var recipe: MobRecipeDetail?,
        var thumbnail: String
)