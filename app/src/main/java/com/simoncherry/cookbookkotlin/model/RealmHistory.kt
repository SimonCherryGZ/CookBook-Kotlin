package com.simoncherry.cookbookkotlin.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@PoKo open class RealmHistory(
        @PrimaryKey var id: Long,
        var ctgTitles: String?,
        var menuId: String?,
        var name: String?,
        var summary: String?,
        var ingredients: String?,
        var thumbnail: String?,
        var createTime: Date
) : RealmObject()