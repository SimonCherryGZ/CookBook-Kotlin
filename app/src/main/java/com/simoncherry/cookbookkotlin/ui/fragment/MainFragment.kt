package com.simoncherry.cookbookkotlin.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import com.orhanobut.logger.Logger
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.model.RealmCategory
import com.simoncherry.cookbookkotlin.ui.adapter.MainFragmentAdapter
import com.simoncherry.cookbookkotlin.util.RealmHelper
import com.zhl.channeltagview.bean.ChannelItem
import com.zhl.channeltagview.listener.OnChannelItemClicklistener
import com.zhl.channeltagview.listener.UserActionListener
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_main.*
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
class MainFragment : SimpleFragment() {

    private val TAG = MainFragment::class.java.simpleName

    private lateinit var mainFragmentAdapter: MainFragmentAdapter
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var titleList: ArrayList<String>

    private val addedChannels = ArrayList<ChannelItem>()
    private val unAddedChannels = ArrayList<ChannelItem>()

    private lateinit var realm: Realm
    private lateinit var realmResults: RealmResults<RealmCategory>

    private var isInit = false


    companion object {
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_main
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
        initView()
        initViewPager()
        initIndicator()
        initChannelView()
        initRealm()
    }

    private fun initView() {
        iv_expand.setOnClickListener({
            if (layout_channel.visibility == View.GONE) {
                channelLayoutExpand()
            } else {
                channelLayoutCollapse()
            }
        })

        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP
                    && keyCode == KeyEvent.KEYCODE_BACK
                    && layout_channel.visibility == View.VISIBLE) {
                channelLayoutCollapse()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun iconRotateUp() {
        iv_expand.clearAnimation()
        iv_expand.setColorFilter(ContextCompat.getColor(mContext, R.color.white))
        val rotateAnimation = RotateAnimation(0f, 180f, (iv_expand.width / 2).toFloat(), (iv_expand.height / 2).toFloat())
        rotateAnimation.duration = 300
        rotateAnimation.fillAfter = true
        iv_expand.startAnimation(rotateAnimation)
    }

    private fun iconRotateDown() {
        iv_expand.clearAnimation()
        iv_expand.clearColorFilter()
        val rotateAnimation = RotateAnimation(180f, 0f, (iv_expand.width / 2).toFloat(), (iv_expand.height / 2).toFloat())
        rotateAnimation.duration = 300
        rotateAnimation.fillAfter = true
        iv_expand.startAnimation(rotateAnimation)
    }

    private fun channelLayoutExpand() {
        layout_channel.clearAnimation()
        val scaleAnimation = ScaleAnimation(
                1.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f)
        scaleAnimation.duration = 300
        scaleAnimation.fillAfter = true
        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                iconRotateUp()
                layout_channel.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
        layout_channel.startAnimation(scaleAnimation)
    }

    private fun channelLayoutCollapse() {
        layout_channel.clearAnimation()
        val scaleAnimation = ScaleAnimation(
                1.0f, 1.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f)
        scaleAnimation.duration = 300
        scaleAnimation.fillAfter = true
        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                iconRotateDown()
            }

            override fun onAnimationEnd(animation: Animation) {
                layout_channel.clearAnimation()
                layout_channel.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        layout_channel.startAnimation(scaleAnimation)
    }

    private fun initViewPager() {
        titleList = ArrayList<String>()
        fragmentList = ArrayList<Fragment>()
        mainFragmentAdapter = MainFragmentAdapter(childFragmentManager, fragmentList, titleList)
        view_pager.offscreenPageLimit = 2
        view_pager.adapter = mainFragmentAdapter
    }

    private fun initIndicator() {
        layout_tab.setupWithViewPager(view_pager)
    }

    private fun initChannelView() {
        channel_tag_view.setColums(4)
        channel_tag_view.showPahtAnim(false)
        channel_tag_view.setOnChannelItemClicklistener(object : OnChannelItemClicklistener {
            override fun onAddedChannelItemClick(itemView: View, position: Int) {}

            override fun onUnAddedChannelItemClick(itemView: View, checkedChannels: ArrayList<ChannelItem>) {
                RealmHelper.changeCategorySelectedByCtgId(realm, checkedChannels[checkedChannels.size - 1].value, true)
            }
        })

        channel_tag_view.setUserActionListener(object : UserActionListener {
            override fun onMoved(fromPos: Int, toPos: Int, checkedChannels: ArrayList<ChannelItem>) {
                addedChannels.clear()
                addedChannels.addAll(checkedChannels)
            }

            override fun onSwiped(position: Int, itemView: View, checkedChannels: ArrayList<ChannelItem>, uncheckedChannels: ArrayList<ChannelItem>) {
                RealmHelper.changeCategorySelectedByCtgId(realm, uncheckedChannels[uncheckedChannels.size - 1].value, false)
            }
        })
    }

    private fun initRealm() {
        realm = Realm.getDefaultInstance()

        if (realm.where(RealmCategory::class.java)
                .equalTo("isChild", true)
                .equalTo("isSelected", true)
                .findAll().size == 0) {
            Logger.t(TAG).i("set first category selected")
            RealmHelper.setFirstChildCategorySelected(realm)
        }

        realmResults = realm.where(RealmCategory::class.java)
                .equalTo("isChild", true)
                .findAllAsync()
        realmResults.addChangeListener { element ->
            Logger.t(TAG).i("RealmCategory: " + element.toString())
            if (element.size > 0) {
                handleRealmResult(element)
            }
        }
    }

    private fun handleRealmResult(element: RealmResults<RealmCategory>) {
        titleList.clear()
        fragmentList.clear()
        addedChannels.clear()
        unAddedChannels.clear()

        for (i in element.indices) {
            val realmCategory = element[i]
            if (realmCategory.isSelected) {
                titleList.add(realmCategory.name)
                val recipeFragment = RecipeFragment.newInstance(realmCategory.ctgId)
                fragmentList.add(recipeFragment)

                addedChannels.add(convertCategoryToChannel(i, realmCategory))
            } else {
                unAddedChannels.add(convertCategoryToChannel(i, realmCategory))
            }
        }
        Logger.t(TAG).i("addedChannels: " + addedChannels.toString())
        Logger.t(TAG).i("unAddedChannels: " + unAddedChannels.toString())
        if (!isInit) {
            isInit = true
            channel_tag_view.initChannels(addedChannels, unAddedChannels, null)
        }
        mainFragmentAdapter.notifyDataSetChanged()
    }

    private fun convertCategoryToChannel(index: Int, category: RealmCategory): ChannelItem {
        val channelItem = ChannelItem()
        channelItem.id = index
        channelItem.title = category.name
        channelItem.value = category.ctgId
        return channelItem
    }
}