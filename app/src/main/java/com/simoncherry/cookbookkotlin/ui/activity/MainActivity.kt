package com.simoncherry.cookbookkotlin.ui.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.ui.fragment.CategoryFragment
import com.simoncherry.cookbookkotlin.ui.fragment.CollectionFragment
import com.simoncherry.cookbookkotlin.ui.fragment.RecipeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : SimpleActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        CategoryFragment.OnFragmentInteractionListener{

    private lateinit var fragmentManager: FragmentManager
    private lateinit var currentFragment: Fragment
    private lateinit var previousFragment: Fragment
    private lateinit var categoryFragment: CategoryFragment
    private lateinit var collectionFragment: CollectionFragment
    private lateinit var recipeFragment: RecipeFragment
    private var exitTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (previousFragment is CategoryFragment && currentFragment is RecipeFragment) {
                backToFragment(currentFragment, previousFragment)
                toolbar.setTitle(R.string.main_title_category)
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                    exitTime = System.currentTimeMillis()
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_home -> toolbar.setTitle(R.string.main_title_home)
            R.id.nav_category -> {
                toolbar.setTitle(R.string.main_title_category)
                switchFragment(currentFragment, categoryFragment)
            }
            R.id.nav_collection -> {
                toolbar.setTitle(R.string.main_title_collection)
                switchFragment(currentFragment, collectionFragment)
            }
            R.id.nav_history -> toolbar.setTitle(R.string.main_title_history)
            R.id.nav_manage -> toolbar.setTitle(R.string.main_title_setting)
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun init() {
        initView()
        initFragment()
    }

    private fun initView() {
        toolbar.setTitle(R.string.main_title_home)
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun initFragment() {
        fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        categoryFragment = CategoryFragment.newInstance()
        collectionFragment = CollectionFragment.newInstance()
        recipeFragment = RecipeFragment.newInstance("0010001010")

        previousFragment = categoryFragment
        currentFragment = categoryFragment

        transaction
                .add(R.id.layout_content, categoryFragment)
                .add(R.id.layout_content, collectionFragment)
                .add(R.id.layout_content, recipeFragment)
                .show(categoryFragment)
                .hide(collectionFragment)
                .hide(recipeFragment)
                .commit()
    }

    private fun switchFragment(from: Fragment, to: Fragment) {
        if (from != to) {
            val transaction = fragmentManager.beginTransaction()
            transaction
                    .hide(from)
                    .show(to)
                    .commit()
            previousFragment = from
            currentFragment = to
        }
    }

    private fun backToFragment(from: Fragment, to: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction
                .hide(from)
                .show(to)
                .commit()
        previousFragment = to
        currentFragment = to
    }

    override fun onClickCategory(ctgId: String, name: String) {
        recipeFragment.changeCategory(ctgId)
        switchFragment(currentFragment, recipeFragment)
        toolbar.title = "分类 - " + name
    }
}
