package simoncherry.cookbookkotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_api_test.*
import simoncherry.cookbookkotlin.api.MobAPIService
import simoncherry.cookbookkotlin.model.MobCategoryResult
import simoncherry.cookbookkotlin.model.MobRecipe
import simoncherry.cookbookkotlin.model.MobRecipeResult
import simoncherry.cookbookkotlin.util.RxUtils
import java.util.*

class ApiTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(simoncherry.cookbookkotlin.R.layout.activity_api_test)

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

    fun queryCategory() {
        MobAPIService.getMobAPI()
                .queryCategory(MobAPIService.MOB_API_KEY)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleMobResult())
                .subscribe(object : Consumer<MobCategoryResult> {
                    override fun accept(t: MobCategoryResult?) {
                        tv_result.text = t?.toString() ?: "获取目录为空"
                    }
                })
    }

    fun queryRecipe(cid : String, page : Int, size: Int) {
        val params = HashMap<String, String>()
        params.put("key", MobAPIService.MOB_API_KEY)
        params.put("cid", cid)
        params.put("page", page.toString())
        params.put("size", size.toString())

        MobAPIService.getMobAPI()
                .queryRecipe(params)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleMobResult())
                .subscribe(object : Consumer<MobRecipeResult> {
                    override fun accept(t: MobRecipeResult?) {
                        tv_result.text = t?.toString() ?: "获取菜谱为空"
                    }
                })
    }

    fun queryDetail(id : String) {
        val params = HashMap<String, String>()
        params.put("key", MobAPIService.MOB_API_KEY)
        params.put("id", id)

        MobAPIService.getMobAPI()
                .queryDetail(params)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleMobResult())
                .subscribe( object : Consumer<MobRecipe> {
                    override fun accept(t: MobRecipe?) {
                        tv_result.text = t?.toString() ?: "获取详情为空"
                    }
                })
    }
}
