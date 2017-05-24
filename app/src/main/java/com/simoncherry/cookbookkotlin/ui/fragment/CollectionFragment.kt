package com.simoncherry.cookbookkotlin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.kogitune.activity_transition.ActivityTransitionLauncher
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.model.RealmCollection
import com.simoncherry.cookbookkotlin.ui.activity.DetailActivity
import com.simoncherry.cookbookkotlin.ui.adapter.CollectionAdapter
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_collection.*
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
class CollectionFragment : SimpleFragment() {

    private lateinit var mAdapter: CollectionAdapter
    private lateinit var mData: MutableList<RealmCollection>

    private lateinit var realm: Realm
    private lateinit var realmResults: RealmResults<RealmCollection>


    companion object {
        fun newInstance(): CollectionFragment {
            val fragment = CollectionFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_collection
    }

    override fun onDestroy() {
        super.onDestroy()
        realmResults.removeAllChangeListeners()
        realm.close()
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
        mData = ArrayList<RealmCollection>()
        mAdapter = CollectionAdapter(mContext, mData)
        mAdapter.setOnItemClickListener(object : CollectionAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                if (mData.size > position) {
                    val collection : RealmCollection = mData[position]
                    val menuId: String? = collection.menuId
                    val thumbnail: String? = collection.thumbnail
                    if (menuId != null && thumbnail != null) {
                        startDetailActivity(view, menuId, thumbnail)
                    }
                }
            }
        })
        rv_recipe.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        rv_recipe.adapter = mAdapter
    }

    private fun initRealm() {
        realm = Realm.getDefaultInstance()
        realmResults = realm.where(RealmCollection::class.java).findAllSortedAsync("id", Sort.DESCENDING)
        realmResults.addChangeListener { element -> loadCollectionRecipeFromRealm(element) }
    }

    private fun loadCollectionRecipeFromRealm(element: RealmResults<RealmCollection>) {
        mData.clear()
        if (element.size > 0) {
            val recipeList = element.subList(0, element.size)
            mData.addAll(recipeList)
        }
        mAdapter.notifyDataSetChanged()
    }

    private fun startDetailActivity(view: View, recipeId: String, thumbnail: String) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.KEY_RECIPE_ID, recipeId)
        intent.putExtra(DetailActivity.KEY_THUMBNAIL, thumbnail)
        ActivityTransitionLauncher.with(activity).from(view).launch(intent)
    }
}