package com.simoncherry.cookbookkotlin.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
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
class MainFragmentAdapter(
        fm: FragmentManager,
        var fragments: ArrayList<Fragment>,
        var titles: ArrayList<String>
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}