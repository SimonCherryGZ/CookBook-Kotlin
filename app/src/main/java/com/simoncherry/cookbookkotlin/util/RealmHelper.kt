package com.simoncherry.cookbookkotlin.util

import com.simoncherry.cookbookkotlin.model.MobCategory
import com.simoncherry.cookbookkotlin.model.RealmCategory
import io.realm.Realm

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class RealmHelper {

    private val TAG = RealmHelper::class.java.simpleName

    companion object {
        fun convertMobCategoryToRealmCategory(mobCategory: MobCategory, isChild: Boolean): RealmCategory {
            val realmCategory = RealmCategory(mobCategory.ctgId, mobCategory.parentId, mobCategory.name, isChild, mobCategory.isSelected)
            return realmCategory
        }

        fun saveCategoryToRealm(realm: Realm, categoryList: List<RealmCategory>) {
            for (category in categoryList) {
                val realmResults = realm.where(RealmCategory::class.java)
                        .equalTo("ctgId", category.ctgId).findAll()
                if (realmResults.size > 0) {
                    val old = realmResults.first()
                    category.isSelected = old.isSelected
                }
                realm.copyToRealmOrUpdate(category)
            }
        }

        fun convertRealmCategoryToMobCategory(realmCategory: RealmCategory): MobCategory {
            val mobCategory = MobCategory(realmCategory.ctgId, realmCategory.parentId, realmCategory.name, realmCategory.isSelected)
            return mobCategory
        }
    }
}