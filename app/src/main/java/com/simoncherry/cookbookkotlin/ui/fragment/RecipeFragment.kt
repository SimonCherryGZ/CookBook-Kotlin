package com.simoncherry.cookbookkotlin.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.model.MobRecipe
import com.simoncherry.cookbookkotlin.model.MobRecipeResult
import com.simoncherry.cookbookkotlin.mvp.contract.RecipeContract
import com.simoncherry.cookbookkotlin.mvp.presenter.RecipePresenter
import com.simoncherry.cookbookkotlin.ui.adapter.RecipeAdapter
import kotlinx.android.synthetic.main.fragment_recipe.*
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
class RecipeFragment : BaseFragment<RecipeContract.View, RecipeContract.Presenter>(), RecipeContract.View {

    private val TAG = RecipeFragment::class.java.simpleName
    private val PAGE_SIZE = 15
    private var currentPage = 1
    private var ctgId: String? = ""
    private var field = "cid"
    private var value = ""

    private lateinit var mAdapter: RecipeAdapter
    private lateinit var mData: MutableList<MobRecipe>

    companion object {
        private val ARG_CTG_ID = "ctgId"

        fun newInstance(ctgId: String) : RecipeFragment {
            val fragment = RecipeFragment()
            val args = Bundle()
            args.putString(ARG_CTG_ID, ctgId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            ctgId = arguments.getString(ARG_CTG_ID)
            field = "cid"
            value = arguments.getString(ARG_CTG_ID)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_recipe
    }

    override fun initComponent() {
        mPresenter = RecipePresenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onQueryRecipeSuccess(result: MobRecipeResult) {
        if (currentPage == 1) {
            mData.clear()
        }
        val resultList = result.list
        if (resultList != null && resultList.isNotEmpty()) {
            mData.addAll(resultList)
        }
        currentPage++
        mAdapter.notifyDataSetChanged()

        stopRefreshLayout()
    }

    override fun onQueryEmpty() {
        super.onQueryEmpty()
        stopRefreshLayout()
    }

    override fun onQueryError(msg: String) {
        super.onQueryError(msg)
        stopRefreshLayout()
    }

    private fun stopRefreshLayout() {
        layout_recipe?.finishRefreshing()
        layout_recipe?.finishLoadmore()
    }

    private fun init() {
        initRecyclerView()
        initRefreshLayout()
        currentPage = 1
        queryRecipeByField(field, value, currentPage)
    }

    private fun initRecyclerView() {
        mData = ArrayList<MobRecipe>()
        mAdapter = RecipeAdapter(mContext, mData)
        mAdapter.setOnItemClickListener(object : RecipeAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                if (mData.size > position) {
                    val recipe = mData[position]
                    val menuId = recipe.menuId
                    val thumbnail = recipe.thumbnail
                    if (menuId != null && thumbnail != null) {
                        startDetailActivity(view, menuId, thumbnail)
                    }
                }
            }
        })
        rv_recipe.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        rv_recipe.adapter = mAdapter
    }

    private fun startDetailActivity(view: View, recipeId: String, thumbnail: String) {
//        val intent = Intent(activity, DetailActivity::class.java)
//        intent.putExtra(DetailActivity.KEY_RECIPE_ID, recipeId)
//        intent.putExtra(DetailActivity.KEY_THUMBNAIL, thumbnail)
//        ActivityTransitionLauncher.with(activity).from(view).launch(intent)
    }

    private fun initRefreshLayout() {
        layout_recipe.setAutoLoadMore(true)
        layout_recipe.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                currentPage = 1
                queryRecipeByField(field, value, currentPage)
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                queryRecipeByField(field, value, currentPage)
            }
        })
    }

    private fun queryRecipeByField(field: String, value: String, currentPage: Int) {
        if (ctgId != null) {
            mPresenter?.queryRecipeByField(field, value, currentPage, PAGE_SIZE)
        } else {
            onShowToast("获取菜谱分类失败，请退出后重新进入")
        }
    }

    fun changeCategory(ctgId: String) {
        this.ctgId = ctgId
        this.field = "cid"
        this.value = ctgId
        currentPage = 1
        rv_recipe.scrollToPosition(0)
        queryRecipeByField(field, value, currentPage)
    }
}