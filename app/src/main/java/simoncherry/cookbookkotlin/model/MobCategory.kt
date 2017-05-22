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
data class MobCategory(
        var ctgId: String,
        var name: String,
        var parentId: String,
        var isSelected: Boolean
)