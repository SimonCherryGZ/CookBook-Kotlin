package com.simoncherry.cookbookkotlin.ui.activity

import android.animation.Animator
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kogitune.activity_transition.ActivityTransition
import com.kogitune.activity_transition.ExitActivityTransition
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.loadUrl
import com.simoncherry.cookbookkotlin.model.*
import com.simoncherry.cookbookkotlin.mvp.contract.DetailContract
import com.simoncherry.cookbookkotlin.mvp.presenter.DetailPresenter
import com.simoncherry.cookbookkotlin.ui.adapter.MethodAdapter
import com.simoncherry.cookbookkotlin.util.RealmHelper
import com.simoncherry.cookbookkotlin.util.SPUtils
import com.simoncherry.cookbookkotlin.util.StatusBarUtils
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class DetailActivity : BaseSwipeBackActivity<DetailContract.View, DetailContract.Presenter>(), DetailContract.View {

    private val TAG = DetailActivity::class.java.simpleName


    private lateinit var exitTransition: ExitActivityTransition

    private lateinit var mAdapter: MethodAdapter
    private lateinit var mData: MutableList<MobRecipeMethod>

    private lateinit var realm: Realm
    private lateinit var realmResults: RealmResults<RealmCollection>

    private lateinit var mobRecipe: MobRecipe
    private var recipeId: String = ""


    companion object {
        val KEY_RECIPE_ID = "recipeId"
        val KEY_THUMBNAIL = "thumbnail"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startEnterAnimation(savedInstanceState)
        init()
    }

    override fun getLayout(): Int {
        return R.layout.activity_detail
    }

    override fun initComponent() {
        mPresenter = DetailPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        realmResults.removeAllChangeListeners()
        realm.close()
    }

    override fun onBackPressed() {
        startExitAnimation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        initView()
        initRecyclerView()
        initData()
        initRealm()
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtils.transparencyBar(this)
        }
        setSupportActionBar(tool_bar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener({
            handleCollection()
        })
    }

    private fun initRecyclerView() {
        mData = ArrayList<MobRecipeMethod>()
        mAdapter = MethodAdapter(mContext, mData)
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.isAutoMeasureEnabled = true
        rv_method.layoutManager = linearLayoutManager
        rv_method.adapter = mAdapter
    }

    private fun initData() {
        val intent = intent
        val thumbnail = intent.getStringExtra(KEY_THUMBNAIL)
        iv_img.loadUrl(thumbnail)

        recipeId = intent.getStringExtra(KEY_RECIPE_ID)
        val tempId = recipeId
        if (!TextUtils.isEmpty(tempId)) {
            mPresenter?.queryDetail(tempId)
        } else {
            recipeId = ""
            onShowToast("没有收到recipeId")
        }
    }

    private fun initRealm() {
        realm = Realm.getDefaultInstance()
        realmResults = realm.where(RealmCollection::class.java)
                .equalTo("menuId", recipeId)
                .findAllAsync()
        realmResults.addChangeListener { element ->
            if (element.size > 0) {
                fab.setImageResource(R.drawable.ic_fab_like_p)
            } else {
                fab.setImageResource(R.drawable.ic_fab_like_n)
            }
        }
    }

    private fun startEnterAnimation(savedInstanceState: Bundle?) {
        val animatorListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                hideViewBeforeAnimation()
            }

            override fun onAnimationEnd(animation: Animator) {
                showViewAfterAnimation()
                scroll_view.scrollTo(0, 0)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        }
        exitTransition = ActivityTransition.with(intent)
                .to(iv_img)
                .duration(300)
                .interpolator(DecelerateInterpolator())
                .enterListener(animatorListener)
                .start(savedInstanceState)
    }

    private fun startExitAnimation() {
        layout_app_bar.setExpanded(true, false)
        scroll_view.scrollTo(0, 0)
        Handler().postDelayed({
            hideViewBeforeAnimation()
            exitTransition.interpolator(AccelerateInterpolator()).exit(this@DetailActivity)
        }, 200)
    }

    private fun hideViewBeforeAnimation() {
        layout_coordinator.clipChildren = false
        layout_app_bar.clipChildren = false
        layout_tool_bar.clipChildren = false
        iv_shadow.visibility = View.INVISIBLE
        scroll_view.visibility = View.INVISIBLE
        fab.visibility = View.INVISIBLE
    }

    private fun showViewAfterAnimation() {
        layout_coordinator.clipChildren = true
        layout_app_bar.clipChildren = true
        layout_tool_bar.clipChildren = true
        iv_shadow.visibility = View.VISIBLE
        scroll_view.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE
    }

    private fun handleCollection() {
        if (realmResults.size > 0) {
            RealmHelper.deleteCollectionByResult(realm, realmResults)
        } else {
            if (mobRecipe.recipe != null) {
                val detail = mobRecipe.recipe
                val realmCollection = RealmCollection(
                        0,
                        mobRecipe.ctgTitles,
                        mobRecipe.menuId,
                        mobRecipe.name,
                        detail?.sumary,
                        detail?.ingredients,
                        mobRecipe.thumbnail)
                RealmHelper.createCollection(realm, realmCollection)
            }
        }
    }

    private fun handleRecipeResult(value: MobRecipe) {
        mobRecipe = value

        iv_img.loadUrl(mobRecipe.thumbnail)

        val title = value.name
        if (title != null) {
            layout_tool_bar.title = title
        }

        val recipeDetail = value.recipe
        if (recipeDetail != null) {
            tv_summary.text = recipeDetail.sumary
            var ingredients = recipeDetail.ingredients
            if (ingredients != null) {
                ingredients = ingredients.replace("[", "")
                ingredients = ingredients.replace("]", "")
                ingredients = ingredients.replace("\"", "")
            }
            tv_ingredients.text = ingredients ?: "不详"

            val methods = recipeDetail.method
            if (methods != null && !TextUtils.isEmpty(methods)) {
                val methodList = Gson().fromJson<List<MobRecipeMethod>>(methods, object : TypeToken<List<MobRecipeMethod>>() {

                }.type)
                if (methodList != null && methodList.isNotEmpty()) {
                    mData.clear()
                    mData.addAll(methodList)
                    mAdapter.notifyDataSetChanged()
                }
            }
        }
        // 更新历史
        saveHistoryToRealm()
    }

    private fun saveHistoryToRealm() {
        if (mobRecipe.recipe != null) {
            if (RealmHelper.retrieveHistoryByMenuId(realm, recipeId).size == 0) {  // 如果没有该条历史
                SPUtils.init(mContext, Constant.SP_NAME)
                val limit = SPUtils.getInt(Constant.SP_HISTORY_LIMIT, Constant.DEFAULT_HISTORY_LIMIT)
                if (RealmHelper.retrieveHistory(realm).size >= limit) {
                    RealmHelper.deleteFirstHistory(realm)
                }

                val recipeDetail = mobRecipe.recipe
                val realmHistory = RealmHistory(
                        0,
                        mobRecipe.ctgTitles,
                        mobRecipe.menuId,
                        mobRecipe.name,
                        recipeDetail?.sumary,
                        recipeDetail?.ingredients,
                        mobRecipe.thumbnail,
                        Date())
                RealmHelper.createHistory(realm, realmHistory)
            }
        }
    }

    override fun onQueryDetailSuccess(value: MobRecipe) {
        handleRecipeResult(value)
    }
}
