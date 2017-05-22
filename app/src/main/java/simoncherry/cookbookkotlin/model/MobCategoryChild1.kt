package simoncherry.cookbookkotlin.model

import java.util.ArrayList

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
data class MobCategoryChild1(
        var categoryInfo: MobCategory?,
        var childs: ArrayList<MobCategoryChild2>?
)