package com.simoncherry.cookbookkotlin.model

import java.util.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
data class MobCategoryResult(
        var categoryInfo: MobCategory?,
        var childs: ArrayList<MobCategoryChild1>?
)