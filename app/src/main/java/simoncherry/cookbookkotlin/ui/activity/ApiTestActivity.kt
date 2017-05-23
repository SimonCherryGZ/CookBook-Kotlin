package simoncherry.cookbookkotlin.ui.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_api_test.*
import simoncherry.cookbookkotlin.R
import simoncherry.cookbookkotlin.model.MobCategoryResult
import simoncherry.cookbookkotlin.model.MobRecipe
import simoncherry.cookbookkotlin.model.MobRecipeResult
import simoncherry.cookbookkotlin.mvp.contract.ApiTestContract
import simoncherry.cookbookkotlin.mvp.presenter.ApiTestPresenter

class ApiTestActivity : BaseActivity<ApiTestContract.View, ApiTestContract.Presenter>(), ApiTestContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_query_category.setOnClickListener {
            queryCategory()
        }

        btn_query_recipe.setOnClickListener {
            queryRecipe("0010001010", 1, 20)
        }

        btn_query_detail.setOnClickListener {
            queryDetail("00100010100000031636")
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_api_test
    }

    override fun initComponent() {
        mPresenter = ApiTestPresenter()
    }

    fun queryCategory() {
        mPresenter?.queryCategory()
    }

    fun queryRecipe(cid : String, page : Int, size: Int) {
        mPresenter?.queryRecipe(cid, page, size)
    }

    fun queryDetail(id : String) {
        mPresenter?.queryDetail(id)
    }

    override fun onQueryEmpty() {
        tv_result.text = "查询结果为空"
    }

    override fun onQueryError(msg: String) {
        tv_result.text = msg
    }

    override fun onQueryCategorySuccess(value: MobCategoryResult?) {
        tv_result.text = value?.toString() ?: "获取目录为空"
    }

    override fun onQueryRecipeSuccess(value: MobRecipeResult?) {
        tv_result.text = value?.toString() ?: "获取菜谱为空"
    }

    override fun onQueryDetailSuccess(value: MobRecipe?) {
        tv_result.text = value?.toString() ?: "获取详情为空"
    }
}
