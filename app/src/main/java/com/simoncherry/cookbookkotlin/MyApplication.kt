package com.simoncherry.cookbookkotlin

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MyApplication : Application() {

    private val DB_NAME = "cookbookkotlin"

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
                .name(DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}