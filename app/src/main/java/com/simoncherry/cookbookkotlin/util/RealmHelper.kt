package com.simoncherry.cookbookkotlin.util

import com.simoncherry.cookbookkotlin.model.MobCategory
import com.simoncherry.cookbookkotlin.model.RealmCategory
import com.simoncherry.cookbookkotlin.model.RealmCollection
import io.realm.Realm
import io.realm.RealmResults
import java.util.concurrent.atomic.AtomicLong

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
            val realmCategory = RealmCategory(mobCategory.ctgId, mobCategory.name, mobCategory.parentId, isChild, mobCategory.isSelected)
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
            val mobCategory = MobCategory(realmCategory.ctgId, realmCategory.name, realmCategory.parentId, realmCategory.isSelected)
            return mobCategory
        }

        fun createCollection(realm: Realm, realmCollection: RealmCollection) {
            realm.executeTransaction { realm ->
                val maxId = realm.where(RealmCollection::class.java).max("id")
                val primaryKeyValue = AtomicLong(maxId?.toLong() ?: 0)
                realmCollection.id = primaryKeyValue.incrementAndGet()
                realm.copyToRealm(realmCollection)
            }
        }

        fun retrieveCollectionByMenuId(realm: Realm, menuId: String): RealmResults<RealmCollection> {
            return realm.where(RealmCollection::class.java)
                    .equalTo("menuId", menuId)
                    .findAll()
        }

        fun deleteCollectionByResult(realm: Realm, realmResults: RealmResults<*>) {
            realm.executeTransaction { realmResults.deleteAllFromRealm() }
        }
    }
}