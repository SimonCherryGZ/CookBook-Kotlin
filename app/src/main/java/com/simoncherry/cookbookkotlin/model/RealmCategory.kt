package com.simoncherry.cookbookkotlin.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@PoKo open class RealmCategory(
        @PrimaryKey var ctgId: String,
        var name: String,
        var parentId: String,
        var isChild: Boolean,
        var isSelected: Boolean
) : RealmObject() {
    // The Kotlin compiler generates standard getters and setters.
    // Realm will overload them and code inside them is ignored.
    // So if you prefer you can also just have empty abstract methods.
}