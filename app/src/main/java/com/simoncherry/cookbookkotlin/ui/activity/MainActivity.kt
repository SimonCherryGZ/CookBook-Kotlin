package com.simoncherry.cookbookkotlin.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.ui.fragment.*
import com.simoncherry.cookbookkotlin.util.MySuggestionProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : SimpleActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        CategoryFragment.OnFragmentInteractionListener{

    private lateinit var fragmentManager: FragmentManager
    private lateinit var currentFragment: Fragment
    private lateinit var previousFragment: Fragment
    private lateinit var mainFragment: MainFragment
    private lateinit var categoryFragment: CategoryFragment
    private lateinit var collectionFragment: CollectionFragment
    private lateinit var historyFragment: HistoryFragment
    private lateinit var settingFragment: SettingFragment
    private lateinit var recipeFragment: RecipeFragment
    internal var searchView: SearchView? = null
    private var exitTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            val suggestions = SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
            suggestions.saveRecentQuery(query, null)
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (searchView != null && !searchView!!.isIconified) {
                searchView?.isIconified = true
            } else if (previousFragment is CategoryFragment && currentFragment is RecipeFragment) {
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
        val searchItem = menu?.findItem(R.id.search_view)  //在菜单中找到对应控件的item
        val searchManager = this@MainActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }

        if (searchView != null) {
            val finalSearchView = searchView
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(this@MainActivity.componentName))
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    onQueryRecipeByName(query)
                    finalSearchView?.isIconified = true
                    finalSearchView?.isIconified = true
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })

            searchView?.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return false
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    val cursor = finalSearchView?.suggestionsAdapter?.getItem(position) as Cursor
                    val query = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                    onQueryRecipeByName(query)
                    finalSearchView.isIconified = true
                    return false
                }
            })
        }

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_home -> {
                toolbar.setTitle(R.string.main_title_home)
                switchFragment(currentFragment, mainFragment)
            }
            R.id.nav_category -> {
                toolbar.setTitle(R.string.main_title_category)
                switchFragment(currentFragment, categoryFragment)
            }
            R.id.nav_collection -> {
                toolbar.setTitle(R.string.main_title_collection)
                switchFragment(currentFragment, collectionFragment)
            }
            R.id.nav_history -> {
                toolbar.setTitle(R.string.main_title_history)
                switchFragment(currentFragment, historyFragment)
            }
            R.id.nav_manage -> {
                toolbar.setTitle(R.string.main_title_setting)
                switchFragment(currentFragment, settingFragment)
            }
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

        mainFragment = MainFragment.newInstance()
        categoryFragment = CategoryFragment.newInstance()
        collectionFragment = CollectionFragment.newInstance()
        historyFragment = HistoryFragment.newInstance()
        settingFragment = SettingFragment.newInstance()
        recipeFragment = RecipeFragment.newInstance("0010001010")

        previousFragment = mainFragment
        currentFragment = mainFragment

        transaction
                .add(R.id.layout_content, mainFragment)
                .add(R.id.layout_content, categoryFragment)
                .add(R.id.layout_content, collectionFragment)
                .add(R.id.layout_content, historyFragment)
                .add(R.id.layout_content, settingFragment)
                .add(R.id.layout_content, recipeFragment)
                .show(mainFragment)
                .hide(categoryFragment)
                .hide(collectionFragment)
                .hide(historyFragment)
                .hide(settingFragment)
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

    private fun onQueryRecipeByName(name: String) {
        recipeFragment.queryRecipeByName(name)
        switchFragment(currentFragment, recipeFragment)
        toolbar.title = "查询 - " + name
    }
}
