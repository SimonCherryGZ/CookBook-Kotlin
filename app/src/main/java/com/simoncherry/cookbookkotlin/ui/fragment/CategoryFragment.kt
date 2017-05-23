package com.simoncherry.cookbookkotlin.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.model.MobCategory
import com.simoncherry.cookbookkotlin.model.RealmCategory
import com.simoncherry.cookbookkotlin.ui.adapter.ChildCategoryAdapter
import com.simoncherry.cookbookkotlin.ui.adapter.ParentCategoryAdapter
import com.simoncherry.cookbookkotlin.util.GridSpacingItemDecoration
import com.simoncherry.cookbookkotlin.util.RealmHelper
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_category.*
import java.util.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class CategoryFragment : SimpleFragment() {

    private val TAG = CategoryFragment::class.java.simpleName

    lateinit var parentCategoryAdapter: ParentCategoryAdapter
    lateinit var parentList: MutableList<MobCategory>
    lateinit var childCategoryAdapter: ChildCategoryAdapter
    lateinit var allChildList: MutableList<List<MobCategory>>
    lateinit var childList: MutableList<MobCategory>

    lateinit var realm: Realm
    lateinit var realmResults: RealmResults<RealmCategory>


    interface OnFragmentInteractionListener {
        fun onClickCategory(ctgId: String, name: String)
    }

    private var mListener: OnFragmentInteractionListener? = null


    companion object {
        fun newInstance(): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_category
    }

    override fun onDestroyView() {
        super.onDestroyView()
        realmResults.removeAllChangeListeners()
        realm.close()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        initRecyclerView()
        initRealm()
    }

    private fun initRecyclerView() {
        parentList = ArrayList<MobCategory>()
        parentCategoryAdapter = ParentCategoryAdapter(mContext, parentList)
        parentCategoryAdapter.setOnItemClickListener(object : ParentCategoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (allChildList.size > position) {
                    childList.clear()
                    childList.addAll(allChildList[position])
                    Logger.t(TAG).i("childList: " + childList.toString())
                    childCategoryAdapter.notifyDataSetChanged()
                }
            }
        })
        rv_category.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        rv_category.adapter = parentCategoryAdapter

        allChildList = ArrayList<List<MobCategory>>()
        childList = ArrayList<MobCategory>()
        childCategoryAdapter = ChildCategoryAdapter(mContext, childList)
        childCategoryAdapter.setOnItemClickListener(object : ChildCategoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (childList.size > position) {
                    onClickCategory(childList[position].ctgId, childList[position].name)
                }
            }
        })
        rv_tag.layoutManager = GridLayoutManager(mContext, 3)
        rv_tag.addItemDecoration(GridSpacingItemDecoration(3, 20, true))
        rv_tag.adapter = childCategoryAdapter
    }

    private fun onClickCategory(ctgId: String, name: String) {
        mListener?.onClickCategory(ctgId, name)
    }

    private fun initRealm() {
        realm = Realm.getDefaultInstance()
        realmResults = realm.where(RealmCategory::class.java).findAllAsync()
        realmResults.addChangeListener { element ->
            if (element.size > 0) {
                handleRealmResult(element)
            }
        }
    }

    private fun handleRealmResult(element: RealmResults<RealmCategory>) {
        val parentCategory = element.where().equalTo("isChild", false).findAll()
        if (parentCategory != null) {
            parentList.clear()
            allChildList.clear()

            for (category in parentCategory) {
                parentList.add(RealmHelper.convertRealmCategoryToMobCategory(category))
                val childCategory = element.where().equalTo("parentId", category.ctgId).findAll()
                if (childCategory != null) {
                    val childList = ArrayList<MobCategory>()
                    for (category1 in childCategory) {
                        childList.add(RealmHelper.convertRealmCategoryToMobCategory(category1))
                    }
                    allChildList.add(childList)
                }
            }
            parentList[0].isSelected = true
        }

        if (allChildList.size > 0) {
            Logger.t(TAG).i("allChildList: " + allChildList.toString())
            childList.clear()
            childList.addAll(allChildList[0])
        }

        parentCategoryAdapter.notifyDataSetChanged()
        childCategoryAdapter.notifyDataSetChanged()
    }
}