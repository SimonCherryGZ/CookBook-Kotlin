package com.simoncherry.cookbookkotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.model.MobCategoryResult
import com.simoncherry.cookbookkotlin.model.RealmCategory
import com.simoncherry.cookbookkotlin.mvp.contract.SplashContract
import com.simoncherry.cookbookkotlin.mvp.presenter.SplashPresenter
import com.simoncherry.cookbookkotlin.util.RealmHelper
import io.realm.Realm
import java.util.*

class SplashActivity : BaseActivity<SplashContract.View, SplashContract.Presenter>(), SplashContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter?.queryCategory()
    }

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initComponent() {
        mPresenter = SplashPresenter()
    }

    override fun onShowProgressBar() {
    }

    override fun onQueryEmpty() {
        onShowToast("查询不到任何菜谱...")
    }

    override fun onQueryError(msg: String) {
        onShowToast(msg)
    }

    override fun onQueryCategorySuccess(value: MobCategoryResult?) {
        handleCategoryResult(value)
    }

    private fun handleCategoryResult(result: MobCategoryResult?) {
        if (result != null) {
            val categoryList = ArrayList<RealmCategory>()
            // 第1层子类
            val child1 = result.childs
            if (child1 != null && child1.size > 0) {
                for ((categoryInfo, child2) in child1) {
                    categoryList.add(RealmHelper.convertMobCategoryToRealmCategory(categoryInfo, false))
                    // 第2层子类
                    if (child2 != null && child2.size > 0) {
                        for ((categoryInfo1) in child2) {
                            categoryList.add(RealmHelper.convertMobCategoryToRealmCategory(categoryInfo1, true))
                        }
                    }
                }
            }
            saveCategoryToRealm(categoryList)
        }
    }

    private fun saveCategoryToRealm(categoryList: List<RealmCategory>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync({ realm -> RealmHelper.saveCategoryToRealm(realm, categoryList) }, {
            realm.close()
            startMainActivity()
        }) { error ->
            realm.close()
            onShowToast(error.toString())
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
        finish()
    }
}
